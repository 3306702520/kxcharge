package com.hanxing.kxcharge.module.pay.api.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Schema(description = "RPC 服务 - 充电订单钱包处理 Request DTO")
@Data
public class ChargeOrderReqDTO implements Serializable {
    //充电订单id
    private Long orderId;
    //用户id
    private Long userId;
    // 充电费用
    private BigDecimal chargingCost;
}
