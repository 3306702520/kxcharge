package com.hanxing.kxcharge.module.operations.controller.admin.home.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 枪数据 Request VO")
@Data
@ToString(callSuper = true)
public class GunDataRespVo {

    @Schema(description = "充电中数量")
    private Integer charging;


    @Schema(description = "空闲中")
    private Integer atLeisure;

    @Schema(description = "故障中")
    private Integer inTrouble;

    @Schema(description = "占用中")
    private Integer inUse;

    @Schema(description = "离线中")
    private Integer offLine;

    @Schema(description = "停用中")
    private Integer outOfUse;







}
