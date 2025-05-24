package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 设备品牌管理更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentBrandUpdateReqVO extends EquipmentBrandBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "3371")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21154")
    @NotNull(message = "运营商id不能为空")
    private Long orgId;

}
