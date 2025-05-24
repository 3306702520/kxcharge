package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.EquipmentModelTreeRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 品牌 Response VO")
@Data
public class EquipmentTreeRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "110000")
    private Long id;

    @Schema(description = "名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京")
    private String name;

    /**
     * 子节点
     */
    private List<EquipmentModelTreeRespVO> children;

}
