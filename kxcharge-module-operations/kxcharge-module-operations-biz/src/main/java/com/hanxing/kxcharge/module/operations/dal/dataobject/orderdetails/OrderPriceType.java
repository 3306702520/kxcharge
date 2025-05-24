package com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderPriceType {
    // 充电度数
    private BigDecimal chargedPower;
    // 金额(电费+服务费)
    private BigDecimal price;

}
