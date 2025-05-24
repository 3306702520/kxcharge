package com.hanxing.kxcharge.module.operations.api.billingmodel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Schema(description = "计费模型")
@Data
public class BillingModelRespDto implements Serializable {


    public static final String EXCHANGE = "CHARGE_SERVER_BILLING_MODEL";

    private String pileCode;

    // 计费模型编号 固定值：01 00
    private String billingModelNo;
    // 尖费电费费率 精确到五位小数
    private Float sharpElectricityRate;
    // 尖服务费费率
    private Float topServiceFeeRate;
    // 峰电费费率
    private Float peakElectricityRate;
    // 峰服务费费率
    private Float peakServiceFeeRate;
    // 平电费费率
    private Float flatElectricityRate;
    // 平服务费费率
    private Float flatServiceFeeRate;
    // 谷电费费率
    private Float valleyElectricityRate;
    // 谷服务费费率 精确到五位小数
    private Float valleyServiceFeeRate;
    // 计损比例 见名词解释
    private String lossCalculationRatio;
    private List<String> periodRateNoList;
}
