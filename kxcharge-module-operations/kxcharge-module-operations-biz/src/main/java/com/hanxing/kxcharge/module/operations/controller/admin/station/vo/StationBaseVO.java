package com.hanxing.kxcharge.module.operations.controller.admin.station.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 站点信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class StationBaseVO {

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "31091")
    @NotNull(message = "运营商id不能为空")
    private Long orgId;

    @Schema(description = "电站名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "电站名称不能为空")
    private String stationName;

    @Schema(description = "省市区中文名", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "省市区中文名不能为空")
    private String areaCodeName;

    @Schema(description = "地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "地址不能为空")
    private String address;

    @Schema(description = "电站类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "电站类型不能为空")
    private Integer stationType;

    @Schema(description = "电站状态", example = "1")
    private Integer stationStatus;

    @Schema(description = "停车说明", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "停车说明不能为空")
    private String parkExplain;

    @Schema(description = "开放说明时间")
    private String openExplain;

    @Schema(description = "电站全景")
    private String imgFullView;

    @Schema(description = "是否启用电站")
    private Integer isEnableStart;

    @Schema(description = "场地属性", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "场地属性不能为空")
    private Integer property;

}
