package com.hanxing.kxcharge.module.pile.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class RemoteShutdownOfOperationPlatformDto implements Serializable {

    public static final String EXCHANGE = "CHARGING_SERVER_STOP_CHARGE";


    // 桩编号  不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;
}
