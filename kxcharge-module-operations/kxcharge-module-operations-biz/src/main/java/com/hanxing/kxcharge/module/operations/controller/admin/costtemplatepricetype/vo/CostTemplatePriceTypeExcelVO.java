package com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 计费模板价格类别 Excel VO
 *
 * @author kaixin
 */
@Data
public class CostTemplatePriceTypeExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("模板id")
    private Long costTemplateId;

    @ExcelProperty("更新版本")
    private String protocolVersion;

    @ExcelProperty("运营商id")
    private Long orgId;

    @ExcelProperty("排序")
    private Integer identification;

    @ExcelProperty("类型名称")
    private String typeName;

    @ExcelProperty("电费价格")
    private BigDecimal pricePower;

    @ExcelProperty("服务价格")
    private BigDecimal priceService;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
