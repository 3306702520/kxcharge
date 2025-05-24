package com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.CostTemplatePriceCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.CostTemplatePriceTypeCreateReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 计费模板创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CostTemplateCreateReqVO extends CostTemplateBaseVO {

    @Schema(description = "计费价格")
    private List<CostTemplatePriceCreateReqVO> costTemplatePriceRespVOS;

    @Schema(description = "计费类型")
    private List<CostTemplatePriceTypeCreateReqVO> costTemplatePriceTypeRespVOS;

}
