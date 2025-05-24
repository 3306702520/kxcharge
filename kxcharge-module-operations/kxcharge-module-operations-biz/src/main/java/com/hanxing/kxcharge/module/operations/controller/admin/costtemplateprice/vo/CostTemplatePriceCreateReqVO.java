package com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 计费模板价格时段创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CostTemplatePriceCreateReqVO extends CostTemplatePriceBaseVO {

}
