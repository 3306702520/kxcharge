package com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 电桩管理 Excel 导出 Request VO，参数和 EquipmentPageReqVO 是一致的")
@Data
public class EquipmentExportReqVO {

    @Schema(description = "电桩名称", example = "李四")
    private String equipmentName;

    @Schema(description = "设备编码")
    private String equipmentCode;

    @Schema(description = "电站id", example = "10690")
    private Long stationId;

    @Schema(description = "校价id", example = "26849")
    private Long operatorId;

}
