package com.hanxing.kxcharge.module.operations.api.order.dto;

import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationChargeOrderInfoDetailsReqDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderInfoRespDto implements Serializable {


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 站点id
     */
    private Long stationId;
    /**
     * 站点名称
     */
    private String stationName;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 订单类型
     * <p>
     * 枚举 {@link TODO order_type 对应的类}
     */
    private String orderType;
    /**
     * 运营商id
     */
    private Long orgId;
    /**
     * 企业名称
     */
    private String companyName;
    /**
     * 企业账户消费金额
     */
    private BigDecimal companyConsume;
    /**
     * 个人消费
     */
    private BigDecimal personalConsume;
    /**
     * 企业id
     */
    private Long companyId;
    /**
     * 充电费用
     */
    private BigDecimal chargedAmount;
    /**
     * 充电时间中文（列如：4分钟）
     */
    private String chargedTime;
    /**
     * 卡号
     */
    private String cardNo;
    /**
     * 付款人
     */
    private String payer;
    /**
     * 运营商充电收费金额
     */
    private BigDecimal orgChargedAmount;
    /**
     * 运营商停车收费金额
     */
    private BigDecimal orgParkedAmount;
    /**
     * 运营商收入总费用
     */
    private String orgAllAmount;
    /**
     * 运营商电费实际收入
     */
    private BigDecimal orgElecActualIncome;
    /**
     * 运营商实际服务费收入
     */
    private BigDecimal orgServActualIncome;
    /**
     * 枪名称
     */
    private String gunName;
    /**
     * 枪功率
     */
    private String pilePower;
    /**
     * 付款类型
     */
    private String payType;
    /**
     * 枪id
     */
    private String gunId;
    /**
     * 中电联订单号
     */
    private String tradeSeq;
    /**
     * 中电联订单状态
     */
    private String tradeStatus;
    /**
     * 中电联订单时间
     */
    private LocalDateTime tradeTime;
    /**
     * 充电度数
     */
    private BigDecimal chargedPower;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户账号
     */
    private String userAccount;
    /**
     * 停车折扣费用
     */
    private String parkedDiscountAmount;
    /**
     * 付款金额
     */
    private BigDecimal inPay;
    /**
     * 开始soc
     */
    private String startSoc;
    /**
     * 结束soc
     */
    private String endSoc;
    /**
     * 收入
     */
    private BigDecimal income;
    /**
     * 红包消费
     */
    private BigDecimal redPacketConsume;
    /**
     * 停车费用
     */
    private BigDecimal parkedAmount;
    /**
     * 卡类型
     */
    private String carType;
    /**
     * 运营商总收入
     */
    private BigDecimal orgIncome;
    /**
     * 开始充电
     */
    private LocalDateTime startTime;
    /**
     * 站点运营商类型
     */
    private String stationOrgType;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 充电来源
     * <p>
     * 枚举 {@link TODO charging_source 对应的类}
     */
    private Integer chargingSource;
    /**
     * 充电方式
     * <p>
     * 枚举 {@link TODO charging_source 对应的类}
     */
    private Integer chargingWay;
    /**
     * 充电总金额
     */
    private BigDecimal allAmount;
    /**
     * 充电折扣金额
     */
    private String chargedDiscountAmount;
    /**
     * 电桩类型
     */
    private String pileType;
    /**
     * 订单号
     */
    private String seqNumber;
    /**
     * 结束充电时间
     */
    private LocalDateTime endTime;
    /**
     * 结算类型
     */
    private String settleType;
    /**
     * 车辆型号
     */
    private String carModel;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 失败中文描述
     */
    private String stopReasonMsg;

    /**
     * 失败类型
     */
    private Integer stopReason;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    /**
     * 详情
     */
    private List<NotificationChargeOrderInfoDetailsReqDto> detailsReqDtoList;

}
