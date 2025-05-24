package com.hanxing.kxcharge.module.operations.service.equipment;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.enums.PriceSetEnum;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.BillingModelRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.PileLoginReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentPageReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentUpdateReqVO;
import com.hanxing.kxcharge.module.operations.convert.equipment.EquipmentConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplate.CostTemplateMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplateprice.CostTemplatePriceMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplatepricetype.CostTemplatePriceTypeMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipment.EquipmentMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipmentconnector.EquipmentConnectorMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.station.StationMapper;
import com.hanxing.kxcharge.module.operations.framework.kxcharge.config.KxchargeProperties;
import com.hanxing.kxcharge.module.operations.mq.producer.proofTime.ProofTimeProducer;
import com.hanxing.kxcharge.module.operations.mq.producer.proofprice.ProofPriceProducer;
import com.hanxing.kxcharge.module.operations.mq.producer.remoteissueqrcode.RemoteIssueQRCodeProducer;
import com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector.OnlineEquipmentConnectorService;
import com.hanxing.kxcharge.module.pile.mq.RemoteIssueQRCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.EQUIPMENT_ALREADY_EXISTED;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.EQUIPMENT_NOT_EXISTS;

/**
 * 电桩管理 Service 实现类
 *
 * @author kaixin
 */
@Service
@Slf4j
@Validated
public class EquipmentServiceImpl implements EquipmentService {

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private StationMapper stationMapper;

    @Resource
    private EquipmentConnectorMapper equipmentConnectorMapper;

    @Resource
    private CostTemplateMapper costTemplateMapper;

    @Resource
    private CostTemplatePriceTypeMapper costTemplatePriceTypeMapper;

    @Resource
    private CostTemplatePriceMapper costTemplatePriceMapper;

    @Resource
    private OnlineEquipmentConnectorService onlineEquipmentConnectorService;

    @Resource
    private RemoteIssueQRCodeProducer remoteIssueQRCodeProducer;

    @Resource
    private KxchargeProperties kxchargeProperties;


    @Resource
    private ProofTimeProducer proofTimeProducer;

    @Resource
    private ProofPriceProducer proofPriceProducer;

    //尖
    private final static String POINTED = "pointed";
    //峰
    private final static String PEAK = "peak";
    //平
    private final static String FLAT = "flat";
    //谷
    private final static String VALLEY = "valley";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createEquipment(EquipmentCreateReqVO createReqVO) {

        // 如果为1，按电站定价
        if (createReqVO.getPriceSet() == 1) {
            StationDO station = stationMapper.selectById(createReqVO.getStationId());
            createReqVO.setCostTemplateId(station.getCostTemplateId());
        }
        boolean exists = equipmentMapper.exists(new LambdaQueryWrapperX<EquipmentDO>()
                .and(e -> e.eq(EquipmentDO::getEquipmentCode, createReqVO.getEquipmentCode())
                        .or().eq(EquipmentDO::getEquipmentName, createReqVO.getEquipmentName())));
        if (exists) {
            throw exception(EQUIPMENT_ALREADY_EXISTED);
        }
        // 插入
        EquipmentDO equipment = EquipmentConvert.INSTANCE.convert(createReqVO);
        equipmentMapper.insert(equipment);

        for (int i = 0; i < equipment.getEquipmentGunNumber(); i++) {
            EquipmentConnectorDO connector = new EquipmentConnectorDO();
            connector.setEquipmentId(equipment.getId());
            connector.setStationId(equipment.getStationId());
            connector.setConnectorCode(equipment.getEquipmentCode() + "0" + (i + 1));
            connector.setConnectorName(equipment.getEquipmentName() + "0" + (i + 1));
            connector.setIsEnableStart(1);
            connector.setOrgId(equipment.getOrgId());
            connector.setEquipmentBrandId(equipment.getEquipmentBrandId());
            connector.setEquipmentModelId(equipment.getEquipmentModelId());
            equipmentConnectorMapper.insert(connector);
        }


        // 返回
        return equipment.getId();
    }

    @Override
    public void updateEquipment(EquipmentUpdateReqVO updateReqVO) {
        // 校验存在
        validateEquipmentExists(updateReqVO.getId());
        // 如果为1，按电站定价
        if (PriceSetEnum.STATIONPRICE.getValue().equals(updateReqVO.getPriceSet())) {
            StationDO station = stationMapper.selectById(updateReqVO.getStationId());
            updateReqVO.setCostTemplateId(station.getCostTemplateId());
        }
        // 更新
        EquipmentDO updateObj = EquipmentConvert.INSTANCE.convert(updateReqVO);
        equipmentMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquipment(Long id) {
        // 校验存在
        validateEquipmentExists(id);
        // 删除
        equipmentMapper.deleteById(id);
        // 删除枪
        equipmentConnectorMapper.delete(new LambdaQueryWrapperX<EquipmentConnectorDO>().eq(EquipmentConnectorDO::getEquipmentId, id));
    }

    private void validateEquipmentExists(Long id) {
        if (equipmentMapper.selectById(id) == null) {
            throw exception(EQUIPMENT_NOT_EXISTS);
        }
    }

    @Override
    public EquipmentDO getEquipment(Long id) {
        EquipmentDO equipmentDO = equipmentMapper.selectById(id);
        //更新在线状态
        Integer isOnline = this.getIsOnline(equipmentDO);
        equipmentDO.setIsOnline(isOnline);
        return equipmentDO;
    }

    @Override
    public List<EquipmentDO> getEquipmentList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        List<EquipmentDO> equipmentDOS = equipmentMapper.selectBatchIds(ids);
        //更新在线状态
        equipmentDOS.forEach(item -> {
            item.setIsOnline(getIsOnline(item));
        });
        return equipmentDOS;
    }

    @Override
    public PageResult<EquipmentDO> getEquipmentPage(EquipmentPageReqVO pageReqVO) {
        PageResult<EquipmentDO> equipmentDOPageResult = equipmentMapper.selectPage(pageReqVO);
        List<EquipmentDO> list = equipmentDOPageResult.getList();
        //更新在线状态
        list.forEach(item -> {
            item.setIsOnline(getIsOnline(item));
        });
        equipmentDOPageResult.setList(list);
        return equipmentDOPageResult;
    }

    @Override
    public List<EquipmentDO> getEquipmentList(EquipmentExportReqVO exportReqVO) {
        List<EquipmentDO> equipmentDOS = equipmentMapper.selectList(exportReqVO);
        //更新在线状态
        equipmentDOS.forEach(item -> {
            item.setIsOnline(getIsOnline(item));
        });
        return equipmentDOS;
    }

    @Override
    public Boolean pileLogin(PileLoginReqDto pileLoginReqDto) {
        EquipmentDO equipmentDO = equipmentMapper.selectOne(new QueryWrapper<EquipmentDO>().lambda().eq(EquipmentDO::getEquipmentCode, pileLoginReqDto.getPileCode()));
        if (equipmentDO == null) {
            log.info("登陆失败,找不到该桩");
            return Boolean.FALSE;
        }
        if (equipmentDO.getIsEnableStart() == 0) {
            log.info("登陆失败,该桩已经被停用");
            return Boolean.FALSE;
        }
        SaveEquipmentDOByPillLogin(pileLoginReqDto, equipmentDO);

        log.info("登陆成功");

        //存入设备登陆信息
        onlineEquipmentConnectorService.login(equipmentDO.getStationId(), pileLoginReqDto.getPileCode(), Integer.parseInt(pileLoginReqDto.getChargingGunNum()));

        //下发二维码
//        sendRemoteIssueQRCode(pileLoginReqDto.getPileCode());

        return Boolean.TRUE;
    }

    private void sendRemoteIssueQRCode(String pileCode) {
        RemoteIssueQRCode remoteIssueQRCode = new RemoteIssueQRCode();
        remoteIssueQRCode.setPileCode(pileCode);
        remoteIssueQRCode.setQRCodeFormat("01");
        String QRCodePrefix = kxchargeProperties.getUrl()+"/stser/?connector_id=";
        remoteIssueQRCode.setQRCodePrefixLength(QRCodePrefix.length());
        remoteIssueQRCode.setQRCodePrefix(QRCodePrefix);
        remoteIssueQRCodeProducer.sendIssueQRCode(remoteIssueQRCode);
    }


    @Override
    public EquipmentDO getEquipmentByPileCode(String pileCode) {
        return equipmentMapper.selectOne(new QueryWrapper<EquipmentDO>().lambda().eq(EquipmentDO::getEquipmentCode, pileCode));

    }


    @Override
    public void batchUpdateCostTemplateId(EquipmentUpdateReqVO equipmentUpdateReqVO) {
        // 更新
        EquipmentDO updateObj = EquipmentConvert.INSTANCE.convert(equipmentUpdateReqVO);

        equipmentMapper.update(updateObj, new LambdaQueryWrapperX<EquipmentDO>()
                .eq(EquipmentDO::getStationId, updateObj.getStationId())
                .eq(EquipmentDO::getPriceSet, 1));
    }

    @Override
    public BillingModelRespDto getCostTemplatePrice(String pileCode) {
        //查询桩
        EquipmentDO equipment = this.getEquipmentByPileCode(pileCode);

        return getBillingModelRespDto(equipment);
    }

    @Override
    public BillingModelRespDto getCostTemplatePriceById(Long id) {
        EquipmentDO equipment = this.getEquipment(id);
        BillingModelRespDto billingModelRespDto = getBillingModelRespDto(equipment);

        billingModelRespDto.setPileCode(equipment.getEquipmentCode());
        return billingModelRespDto;
    }

    @Override
    public Boolean enable(Long id) {
        //查询该枪号
        EquipmentConnectorDO equipmentConnectorDO = equipmentConnectorMapper.selectById(id);
        equipmentConnectorDO.setIsEnableStart(equipmentConnectorDO.getIsEnableStart() == 1 ? 0 : 1);
        int i = equipmentConnectorMapper.updateById(equipmentConnectorDO);
        return i == 1;
    }

    @Override
    public Boolean proofTime(Long id) {
        EquipmentDO equipment =this.getEquipment(id);
        if (equipment==null){
            return Boolean.FALSE;
        }
        proofTimeProducer.sendProofTime(equipment.getEquipmentCode());
        return Boolean.TRUE;
    }

    @Override
    public Boolean proofPrice(Long id) {
        BillingModelRespDto costTemplatePriceById = this.getCostTemplatePriceById(id);
        proofPriceProducer.sendProofPrice(costTemplatePriceById);
        EquipmentDO equipment = this.getEquipment(id);
        equipment.setSetPriceTime(LocalDateTime.now());
        return Boolean.TRUE;
    }

    //根据桩获取桩的计费模块
    private BillingModelRespDto getBillingModelRespDto(EquipmentDO equipment) {
        //定义返回对象
        BillingModelRespDto billingModelRespDto = new BillingModelRespDto();


        //根据桩的计费模型id查询计费模型规则
        Long costTemplateId = equipment.getCostTemplateId();


        //设置计费模型编号,固定值0100
        billingModelRespDto.setBillingModelNo("0100");


        //计算尖峰平谷费率
        List<CostTemplatePriceTypeDO> costTemplatePriceTypeByTemplateId = this.getCostTemplatePriceTypeByTemplateId(costTemplateId);
        costTemplatePriceTypeByTemplateId.forEach(item -> {
            BigDecimal pricePower = item.getPricePower();
            BigDecimal priceService = item.getPriceService();
            if (item.getTypeName().equals(POINTED)) {
                billingModelRespDto.setSharpElectricityRate((pricePower.floatValue()));
                billingModelRespDto.setTopServiceFeeRate(priceService.floatValue());
            } else if (item.getTypeName().equals(PEAK)) {
                billingModelRespDto.setPeakElectricityRate(pricePower.floatValue());
                billingModelRespDto.setPeakServiceFeeRate(priceService.floatValue());
            } else if (item.getTypeName().equals(FLAT)) {
                billingModelRespDto.setFlatElectricityRate(pricePower.floatValue());
                billingModelRespDto.setFlatServiceFeeRate(priceService.floatValue());
            } else if (item.getTypeName().equals(VALLEY)) {
                billingModelRespDto.setValleyElectricityRate(pricePower.floatValue());
                billingModelRespDto.setValleyServiceFeeRate(priceService.floatValue());
            }
        });

        //计算计损费率
        billingModelRespDto.setLossCalculationRatio("00");

        //计算各时段费率
        List<String> periodRateNoList = new ArrayList<>();
        List<CostTemplatePriceDO> costTemplatePriceByTemplateId = this.getCostTemplatePriceByTemplateId(costTemplateId);
        for (int i = 1; i <= 48; i++) {
            int finalI = i;
            costTemplatePriceByTemplateId.forEach(item -> {
                if (finalI > item.getStartTime() && finalI <= item.getEndTime()) {
                    if (item.getPriceTypeId().equals(POINTED)) {
                        periodRateNoList.add("00");
                    } else if (item.getPriceTypeId().equals(PEAK)) {
                        periodRateNoList.add("01");
                    } else if (item.getPriceTypeId().equals(FLAT)) {
                        periodRateNoList.add("02");
                    } else if (item.getPriceTypeId().equals(VALLEY)) {
                        periodRateNoList.add("03");
                    }
                }
            });
        }
        billingModelRespDto.setPeriodRateNoList(periodRateNoList);


        return billingModelRespDto;
    }


    @Override
    public List<CostTemplatePriceDO> getCostTemplatePriceByTemplateId(Long costTemplateId) {
        return costTemplatePriceMapper.selectList(new QueryWrapper<CostTemplatePriceDO>().lambda().eq(CostTemplatePriceDO::getCostTemplateId, costTemplateId));
    }

    @Override
    public List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeByTemplateId(Long costTemplateId) {
        return costTemplatePriceTypeMapper.selectList(new QueryWrapper<CostTemplatePriceTypeDO>().lambda().eq(CostTemplatePriceTypeDO::getCostTemplateId, costTemplateId));
    }


    //更新设备表,基于桩的登陆
    private void SaveEquipmentDOByPillLogin(PileLoginReqDto pileLoginReqDto, EquipmentDO equipmentDO) {
        //设备类型
        if (("00").equals(pileLoginReqDto.getPileType())) {
            equipmentDO.setCommunicationType(1);
        } else {
            equipmentDO.setCommunicationType(2);
        }
        //充电枪数量
        int i = Integer.parseInt(pileLoginReqDto.getChargingGunNum());
        equipmentDO.setEquipmentGunNumber(i);
        //通信协议版本 版本号乘 10，v1.0 表示 0x0A
        equipmentDO.setProtocolVersion(pileLoginReqDto.getProtocolVersion());
        //sim卡
        if (("00000000000000000000").equals(pileLoginReqDto.getSimId()) || StringUtils.isEmpty(pileLoginReqDto.getSimId())) {
            equipmentDO.setIsSim(false);
            equipmentDO.setNumberSim(null);
        } else {
            equipmentDO.setIsSim(true);
            equipmentDO.setNumberSim(removeLeadingZeros(pileLoginReqDto.getSimId()));
        }
        //程序版本号
        equipmentDO.setVersion(removeLeadingZeros(pileLoginReqDto.getProgramVersion()));
        //网络链接类型
        if ("00".equals(pileLoginReqDto.getNetworkLinkType())) {
            equipmentDO.setNetworkLinkType(1);
        } else if ("01".equals(pileLoginReqDto.getNetworkLinkType())) {
            equipmentDO.setNetworkLinkType(2);
        } else if ("02".equals(pileLoginReqDto.getNetworkLinkType())) {
            equipmentDO.setNetworkLinkType(3);
        } else {
            equipmentDO.setNetworkLinkType(4);
        }
        //运营商
        if (("00").equals(pileLoginReqDto.getSim())) {
            equipmentDO.setCommunicationOperator(1);
        } else if (("02").equals(pileLoginReqDto.getSim())) {
            equipmentDO.setCommunicationOperator(3);
        } else if (("03").equals(pileLoginReqDto.getSim())) {
            equipmentDO.setCommunicationOperator(2);
        } else {
            equipmentDO.setCommunicationOperator(0);
        }
        equipmentDO.setUpdateTime(LocalDateTime.now());
        equipmentDO.setUpdater("充电桩");
        equipmentDO.setSetPriceTime(LocalDateTime.now());
        equipmentMapper.updateById(equipmentDO);
    }


    public static String removeLeadingZeros(String str) {
        int length = str.length();
        int i = 0;

        // 找到第一个不为零的字符的位置
        while (i < length && str.charAt(i) == '0') {
            i++;
        }

        // 返回从第一个不为零的字符开始的子字符串
        return str.substring(i);
    }

    /**
     * @param equipmentDO
     * @return
     */
    private Integer getIsOnline(EquipmentDO equipmentDO) {
        Integer pileState = onlineEquipmentConnectorService.getPileState(equipmentDO.getEquipmentCode(), equipmentDO.getEquipmentGunNumber());
        return pileState == -1 ? 0 : 1;
    }

}
