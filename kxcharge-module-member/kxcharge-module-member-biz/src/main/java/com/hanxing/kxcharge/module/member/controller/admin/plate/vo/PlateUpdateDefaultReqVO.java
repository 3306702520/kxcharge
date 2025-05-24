package com.hanxing.kxcharge.module.member.controller.admin.plate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 用户车牌信息更新 Request VO")
@Data
public class PlateUpdateDefaultReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1156")
    @NotNull(message = "主键不能为空")
    private Long id;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5797")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "是否为默认", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isDefault;

}
