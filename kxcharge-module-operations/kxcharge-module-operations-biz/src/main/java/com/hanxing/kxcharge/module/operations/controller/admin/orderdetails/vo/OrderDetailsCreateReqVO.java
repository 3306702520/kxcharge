package com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 订单详情管理创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetailsCreateReqVO extends OrderDetailsBaseVO {

    // 订单处理时，因为是电桩发来信息，所以不会主动生成tenantId
    private Long tenantId;

}
