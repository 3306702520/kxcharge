package com.hanxing.kxcharge.module.pay.api.notify.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 转账单的通知 Request DTO
 *
 * @author jason
 */
@Data
public class PayTransferNotifyReqDTO {

    /**
     * 商户转账单号
     */
    @NotEmpty(message = "商户转账单号不能为空")
    private String merchantTransferId;

    /**
     * 转账订单编号
     */
    @NotNull(message = "转账订单编号不能为空")
    private Long payTransferId;
}
