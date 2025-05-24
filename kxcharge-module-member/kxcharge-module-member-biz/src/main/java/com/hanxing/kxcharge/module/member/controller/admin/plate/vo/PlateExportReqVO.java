package com.hanxing.kxcharge.module.member.controller.admin.plate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户车牌信息 Excel 导出 Request VO，参数和 PlatePageReqVO 是一致的")
@Data
public class PlateExportReqVO {

    @Schema(description = "车辆类型", example = "2")
    private Integer plateType;

    @Schema(description = "车牌号")
    private String plateNumber;

    @Schema(description = "用户ID", example = "5797")
    private Long userId;

    @Schema(description = "最近充电地点")
    private String latelyPlace;

    @Schema(description = "最近充电时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] latelyTime;

    @Schema(description = "充电次数")
    private Integer chargeQuantity;

    @Schema(description = "是否为默认")
    private Integer isDefault;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
