package com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 计费模板价格类别 Excel 导出 Request VO，参数和 CostTemplatePriceTypePageReqVO 是一致的")
@Data
public class CostTemplatePriceTypeExportReqVO {

    @Schema(description = "模板id", example = "29442")
    private Long costTemplateId;

    @Schema(description = "更新版本")
    private String protocolVersion;

    @Schema(description = "运营商id", example = "18591")
    private Long orgId;

    @Schema(description = "排序")
    private Integer identification;

    @Schema(description = "类型名称", example = "张三")
    private String typeName;

    @Schema(description = "电费价格")
    private BigDecimal pricePower;

    @Schema(description = "服务价格")
    private BigDecimal priceService;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
