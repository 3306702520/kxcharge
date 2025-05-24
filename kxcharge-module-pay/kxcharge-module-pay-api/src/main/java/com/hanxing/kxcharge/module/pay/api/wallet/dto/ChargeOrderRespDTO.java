package com.hanxing.kxcharge.module.pay.api.wallet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "RPC 服务 - 钱包处理订单信息 Response DTO")
public class ChargeOrderRespDTO {

    // 付款金额
    private Integer payMoney;
    // 是否成功支付
    private Boolean isPay;
    // 支付时间
    private LocalDateTime payTime;
}
