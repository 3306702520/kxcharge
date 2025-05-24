package com.hanxing.kxcharge.module.member.controller.admin.plate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 用户车牌信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PlateBaseVO {

    @Schema(description = "车辆类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "车辆类型不能为空")
    private Integer plateType;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车牌号不能为空")
    private String plateNumber;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5797")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "最近充电地点")
    private String latelyPlace;

    @Schema(description = "最近充电时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime latelyTime;

    @Schema(description = "充电次数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer chargeQuantity;

    @Schema(description = "是否为默认", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isDefault;

}
