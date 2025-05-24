package com.hanxing.kxcharge.module.operations.service.home;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.ChargeDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.GunDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TerminalOperationDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TimeDistributionDataRespVo;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipmentconnector.EquipmentConnectorMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.order.OrderMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.orderdetails.OrderDetailsMapper;
import com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector.OnlineEquipmentConnectorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页报表接口
 */
@Service
public class HomeServiceImpl implements HomeService {


    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    @Resource
    private EquipmentConnectorMapper equipmentConnectorMapper;

    @Resource
    private OnlineEquipmentConnectorService onlineEquipmentConnectorService;


    /**
     * 充电户数
     *
     * @return
     */
    @Override
    public ChargeDataRespVo chargeData() {
        return orderMapper.chargeData();
    }


    @Override
    public GunDataRespVo gunData() {

        GunDataRespVo vo = new GunDataRespVo();

        //设置电枪列表
        List<EquipmentConnectorDO> equipmentConnectorDOS = equipmentConnectorMapper.selectList(new QueryWrapper<EquipmentConnectorDO>().lambda());


        int outOfUseCount = (int) equipmentConnectorDOS.stream()
                .filter(connector ->
                        connector.getIsEnableStart() == 0)
                .count(); // 停用数量

        vo.setOutOfUse(outOfUseCount);


        int chargingCount = (int) equipmentConnectorDOS.stream()
                .filter(connector ->
                        connector.getIsEnableStart() == 1 &&
                                onlineEquipmentConnectorService.getGunWorkState(connector.getConnectorCode()) == 3)
                .count(); // 充电中数量

        vo.setCharging(chargingCount);


        int atLeisureCount = (int) equipmentConnectorDOS.stream()
                .filter(connector ->
                        connector.getIsEnableStart() == 1 &&
                                onlineEquipmentConnectorService.getGunWorkState(connector.getConnectorCode()) == 2
                                && onlineEquipmentConnectorService.getInsertArmsState(connector.getConnectorCode()) == 0)
                .count(); // 空闲中

        vo.setAtLeisure(atLeisureCount);


        int inTroubleCount = (int) equipmentConnectorDOS.stream()
                .filter(connector ->
                        connector.getIsEnableStart() == 1 &&
                                (onlineEquipmentConnectorService.getGunWorkState(connector.getConnectorCode()) == 1
                                        || onlineEquipmentConnectorService.getGunWorkState(connector.getConnectorCode()) == 4))
                .count(); // 故障中

        vo.setInTrouble(inTroubleCount);


        int inUseCount = (int) equipmentConnectorDOS.stream()
                .filter(connector ->
                        connector.getIsEnableStart() == 1 &&
                                (onlineEquipmentConnectorService.getGunWorkState(connector.getConnectorCode()) == 2
                                        && onlineEquipmentConnectorService.getInsertArmsState(connector.getConnectorCode()) == 1))
                .count(); // 占用中

        vo.setInUse(inUseCount);


        int offLineCount = (int) equipmentConnectorDOS.stream()
                .filter(connector ->
                        connector.getIsEnableStart() == 1 &&
                                onlineEquipmentConnectorService.getGunWorkState(connector.getConnectorCode()) == 0)
                .count(); // 离线中

        vo.setOffLine(offLineCount);

        return vo;
    }

    @Override
    public List<TerminalOperationDataRespVo> terminalOperationData(Integer type) {

        if (type == 1 || type == 2) {
            return orderMapper.selectListByToday(type);
        }
        if (type == 3) {
            return orderMapper.selectListByMonth(type);
        }
        if (type == 4) {
            return orderMapper.selectListByYear(type);
        }
        return new ArrayList<>();
    }

    @Override
    public List<TimeDistributionDataRespVo> timeDistribution(Integer type) {

        return orderDetailsMapper.selectListByCustom(type);

    }
}
