package com.hanxing.kxcharge.module.operations.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 订单管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class OrderBaseVO {

    @Schema(description = "站点id", example = "12990")
    private Long stationId;

    @Schema(description = "站点名称", example = "李四")
    private String stationName;

    @Schema(description = "订单状态", example = "1")
    private Integer orderStatus;

    @Schema(description = "订单类型", example = "1")
    private String orderType;

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23488")
    @NotNull(message = "运营商id不能为空")
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


    @Schema(description = "充电时间(中文)")
    private String chargedTime;


    @Schema(description = "订单支付时间")
    private LocalDateTime payTime;

    @Schema(description = "充电方式")
    private Integer chargingWay;

    @Schema(description = "卡号")
    private String cardNo;

    @Schema(description = "枪名称", example = "张三")
    private String gunName;

    @Schema(description = "枪功率")
    private String pilePower;

    @Schema(description = "付款类型", example = "1")
    private String payType;

    @Schema(description = "枪id", example = "3767")
    private String gunId;

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

    @Schema(description = "收入")
    private BigDecimal income;

    @Schema(description = "红包消费")
    private BigDecimal redPacketConsume;

    @Schema(description = "停车费用")
    private BigDecimal parkedAmount;

    @Schema(description = "卡类型", example = "2")
    private String carType;

    @Schema(description = "运营商总收入")
    private BigDecimal orgIncome;

    @Schema(description = "开始充电")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

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
    private LocalDateTime endTime;

    @Schema(description = "结算类型", example = "2")
    private String settleType;

    @Schema(description = "车辆型号")

    private String carModel;

    @Schema(description = "订单金额")
    private BigDecimal allAmount;

    @Schema(description = "用户手机号")
    private String mobile;



}
