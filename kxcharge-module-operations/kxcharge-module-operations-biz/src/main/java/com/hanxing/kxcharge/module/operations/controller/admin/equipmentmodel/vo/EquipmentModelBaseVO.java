package com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 设备型号管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EquipmentModelBaseVO {

    @Schema(description = "品牌id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15050")
    @NotNull(message = "品牌id不能为空")
    private Long equipmentBrandId;

    @Schema(description = "电站型号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "电站型号不能为空")
    private String equipmentModel;

    @Schema(description = "充电枪数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "充电枪数量不能为空")
    private Integer number;

    @Schema(description = "电桩类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "电桩类型不能为空")
    private Integer type;

    @Schema(description = "电桩功率", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "电桩功率不能为空")
    private BigDecimal power;

}
