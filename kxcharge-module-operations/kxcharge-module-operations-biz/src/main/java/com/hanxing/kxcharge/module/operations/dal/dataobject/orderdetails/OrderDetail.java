package com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetail {
    // 计价类型id
    private String priceTypeId;
    // 开始时间
    private long startTime;
    // 结束时间
    private long endTime;
    // 充电度数
    private BigDecimal chargedPower;
    // 充电金额
    private BigDecimal chargedPrice;
    //充电时间中文
    private int chargedTime;
    //服务费金额
    private BigDecimal servicePrice;

}
