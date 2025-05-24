package com.hanxing.kxcharge.module.operations.controller.admin.station.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 站点信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StationRespVO extends StationBaseVO {


    @Schema(description = "国家code")
    private Integer countryCode;

    @Schema(description = "省code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String provinceCode;

    @Schema(description = "市code", requiredMode = Schema.RequiredMode.REQUIRED)
    private String cityCode;

    @Schema(description = "区code")
    private String areaCode;

    @Schema(description = "镇code")
    private String townCode;;

    @Schema(description = "服务电话")
    private String serviceTel;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经度不能为空")
    private BigDecimal stationLng;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal stationLat;

    @Schema(description = "建设场所", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer construction;

    @Schema(description = "电站位置（地上、地下）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stationAddress;

    @Schema(description = "可用电容")
    private String capacitance;

    @Schema(description = "分润设置", example = "2")
    private String shareType;

    @Schema(description = "是否收取占用费", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isOccupationCost;

    @Schema(description = "停车免费时间")
    private String parkBufferTime;

    @Schema(description = "超额费用")
    private BigDecimal overtimeMoney;

    @Schema(description = "超额min")
    private String overtimeMin;

    @Schema(description = "单次扣款限额")
    private BigDecimal singleDeductionLimit;

    @Schema(description = "道闸", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer isBrake;

    @Schema(description = "站点描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "站点描述不能为空")
    private String stationDescribe;

    @Schema(description = "站点服务设施")
    private Integer serviceSetup;

    @Schema(description = "周边基础设施")
    private Integer baseSetup;

    @Schema(description = "主入口图")
    private String imgMainEntrance;

    @Schema(description = "标志路劲")
    private String imgFlagPath;

    @Schema(description = "电站特写")
    private String imgPilesFeature;

    @Schema(description = "其他图片")
    private String imgOther;

    @Schema(description = "资质")
    private String qualification;

    @Schema(description = "备注说明")
    private String remarks;

    @Schema(description = "客服电话")
    private String hotLine;

    @Schema(description = "站点code")
    private String stationCode;

    @Schema(description = "总电量")
    private BigDecimal sumPower;

    @Schema(description = "站点服务设施")
    private Integer stationServiceSets;

    @Schema(description = "单价关联id", example = "25656")
    private Long costTemplateId;



    @Schema(description = "更新时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "开放说明时间")
    private String openExplain;

    @Schema(description = "电站全景")
    private String imgFullView;

    @Schema(description = "更新人")
    private String updater;

}
