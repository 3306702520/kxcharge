package com.hanxing.kxcharge.module.operations.service.realtimeorder;

import com.hanxing.kxcharge.module.operations.dal.dataobject.realtimeorder.RealTimeOrderDO;

public interface RealTimeOrderService {


    void save(RealTimeOrderDO realTimeOrderDO);


    RealTimeOrderDO get(String transactionSerialNo);
}
