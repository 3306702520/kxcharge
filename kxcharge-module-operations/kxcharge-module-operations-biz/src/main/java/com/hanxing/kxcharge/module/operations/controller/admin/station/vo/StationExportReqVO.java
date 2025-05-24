package com.hanxing.kxcharge.module.operations.controller.admin.station.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 站点信息 Excel 导出 Request VO，参数和 StationPageReqVO 是一致的")
@Data
public class StationExportReqVO {

    @Schema(description = "运营商id", example = "31091")
    private Long orgId;

    @Schema(description = "电站名称", example = "王五")
    private String stationName;

    @Schema(description = "电站状态", example = "1")
    private Integer stationStatus;

    @Schema(description = "场地属性")
    private Integer property;

}
