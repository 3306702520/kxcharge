package com.hanxing.kxcharge.module.operations.api.interflow.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class NotificationChargeOrderInfoDetailsReqDto implements Serializable {


    private Long stationId;

    /**
     * 充电总度数
     */
    private BigDecimal chargedAllPower;
    /**
     * 折扣金额
     */
    private BigDecimal chargedDiscountPrice;
    /**
     * 充电度数
     */
    private BigDecimal chargedPower;
    /**
     * 充电金额
     */
    private BigDecimal chargedPrice;
    /**
     * 充电时间中文（46分钟）
     */
    private String chargedTime;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 金额类型
     */
    private String priceType;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 服务费折扣金额
     */
    private BigDecimal serviceDiscountPrice;
    /**
     * 服务费金额
     */
    private BigDecimal servicePrice;



}
