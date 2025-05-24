package com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 电枪管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentConnectorPageReqVO extends PageParam {

    @Schema(description = "电站id", example = "5128")
    private Long stationId;

    @Schema(description = "运营商id", example = "6147")
    private Long orgId;

    @Schema(description = "操作人id", example = "25885")
    private Long operatorId;

    @Schema(description = "电枪编号")
    private String connectorCode;

    @Schema(description = "电枪简短编号")
    private String shortcutCode;

    @Schema(description = "电枪名称", example = "赵六")
    private String connectorName;

    @Schema(description = "是否启用")
    private Integer isEnableStart;

    @Schema(description = "工作状态", example = "1")
    private Integer workStatus;

    @Schema(description = "版本号")
    private String version;

    @Schema(description = "是否单独价格", example = "3249")
    private Boolean isAlonePrice;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operationDate;

}
