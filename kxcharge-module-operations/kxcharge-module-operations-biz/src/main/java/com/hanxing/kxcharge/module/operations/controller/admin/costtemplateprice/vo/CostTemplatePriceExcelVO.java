package com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 计费模板价格时段 Excel VO
 *
 * @author 航兴新能源
 */
@Data
public class CostTemplatePriceExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("模板id")
    private Long costTemplateId;

    @ExcelProperty("更新版本")
    private String protocolVersion;

    @ExcelProperty("运营商id")
    private Long orgId;

    @ExcelProperty("48段开始")
    private Integer startTime;

    @ExcelProperty("48段结束")
    private Integer endTime;

    @ExcelProperty("开始时间")
    private String startTimeStr;

    @ExcelProperty("结束时间")
    private String endTimeStr;

    @ExcelProperty("价格类型id")
    private String priceTypeId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
