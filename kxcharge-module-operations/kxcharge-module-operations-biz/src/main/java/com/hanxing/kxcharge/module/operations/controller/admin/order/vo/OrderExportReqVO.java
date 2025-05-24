package com.hanxing.kxcharge.module.operations.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 订单管理 Excel 导出 Request VO，参数和 OrderPageReqVO 是一致的")
@Data
public class OrderExportReqVO {

    @Schema(description = "站点id", example = "12990")
    private Long stationId;

    @Schema(description = "站点名称", example = "李四")
    private String stationName;

    @Schema(description = "订单状态", example = "1")
    private Integer orderStatus;

    @Schema(description = "订单类型", example = "1")
    private String orderType;

    @Schema(description = "运营商id", example = "23488")
    private Long orgId;

    @Schema(description = "企业名称", example = "芋艿")
    private String companyName;

    @Schema(description = "企业账户消费金额")
    private BigDecimal companyConsume;

    @Schema(description = "个人消费")
    private BigDecimal personalConsume;

    @Schema(description = "企业id", example = "26685")
    private Long companyId;

    @Schema(description = "充电费用")
    private BigDecimal chargedAmount;

    @Schema(description = "充电时间中文（列如：4分钟）")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private String[] chargedTime;

    @Schema(description = "卡号")
    private String cardNo;

    @Schema(description = "付款人")
    private String payer;

    @Schema(description = "充电度数")
    private BigDecimal chargedPower;

    @Schema(description = "用户昵称", example = "赵六")
    private String nickName;

    @Schema(description = "用户账号", example = "17821")
    private String userAccount;

    @Schema(description = "停车折扣费用")
    private String parkedDiscountAmount;

    @Schema(description = "付款金额")
    private BigDecimal inPay;

    @Schema(description = "开始soc")
    private String startSoc;

    @Schema(description = "结束soc")
    private String endSoc;

    @Schema(description = "卡类型", example = "2")
    private String carType;

    @Schema(description = "开始充电")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "站点运营商类型", example = "1")
    private String stationOrgType;

    @Schema(description = "车牌号")
    private String plateNumber;

    @Schema(description = "充电方式")
    private Integer chargingSource;

    @Schema(description = "电桩类型", example = "2")
    private String pileType;

    @Schema(description = "订单号")
    private String seqNumber;

    @Schema(description = "结束充电时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

}
