package com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 设备型号管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentModelCreateReqVO extends EquipmentModelBaseVO {

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5587")
    @NotNull(message = "运营商id不能为空")
    private Long orgId;

    @Schema(description = "设备图片", requiredMode = Schema.RequiredMode.REQUIRED)
    private String img;

    @Schema(description = "备注说明", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    private String remark;

    @Schema(description = "辅助电源")
    private BigDecimal supPower;

    @Schema(description = "枪线温度预警", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal gunTemperatureWarn;

    @Schema(description = "枪线温度断充", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal gunTemperatureStop;

    @Schema(description = "输入电压", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal inputv;

    @Schema(description = "最大电压", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal maxv;

    @Schema(description = "最小电压", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal minv;

    @Schema(description = "最大电流", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal maxa;

    @Schema(description = "最小电流", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal mina;

    @Schema(description = "恒功率输出电压区间大")
    private BigDecimal maxp;

    @Schema(description = "恒功率输出电压区间小")
    private BigDecimal minp;

}
