package com.hanxing.kxcharge.module.operations.service.equipmentconnector;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnore;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.*;
import com.hanxing.kxcharge.module.operations.convert.equipmentconnector.EquipmentConnectorConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipment.EquipmentMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipmentconnector.EquipmentConnectorMapper;
import com.hanxing.kxcharge.module.operations.mq.producer.charging.ChargeProducer;
import com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector.OnlineEquipmentConnectorService;
import com.hanxing.kxcharge.module.pile.mq.RemoteControlOfStartupOnTheOperationPlatformDto;
import com.hanxing.kxcharge.module.pile.mq.RemoteShutdownOfOperationPlatformDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.*;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.EQUIPMENT_CONNECTOR_NOT_EXISTS;

/**
 * 电枪管理 Service 实现类
 *
 * @author kaixin
 */
@Service
@Validated
public class EquipmentConnectorServiceImpl implements EquipmentConnectorService {

    @Resource
    private EquipmentConnectorMapper equipmentConnectorMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private OnlineEquipmentConnectorService onlineEquipmentConnectorService;

    @Resource
    private ChargeProducer chargeProducer;

    @Override
    public Long createEquipmentConnector(EquipmentConnectorCreateReqVO createReqVO) {
        // 插入
        EquipmentConnectorDO equipmentConnector = EquipmentConnectorConvert.INSTANCE.convert(createReqVO);
        equipmentConnectorMapper.insert(equipmentConnector);
        // 返回
        return equipmentConnector.getId();
    }

    @Override
    public void updateEquipmentConnector(EquipmentConnectorUpdateReqVO updateReqVO) {
        // 校验存在
        validateEquipmentConnectorExists(updateReqVO.getId());
        // 更新
        EquipmentConnectorDO updateObj = EquipmentConnectorConvert.INSTANCE.convert(updateReqVO);
        equipmentConnectorMapper.updateById(updateObj);
    }

    @Override
    public void deleteEquipmentConnector(Long id) {
        // 校验存在
        validateEquipmentConnectorExists(id);
        // 删除
        equipmentConnectorMapper.deleteById(id);
    }

    private void validateEquipmentConnectorExists(Long id) {
        if (equipmentConnectorMapper.selectById(id) == null) {
            throw exception(EQUIPMENT_CONNECTOR_NOT_EXISTS);
        }
    }

    @Override
    public EquipmentConnectorDO getEquipmentConnector(Long id) {
        EquipmentConnectorDO equipmentConnectorDO = equipmentConnectorMapper.selectById(id);
        setState(equipmentConnectorDO);
        return equipmentConnectorDO;
    }

    @Override
    public EquipmentConnectorDO getEquipmentConnector(String connectorCode) {

        EquipmentConnectorDO equipmentConnectorDO = equipmentConnectorMapper.selectOne(new LambdaQueryWrapperX<EquipmentConnectorDO>()
                .eq(EquipmentConnectorDO::getConnectorCode, connectorCode));
        if (equipmentConnectorDO != null){
            setState(equipmentConnectorDO);
        }
        return equipmentConnectorDO;
    }

    @Override
    @TenantIgnore
    public EquipmentConnectorDO getEquipmentConnectorTenantless(String connectorCode) {
        EquipmentConnectorDO equipmentConnectorDO = equipmentConnectorMapper.selectOne(new LambdaQueryWrapperX<EquipmentConnectorDO>()
                .eq(EquipmentConnectorDO::getConnectorCode, connectorCode));
        if (equipmentConnectorDO != null){
            setState(equipmentConnectorDO);
        }
        return equipmentConnectorDO;
    }

    private void setState(EquipmentConnectorDO equipmentConnectorDO) {
        String connectorCode = equipmentConnectorDO.getConnectorCode();
        String gunNo = connectorCode.substring(connectorCode.length() - 1);
        EquipmentDO equipmentDO = equipmentMapper.selectById(equipmentConnectorDO.getEquipmentId());
        long nowTime = System.currentTimeMillis();
//        String gunState = onlineChargeGunDao.getGunState(equipmentDO.getEquipmentCode(), gunNo, nowTime);
        Integer gunWorkState = onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorDO.getConnectorCode());
        equipmentConnectorDO.setWorkStatus(gunWorkState);
    }

    @Override
    public List<EquipmentConnectorDO> getEquipmentConnectorList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        List<EquipmentConnectorDO> equipmentConnectorDOS = equipmentConnectorMapper.selectBatchIds(ids);
        equipmentConnectorDOS.forEach(this::setState);
        return equipmentConnectorDOS;
    }

    @Override
    public PageResult<EquipmentConnectorDO> getEquipmentConnectorPage(EquipmentConnectorPageReqVO pageReqVO) {
        PageResult<EquipmentConnectorDO> equipmentConnectorDOPageResult = equipmentConnectorMapper.selectPage(pageReqVO);
        List<EquipmentConnectorDO> list = equipmentConnectorDOPageResult.getList();
        list.forEach(this::setState);
        equipmentConnectorDOPageResult.setList(list);
        return equipmentConnectorDOPageResult;
    }

    @Override
    public List<EquipmentConnectorDO> getEquipmentConnectorList(EquipmentConnectorExportReqVO exportReqVO) {
        List<EquipmentConnectorDO> equipmentConnectorDOS = equipmentConnectorMapper.selectList(exportReqVO);
        equipmentConnectorDOS.forEach(this::setState);
        return equipmentConnectorDOS;
    }

    @Override
    public void startCharge(String pillCode, String gunCode, String orderNumber, String logicNumber, String physicalNumber, BigDecimal money) {
        RemoteControlOfStartupOnTheOperationPlatformDto platformDto = new RemoteControlOfStartupOnTheOperationPlatformDto();
        platformDto.setPileCode(pillCode);
        //交易流水号,需要注意交易流水号的位数是32位
        platformDto.setTransactionSerialNo(orderNumber);
        platformDto.setGunNo(gunCode);
        platformDto.setLogicalCardNo(logicNumber);
        platformDto.setPhysicalCardNo(physicalNumber);
        platformDto.setAccountBalance(money.floatValue());
        chargeProducer.startCharge(platformDto);
    }

    @Override
    public void endCharge(String pileCode, String gunId) {
        RemoteShutdownOfOperationPlatformDto remoteShutdownOfOperationPlatformDto = new RemoteShutdownOfOperationPlatformDto();
        remoteShutdownOfOperationPlatformDto.setGunNo(gunId);
        remoteShutdownOfOperationPlatformDto.setPileCode(pileCode);
        chargeProducer.endCharge(remoteShutdownOfOperationPlatformDto);
    }

}
