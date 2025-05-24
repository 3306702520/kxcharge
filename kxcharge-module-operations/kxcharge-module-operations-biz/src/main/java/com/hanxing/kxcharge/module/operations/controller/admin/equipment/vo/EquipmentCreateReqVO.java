package com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 电桩管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentCreateReqVO extends EquipmentBaseVO {

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11672")
    @NotNull(message = "运营商id不能为空")
    private Long orgId;

    @Schema(description = "电桩", example = "18074")
    private String equipmentOwnerId;

    @Schema(description = "设备品牌id", requiredMode = Schema.RequiredMode.REQUIRED, example = "17972")
    @NotNull(message = "设备品牌id不能为空")
    private Long equipmentBrandId;

    @Schema(description = "设备型号id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23444")
    @NotNull(message = "设备型号id不能为空")
    private Long equipmentModelId;

    @Schema(description = "版本号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;

    @Schema(description = "枪数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "枪数量不能为空")
    private Integer equipmentGunNumber;

    @Schema(description = "通信运营商", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer communicationOperator;

    @Schema(description = "sim卡", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isSim;

    @Schema(description = "sim号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numberSim;

    @Schema(description = "车位号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parkNo;

    @Schema(description = "有无车锁", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer parkLock;

    @Schema(description = "价格设置", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "价格设置不能为空")
    private Integer priceSet;

    @Schema(description = "固件版本")
    private String firmwareVersion;

    @Schema(description = "是否启用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isEnableStart;

    @Schema(description = "设备标准", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备标准不能为空")
    private Integer equipmentStandard;

    @Schema(description = "充电模块品牌型号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String chargingModuleBrandModel;

    @Schema(description = "充电枪线品牌型号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String chargingGunCableBrandModel;

    @Schema(description = "电桩屏幕品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String equipmentScreenBrand;

    @Schema(description = "电桩程序", requiredMode = Schema.RequiredMode.REQUIRED)
    private String equipmentProcedure;

    @Schema(description = "设备生产日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate productionDate;

    @Schema(description = "设备型号类型", example = "1")
    private String equipmentModelType;

    @Schema(description = "设置价格时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime setPriceTime;

    @Schema(description = "设备类型")
    private Integer communicationType;

    @Schema(description = "单价关联id")
    private Long costTemplateId;

}
