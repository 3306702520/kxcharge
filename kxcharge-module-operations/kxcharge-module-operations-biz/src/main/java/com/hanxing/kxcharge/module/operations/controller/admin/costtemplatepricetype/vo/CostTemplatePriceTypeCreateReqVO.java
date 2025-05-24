package com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 计费模板价格类别创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CostTemplatePriceTypeCreateReqVO extends CostTemplatePriceTypeBaseVO {

}
