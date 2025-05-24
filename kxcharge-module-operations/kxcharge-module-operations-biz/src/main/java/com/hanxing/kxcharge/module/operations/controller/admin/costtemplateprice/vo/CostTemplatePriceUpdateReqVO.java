package com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 计费模板价格时段更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CostTemplatePriceUpdateReqVO extends CostTemplatePriceBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "26472")
    @NotNull(message = "主键不能为空")
    private Long id;

}
