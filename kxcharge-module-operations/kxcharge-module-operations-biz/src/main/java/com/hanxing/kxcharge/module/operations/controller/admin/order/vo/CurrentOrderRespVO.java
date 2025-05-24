package com.hanxing.kxcharge.module.operations.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString(callSuper = true)
@Schema(description = "管理后台 - 实时订单 Response VO")
public class CurrentOrderRespVO implements Serializable {
    @Schema(description = "序号")
    private Integer no;

    @Schema(description = "充电单号")
    private String seqNumber;

    @Schema(description = "用户账户")
    private Long userId;

    @Schema(description = "订单状态", example = "1")
    private Integer orderStatus;

    @Schema(description = "用户手机号")
    private String userPhone;

    @Schema(description = "企业名称")
    private String companyName;

    @Schema(description = "企业ID")
    private Long companyId;

    @Schema(description = "充电车辆")
    private String carNumber;

    @Schema(description = "充电时长(min)")
    private String chargeTime;

    @Schema(description = "已充电量")
    private BigDecimal chargedPower;

    @Schema(description = "已充费用")
    private BigDecimal chargedAmount;


    @Schema(description = "充电折扣金额")
    private BigDecimal chargedDiscountAmount;

    @Schema(description = "红包消费/优惠券金额")
    private BigDecimal redPacketConsume;

    @Schema(description = "充电总金额")
    private BigDecimal allAmount;

    @Schema(description = "付款金额")
    private BigDecimal inPay;



    @Schema(description = "输出电流")
    private BigDecimal outputCurrent;

    @Schema(description = "输出电压")
    private BigDecimal outputVoltage;


    @Schema(description = "充电失败原因中文")
    private String stopReasonMsg;


    @Schema(description = "当前soc")
    private String soc;

    @Schema(description = "电站id")
    private Long stationId;

    @Schema(description = "电站名称")
    private String stationName;


    @Schema(description = "地址")
    private String address;

    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal stationLng;
    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal stationLat;


    @Schema(description = "终端编码")
    private String equipmentConnectorCode;

    @Schema(description = "电桩编码")
    private String equipmentCode;

    @Schema(description = "运营商id")
    private Long orgId;

    @Schema(description = "订单来源")
    private Integer chargingSource;

    @Schema(description = "充电方式")
    private Integer chargingWay;

    @Schema(description = "开始充电时间")
    private LocalDateTime startTime;

    @Schema(description = "结束充电时间")
    private LocalDateTime endTime;

    @Schema(description = "终端名称")
    private String equipmentConnectorName;

    @Schema(description = "设备型号类型")
    private String equipmentModelType;

    @Schema(description = "电枪功率")
    private String equipmentConnectorPower;


    /**
     *
     */
    @Schema(description = "订单详情对象")
    private List<OrderDetailsVO> orderDetailsRespVOList;

    @Schema(description = "充电状态")
    private Integer chargeStatus;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    @Schema(description = "用户账号")
    private Long userAccount;

    @Schema(description = "用户昵称")
    private String userName;

    @Schema(description = "充电卡号")
    private String cardNo;

    @Schema(description = "订单类型")
    private String orderType;

    @Schema(description = "电枪功率")
    private Integer gunPower;

    @Schema(description = "运营商名称")
    private String operatorName;


    @Schema(description = "服务费金额")
    private BigDecimal serviceAmount;

    @Schema(description = "错误信息")
    private String errorMessage;

    @Schema(description = "付款时间")
    private String payTime;

    @Schema(description = "电桩类型")
    private String communicationType;

    @Schema(description = "失败原因")
    private Integer stopReason;
}
