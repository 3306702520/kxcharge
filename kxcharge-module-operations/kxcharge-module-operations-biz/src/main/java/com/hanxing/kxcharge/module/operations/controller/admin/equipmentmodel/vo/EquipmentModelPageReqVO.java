package com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备型号管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentModelPageReqVO extends PageParam {

    @Schema(description = "主键", example = "5587")
    private Long id;

    @Schema(description = "运营商id", example = "5587")
    private Long orgId;

    @Schema(description = "品牌id", example = "15050")
    private Long equipmentBrandId;

    @Schema(description = "电站型号")
    private String equipmentModel;

    @Schema(description = "充电枪数量")
    private Integer number;

    @Schema(description = "电桩类型", example = "2")
    private Integer type;

    @Schema(description = "设备图片")
    private String img;

    @Schema(description = "备注说明", example = "你猜")
    private String remark;

    @Schema(description = "电桩功率")
    private BigDecimal power;

    @Schema(description = "辅助电源")
    private BigDecimal supPower;

    @Schema(description = "枪线温度预警")
    private BigDecimal gunTemperatureWarn;

    @Schema(description = "枪线温度断充")
    private BigDecimal gunTemperatureStop;

    @Schema(description = "输入电压")
    private BigDecimal inputv;

    @Schema(description = "最大电压")
    private BigDecimal maxv;

    @Schema(description = "最小电压")
    private BigDecimal minv;

    @Schema(description = "最大电流")
    private BigDecimal maxa;

    @Schema(description = "最小电流")
    private BigDecimal mina;

    @Schema(description = "恒功率输出电压区间大")
    private BigDecimal maxp;

    @Schema(description = "恒功率输出电压区间小")
    private BigDecimal minp;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
