package com.hanxing.kxcharge.module.operations.service.realtimeorder;

import com.hanxing.kxcharge.module.operations.dal.dataobject.realtimeorder.RealTimeOrderDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.order.OrderMapper;
import com.hanxing.kxcharge.module.operations.dal.redis.realtimeorder.RealTimeOrderDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RealTimeOrderServiceImpl implements RealTimeOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    RealTimeOrderDao realTimeOrderDao;


    @Override
    public void save(RealTimeOrderDO realTimeOrderDO) {
        realTimeOrderDao.set(realTimeOrderDO);
        //更新soc
        updateDatabaseForOrderId(realTimeOrderDO);
    }

    //更新数据
    private void updateDatabaseForOrderId(RealTimeOrderDO realTimeOrderDO) {
//        OrderDO orderDO = new OrderDO();
//        //订单实时订单数据更新
//        //充电度数
//        orderDO.setChargedPower(realTimeOrderDO.getChargingDegree());
//        //充电服务费
//        orderDO.setAllAmount(realTimeOrderDO.getChargedAmount());
//        //开始soc
//        orderDO.setStartSoc(realTimeOrderDO.getSOC());
//        //结束soc
//        orderDO.setEndSoc(realTimeOrderDO.getSOC());
        //soc
        orderMapper.updateBySoc(realTimeOrderDO.getSOC(), realTimeOrderDO.getTransactionSerialNo());
    }

    @Override
    public RealTimeOrderDO get(String transactionSerialNo) {
        return realTimeOrderDao.get(transactionSerialNo);
    }
}
