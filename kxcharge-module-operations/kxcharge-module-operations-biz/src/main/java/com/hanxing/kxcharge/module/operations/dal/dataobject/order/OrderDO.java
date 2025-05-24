package com.hanxing.kxcharge.module.operations.dal.dataobject.order;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单管理 DO
 *
 * @author 小黑
 */
@TableName("operations_order")
@KeySequence("operations_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 站点id
     */
    private Long stationId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 站点名称
     */
    private String stationName;

    /**
     *  订单状态  1 启动中 2 充电中 3停止中 4 已结束 5 未知
     */
    private Integer orderStatus;
    /**
     * 充电状态 1:开启充电
     *        2:收到开始成功开始充电命令,正式充电 一张表中，只能有一个枪号下只能有一个订单是在正式充电的状态
     *        3:结束充电
     *        4:收到结束充电回复报文，
     *        5:收到结束充电,
     *        6:收到订单信息,正式结束
     *        7:开启充电失败
     *        8:结束充电失败
     */
    private Integer chargeStatus;
    /**
     * 订单类型
     *
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
     * 支付时间
     */
    private LocalDateTime payTime;
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
     * 付款金额,(以元为单位)
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
     *
     * 枚举 {@link TODO charging_source 对应的类}
     */
    private Integer chargingSource;
    /**
     * 充电方式
     *
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



}
