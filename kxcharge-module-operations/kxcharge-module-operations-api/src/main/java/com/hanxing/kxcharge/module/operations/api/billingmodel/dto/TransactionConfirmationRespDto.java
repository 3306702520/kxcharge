package com.hanxing.kxcharge.module.operations.api.billingmodel.dto;

import lombok.Data;

@Data
public class TransactionConfirmationRespDto {
    // 交易流水号
    private String physicalCardNumber;
    // 确认结果 0x00 上传成功 0x01 非法账单
    private String confirmResult;
}
