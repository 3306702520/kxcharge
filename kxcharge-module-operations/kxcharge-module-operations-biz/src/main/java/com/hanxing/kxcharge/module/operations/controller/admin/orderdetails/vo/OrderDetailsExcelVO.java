package com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单详情管理 Excel VO
 *
 * @author kaixin
 */
@Data
public class OrderDetailsExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("充电总度数")
    private BigDecimal chargedAllPower;

    @ExcelProperty("折扣金额")
    private BigDecimal chargedDiscountPrice;

    @ExcelProperty("充电度数")
    private BigDecimal chargedPower;

    @ExcelProperty("充电金额")
    private BigDecimal chargedPrice;

    @ExcelProperty("充电时间中文（46分钟）")
    private String chargedTime;

    @ExcelProperty("开始时间")
    private String startTime;

    @ExcelProperty("结束时间")
    private String endTime;

    @ExcelProperty("订单金额")
    private BigDecimal orderAmount;

    @ExcelProperty("金额类型")
    private String priceType;

    @ExcelProperty("订单id")
    private String orderId;

    @ExcelProperty("服务费折扣金额")
    private BigDecimal serviceDiscountPrice;

    @ExcelProperty("服务费金额")
    private BigDecimal servicePrice;

}
