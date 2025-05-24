package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.EquipmentModelRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 设备品牌管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentBrandRespVO extends EquipmentBrandBaseVO {

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orgId;

    @Schema(description = "型号集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<EquipmentModelRespVO> equipmentModelRespVOS;


}
