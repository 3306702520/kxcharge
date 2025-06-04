package com.hanxing.kxcharge.module.pile.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class RemoteIssueQRCode implements Serializable {
    public static final String EXCHANGE = "CHARGING_PILE_REMOTE_ISSUE_QR_CODE";

    // 桩编码 不足 7 位补 0
    private String pileCode;
    // 二维码格式
    private String QRCodeFormat;
    // 二维码前缀长度
    private Integer QRCodePrefixLength;
    // 二维码前缀
    private String QRCodePrefix;

}
