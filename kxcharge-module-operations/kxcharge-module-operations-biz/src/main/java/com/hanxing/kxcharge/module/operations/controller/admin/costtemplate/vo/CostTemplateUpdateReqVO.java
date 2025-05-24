package com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.CostTemplatePriceCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.CostTemplatePriceTypeCreateReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 计费模板更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CostTemplateUpdateReqVO extends CostTemplateBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "4432")
    @NotNull(message = "主键不能为空")
    private Long id;


    @Schema(description = "计费价格")
    private List<CostTemplatePriceCreateReqVO> costTemplatePriceRespVOS;

    @Schema(description = "计费类型")
    private List<CostTemplatePriceTypeCreateReqVO> costTemplatePriceTypeRespVOS;

}
