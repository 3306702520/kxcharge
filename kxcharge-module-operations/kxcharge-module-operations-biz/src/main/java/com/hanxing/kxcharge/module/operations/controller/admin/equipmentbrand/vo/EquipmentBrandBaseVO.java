package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 设备品牌管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EquipmentBrandBaseVO {

    @Schema(description = "品牌名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "品牌名称不能为空")
    private String brandName;

}
