package com.hanxing.kxcharge.module.operations.api.billingmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema(description = "RPC 服务 - 计费模型验证请求")
@Data
public class BillingModelValidationRespDto implements Serializable {
    // 计费模型编号
    private String billingModelNo;
    /**
     * 验证结果
     * 0x00 桩计费模型与平台一致
     * 0x01 桩计费模型与平台不一致
     */
    private String verificationResults;
}
