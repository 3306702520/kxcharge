package com.hanxing.kxcharge.module.pile.mq;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RemoteControlOfStartupOnTheOperationPlatformDto implements Serializable {

    public static final String EXCHANGE = "CHARGING_SERVER_START_CHARGE";

    // 交易流水号 见名词解释
    private String transactionSerialNo;
    // 桩编号 不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;
    // 逻辑卡号 8 显示在屏幕上，不足补零,逻辑卡号为卡面印刷卡号
    private String logicalCardNo;
    // 物理卡号 不足补零，桩与平台交互需使用的物理卡号
    private String physicalCardNo;
    // 账户余额 保留到小数点两位
    private Float accountBalance;

}
