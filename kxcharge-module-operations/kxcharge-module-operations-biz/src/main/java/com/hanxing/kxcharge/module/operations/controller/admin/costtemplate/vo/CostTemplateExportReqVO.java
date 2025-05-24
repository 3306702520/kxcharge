package com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 计费模板 Excel 导出 Request VO，参数和 CostTemplatePageReqVO 是一致的")
@Data
public class CostTemplateExportReqVO {

    @Schema(description = "模板名称", example = "赵六")
    private String templateName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
