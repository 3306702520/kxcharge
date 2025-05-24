package com.hanxing.kxcharge.module.operations.controller.admin.order.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hanxing.kxcharge.framework.excel.core.annotations.DictFormat;
import com.hanxing.kxcharge.framework.excel.core.convert.DictConvert;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 订单管理 Excel VO
 *
 * @author 小黑
 */
@Data
public class OrderExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("站点id")
    private Long stationId;

    @ExcelProperty("站点名称")
    private String stationName;

    @ExcelProperty("订单状态")
    private Integer orderStatus;

    @ExcelProperty(value = "订单类型", converter = DictConvert.class)
    @DictFormat("order_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String orderType;

    @ExcelProperty("运营商id")
    private Long orgId;

    @ExcelProperty("企业名称")
    private String companyName;

    @ExcelProperty("企业账户消费金额")
    private BigDecimal companyConsume;

    @ExcelProperty("个人消费")
    private BigDecimal personalConsume;

    @ExcelProperty("企业id")
    private Long companyId;

    @ExcelProperty("充电费用")
    private BigDecimal chargedAmount;

    @ExcelProperty("卡号")
    private String cardNo;

    @ExcelProperty("枪名称")
    private String gunName;

    @ExcelProperty("枪功率")
    private String pilePower;

    @ExcelProperty("付款类型")
    private String payType;

    @ExcelProperty("枪id")
    private String gunId;

    @ExcelProperty("充电度数")
    private BigDecimal chargedPower;

    @ExcelProperty("用户昵称")
    private String nickName;

    @ExcelProperty("用户账号")
    private String userAccount;

    @ExcelProperty("停车折扣费用")
    private String parkedDiscountAmount;

    @ExcelProperty("付款金额")
    private BigDecimal inPay;

    @ExcelProperty("开始soc")
    private String startSoc;

    @ExcelProperty("结束soc")
    private String endSoc;

    @ExcelProperty("收入")
    private BigDecimal income;

    @ExcelProperty("红包消费")
    private BigDecimal redPacketConsume;

    @ExcelProperty("停车费用")
    private BigDecimal parkedAmount;

    @ExcelProperty("卡类型")
    private String carType;

    @ExcelProperty("运营商总收入")
    private BigDecimal orgIncome;

    @ExcelProperty("开始充电")
    private LocalDateTime startTime;

    @ExcelProperty("站点运营商类型")
    private String stationOrgType;

    @ExcelProperty("车牌号")
    private String plateNumber;

    @ExcelProperty(value = "充电方式", converter = DictConvert.class)
    @DictFormat("charging_source") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer chargingSource;

    @ExcelProperty("电桩类型")
    private String pileType;

    @ExcelProperty("订单号")
    private String seqNumber;

    @ExcelProperty("结束充电时间")
    private LocalDateTime endTime;

    @ExcelProperty("结算类型")
    private String settleType;

    @ExcelProperty("车辆型号")
    private String carModel;

}
