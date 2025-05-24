package com.hanxing.kxcharge.module.operations.controller.admin.station.vo;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 站点信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StationPageReqVO extends PageParam {

    @Schema(description = "运营商id", example = "31091")
    private Long orgId;

    @Schema(description = "电站名称", example = "王五")
    private String stationName;

    @Schema(description = "电站状态", example = "1")
    private Integer stationStatus;

    @Schema(description = "场地属性")
    private Integer property;

}
