package com.hanxing.kxcharge.module.operations.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 订单管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderCreateReqVO extends OrderBaseVO {

    @Schema(description = "充电时间中文（列如：4分钟）")
    private String chargedTime;

    @Schema(description = "付款人")
    private String payer;

    @Schema(description = "运营商充电收费金额")
    private BigDecimal orgChargedAmount;

    @Schema(description = "运营商停车收费金额")
    private BigDecimal orgParkedAmount;

    @Schema(description = "运营商收入总费用")
    private String orgAllAmount;

    @Schema(description = "运营商电费实际收入")
    private BigDecimal orgElecActualIncome;

    @Schema(description = "运营商实际服务费收入")
    private BigDecimal orgServActualIncome;

    @Schema(description = "中电联订单号")
    private String tradeSeq;

    @Schema(description = "中电联订单状态", example = "1")
    private String tradeStatus;

    @Schema(description = "中电联订单时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime tradeTime;

    @Schema(description = "充电总金额")
    private BigDecimal allAmount;

    @Schema(description = "充电折扣金额")
    private String chargedDiscountAmount;

}
