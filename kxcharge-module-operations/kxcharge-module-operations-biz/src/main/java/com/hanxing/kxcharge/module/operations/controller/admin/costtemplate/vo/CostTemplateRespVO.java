package com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.CostTemplatePriceRespVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.CostTemplatePriceTypeRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 计费模板 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CostTemplateRespVO extends CostTemplateBaseVO {

    @Schema(description = "主键", example = "0")
    private Long id;

    @Schema(description = "是否使用", example = "0")
    private Integer isUsed;

    @Schema(description = "运营商名称", example = "王五")
    private String orgName;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新人")
    private String updater;

    @Schema(description = "计费价格", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CostTemplatePriceRespVO> costTemplatePriceRespVOS;

    @Schema(description = "计费类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CostTemplatePriceTypeRespVO> costTemplatePriceTypeRespVOS;

}
