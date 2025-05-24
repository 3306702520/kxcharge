package com.hanxing.kxcharge.module.operations.controller.app.order.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.OrderBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "用户app - 订单管理 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppOrderRespVO extends OrderBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "17428")
    private Long id;

}
