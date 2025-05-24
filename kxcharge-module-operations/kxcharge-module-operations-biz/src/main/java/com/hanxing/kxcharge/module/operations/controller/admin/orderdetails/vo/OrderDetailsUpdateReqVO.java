package com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 订单详情管理更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetailsUpdateReqVO extends OrderDetailsBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "14553")
    @NotNull(message = "主键不能为空")
    private Long id;

}
