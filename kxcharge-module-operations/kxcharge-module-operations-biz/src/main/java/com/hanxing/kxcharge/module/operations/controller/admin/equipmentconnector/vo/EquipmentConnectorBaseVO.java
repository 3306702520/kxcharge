package com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 电枪管理 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EquipmentConnectorBaseVO {

    @Schema(description = "电站id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5128")
    @NotNull(message = "电站id不能为空")
    private Long stationId;

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6147")
    @NotNull(message = "运营商id不能为空")
    private Long orgId;

    @Schema(description = "操作人id", requiredMode = Schema.RequiredMode.REQUIRED, example = "25885")
    @NotNull(message = "操作人id不能为空")
    private Long operatorId;

    @Schema(description = "电枪编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "电枪编号不能为空")
    private String connectorCode;

    @Schema(description = "电枪简短编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "电枪简短编号不能为空")
    private String shortcutCode;

    @Schema(description = "电枪名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "电枪名称不能为空")
    private String connectorName;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否启用不能为空")
    private Integer isEnableStart;

    @Schema(description = "工作状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "工作状态不能为空")
    private Integer workStatus;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "版本号不能为空")
    private String version;

    @Schema(description = "是否单独价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "3249")
    @NotNull(message = "是否单独价格不能为空")
    private Boolean isAlonePrice;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime operationDate;

}
