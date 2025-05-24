package com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hanxing.kxcharge.framework.excel.core.annotations.DictFormat;
import com.hanxing.kxcharge.framework.excel.core.convert.DictConvert;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 计费模板 Excel VO
 *
 * @author kaixin
 */
@Data
public class CostTemplateExcelVO {

    @ExcelProperty("模板名称")
    private String templateName;

    @ExcelProperty(value = "是否使用", converter = DictConvert.class)
    @DictFormat("cost_template_used") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer isUsed;

    @ExcelProperty("运营商名称")
    private String orgName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
