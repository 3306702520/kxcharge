package com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 电枪管理更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentConnectorUpdateReqVO extends EquipmentConnectorBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30657")
    @NotNull(message = "主键不能为空")
    private Long id;

}
