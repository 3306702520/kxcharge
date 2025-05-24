package com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 计费模板价格类别 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CostTemplatePriceTypeBaseVO {

    @Schema(description = "模板id", example = "29442")
    private Long costTemplateId;

    @Schema(description = "更新版本")
    private String protocolVersion;

    @Schema(description = "运营商id", example = "18591")
    private Long orgId;

    @Schema(description = "排序")
    private Integer identification;

    @Schema(description = "类型名称", example = "张三")
    private String typeName;

    @Schema(description = "电费价格")
    private BigDecimal pricePower;

    @Schema(description = "服务价格")
    private BigDecimal priceService;

}
