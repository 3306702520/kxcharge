package com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 订单详情管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class OrderDetailsBaseVO {

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23488")
    @NotNull(message = "运营商id不能为空")
    private Long orgId;

    @Schema(description = "充电总度数")
    private BigDecimal chargedAllPower;

    @Schema(description = "折扣金额", example = "18832")
    private BigDecimal chargedDiscountPrice;

    @Schema(description = "充电度数")
    private BigDecimal chargedPower;

    @Schema(description = "充电金额", example = "2358")
    private BigDecimal chargedPrice;

    @Schema(description = "充电时间中文（46分钟）")
    private String chargedTime;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

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
