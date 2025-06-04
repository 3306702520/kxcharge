package com.hanxing.kxcharge.module.pile.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class StartChargingCommandReply implements Serializable {


    public static final String QUEUE = "CHARGING_PILE_REMOTE_START_COMMAND_QUEUE";

    // 交易流水号
    private String transactionSerialNo;
    // 桩编号 不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;
    // 启动结果 0x00失败 0x01成功
    private String launchResult;
    /**
     * 失败原因
     * 0x00 无
     * 0x01 设备编号不匹配
     * 0x02 枪已在充电
     * 0x03 设备故障
     * 0x04 设备离线
     * 0x05 未插枪
     * 桩在收到启充命令后,检测到未插
     * 枪则发送 0x33 报文回复充电失
     * 败。若在 60 秒（以收到 0x34 时间
     * 开始计算）内检测到枪重新连接，
     * 则补送 0x33 成功报文;超时或者
     * 离线等其他异常，桩不启充、不补
     * 发 0x33 报文
     */
    private String failureReason;



}
