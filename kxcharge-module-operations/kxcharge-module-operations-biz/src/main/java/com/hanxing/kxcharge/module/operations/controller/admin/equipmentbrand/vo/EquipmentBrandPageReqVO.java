package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备品牌管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentBrandPageReqVO extends PageParam {

    @Schema(description = "主键", example = "5587")
    private Long id;

    @Schema(description = "运营商id", example = "5587")
    private Long orgId;

    @Schema(description = "品牌名称", example = "李四")
    private String brandName;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
