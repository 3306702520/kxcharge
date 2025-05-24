package com.hanxing.kxcharge.module.operations.controller.admin.home.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 经营数据 Request VO")
@Data
@ToString(callSuper = true)
public class TerminalOperationDataRespVo {

    @Schema(description = "充电量")
    private BigDecimal chargedPower;


    @Schema(description = "订单数")
    private BigDecimal orderCount;


    @Schema(description = "订单金额")
    private BigDecimal orderAmount;


    @Schema(description = "订单日期")
    private String dateTime;









}
