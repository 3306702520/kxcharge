package com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 型号节点 Response VO")
@Data
public class EquipmentModelTreeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Long id;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京")
    private String name;

}
