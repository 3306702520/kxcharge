package com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 订单详情管理 DO
 *
 * @author kaixin
 */
@TableName("operations_order_details")
@KeySequence("operations_order_details_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDO extends TenantBaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 充电总度数
     */
    private BigDecimal chargedAllPower;
    /**
     * 折扣金额
     */
    private BigDecimal chargedDiscountPrice;
    /**
     * 充电度数
     */
    private BigDecimal chargedPower;
    /**
     * 充电金额
     */
    private BigDecimal chargedPrice;
    /**
     * 充电时间中文（46分钟）
     */
    private String chargedTime;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 金额类型
     */
    private String priceType;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 服务费折扣金额
     */
    private BigDecimal serviceDiscountPrice;
    /**
     * 服务费金额
     */
    private BigDecimal servicePrice;


    private Long orgId;

}
