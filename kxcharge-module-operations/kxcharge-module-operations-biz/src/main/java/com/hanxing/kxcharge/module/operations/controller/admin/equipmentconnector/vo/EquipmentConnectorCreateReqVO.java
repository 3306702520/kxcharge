package com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 电枪管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EquipmentConnectorCreateReqVO extends EquipmentConnectorBaseVO {

}
