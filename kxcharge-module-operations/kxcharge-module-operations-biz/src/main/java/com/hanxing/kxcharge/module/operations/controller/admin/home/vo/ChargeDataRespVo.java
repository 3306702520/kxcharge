package com.hanxing.kxcharge.module.operations.controller.admin.home.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 充电数据 Request VO")
@Data
@ToString(callSuper = true)
public class ChargeDataRespVo {

    @Schema(description = "充电量")
    private BigDecimal todayChargedPower;
    private BigDecimal yesterdayChargedPower;


    @Schema(description = "订单数")
    private BigDecimal todayOrderCount;
    private BigDecimal yesterdayOrderCount;


    @Schema(description = "订单应收金额")
    private BigDecimal todayOrderReceivableAmount;
    private BigDecimal yesterdayOrderReceivableAmount;


    @Schema(description = "订单电费金额")
    private BigDecimal todayOrderReceivableElectricity;
    private BigDecimal yesterdayOrderReceivableElectricity;

    @Schema(description = "订单服务费金额")
    private BigDecimal todayOrderReceivableService;
    private BigDecimal yesterdayOrderReceivableService;



}
