package com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Schema(description = "管理后台 - 订单详情管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetailsPageReqVO extends PageParam {

    @Schema(description = "充电总度数")
    private BigDecimal chargedAllPower;

    @Schema(description = "折扣金额", example = "18832")
    private BigDecimal chargedDiscountPrice;

    @Schema(description = "充电度数")
    private BigDecimal chargedPower;

    @Schema(description = "充电金额", example = "2358")
    private BigDecimal chargedPrice;

    @Schema(description = "充电时间中文（46分钟）")
    private String[] chargedTime;

    @Schema(description = "开始时间")
    private String[] startTime;

    @Schema(description = "结束时间")
    private String[] endTime;

    @Schema(description = "订单金额")
    private BigDecimal orderAmount;

    @Schema(description = "金额类型", example = "2")
    private String priceType;

    @Schema(description = "订单id", example = "16510")
    private String orderId;

    @Schema(description = "服务费折扣金额", example = "26762")
    private BigDecimal serviceDiscountPrice;

    @Schema(description = "服务费金额", example = "6006")
    private BigDecimal servicePrice;

}
