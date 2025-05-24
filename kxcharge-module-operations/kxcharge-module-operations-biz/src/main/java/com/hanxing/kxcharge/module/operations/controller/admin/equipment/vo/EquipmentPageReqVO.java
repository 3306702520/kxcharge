package com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 电桩管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentPageReqVO extends PageParam {

    @Schema(description = "电桩名称", example = "李四")
    private String equipmentName;

    @Schema(description = "设备编码")
    private String equipmentCode;

    @Schema(description = "电站id", example = "10690")
    private Long stationId;

    @Schema(description = "校价id", example = "26849")
    private Long operatorId;

}
