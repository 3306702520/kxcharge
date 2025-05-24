package com.hanxing.kxcharge.module.operations.controller.admin.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDetailsVO {
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
    private Long startTime;
    /**
     * 结束时间
     */
    private Long endTime;
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


    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    private String creator;
    /**
     * 更新者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    private String updater;
    /**
     * 是否删除
     */
    private Boolean deleted;
}
