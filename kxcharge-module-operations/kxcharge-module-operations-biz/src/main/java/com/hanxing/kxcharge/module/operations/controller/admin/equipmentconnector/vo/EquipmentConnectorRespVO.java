package com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 电枪管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentConnectorRespVO extends EquipmentConnectorBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "30657")
    private Long id;

    @Schema(description = "二维码")
    private String codeImage;

    @Schema(description = "桩信息")
    private EquipmentRespVO equipmentRespVO;



    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @Schema(description = "最后更新时间")
    private LocalDateTime updateTime;


    @Schema(description = "创建人")
    private String creator;


    @Schema(description = "更新人")
    private String updater;

}
