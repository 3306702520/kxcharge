package com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 计费模板价格时段 Excel 导出 Request VO，参数和 CostTemplatePricePageReqVO 是一致的")
@Data
public class CostTemplatePriceExportReqVO {

    @Schema(description = "模板id", example = "32699")
    private Long costTemplateId;

    @Schema(description = "更新版本")
    private String protocolVersion;

    @Schema(description = "运营商id", example = "16991")
    private Long orgId;

    @Schema(description = "48段开始", example = "1")
    private Integer startTime;

    @Schema(description = "48段结束", example = "2")
    private Integer endTime;

    @Schema(description = "开始时间")
    private String startTimeStr;

    @Schema(description = "结束时间")
    private String endTimeStr;

    @Schema(description = "价格类型id", example = "10204")
    private String priceTypeId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
