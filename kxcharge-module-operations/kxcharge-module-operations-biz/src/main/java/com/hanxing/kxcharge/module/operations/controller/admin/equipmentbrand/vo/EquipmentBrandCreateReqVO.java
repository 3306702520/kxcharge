package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 设备品牌管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentBrandCreateReqVO extends EquipmentBrandBaseVO {

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "21154")
    @NotNull(message = "运营商id不能为空")
    private Long orgId;

}
