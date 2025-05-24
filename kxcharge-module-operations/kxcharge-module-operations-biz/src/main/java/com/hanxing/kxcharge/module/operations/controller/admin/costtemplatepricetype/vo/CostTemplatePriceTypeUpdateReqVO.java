package com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 计费模板价格类别更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CostTemplatePriceTypeUpdateReqVO extends CostTemplatePriceTypeBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21229")
    @NotNull(message = "主键不能为空")
    private Long id;

}
