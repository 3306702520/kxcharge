package com.hanxing.kxcharge.module.operations.api.billingmodel.dto;

import lombok.Data;

@Data
public class BillingModelReqDto {

    // 桩编号 不足 7 位补 0
    private String pileCode;

    public BillingModelReqDto(String pileCode) {
        this.pileCode = pileCode;
    }
}
