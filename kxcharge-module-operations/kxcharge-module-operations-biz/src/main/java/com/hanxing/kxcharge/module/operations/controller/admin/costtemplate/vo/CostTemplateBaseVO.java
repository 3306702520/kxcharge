package com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 计费模板 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CostTemplateBaseVO {

    @Schema(description = "模板名称", example = "赵六")
    private String templateName;

    @Schema(description = "运营商ID", example = "10")
    private Long orgId;

    @Schema(description = "备注", example = "这是备注")
    private String remark;



}
