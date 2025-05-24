package com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 电桩管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EquipmentBaseVO {

    @Schema(description = "电桩名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "电桩名称不能为空")
    private String equipmentName;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @Length(min = 14, max = 14, message = "设备编码只能是 14 位")
    @Pattern(regexp = "^[0-9a-fA-F]{14}$", message = "匹配 14 位长的 16 进制字符串")
    @NotNull(message = "设备编码不能为空")
    private String equipmentCode;

    @Schema(description = "是否在线", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isOnline;

    @Schema(description = "电站id", requiredMode = Schema.RequiredMode.REQUIRED, example = "10690")
    @NotNull(message = "电站id不能为空")
    private Long stationId;

    @Schema(description = "校价id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26849")
    private Long operatorId;

}
