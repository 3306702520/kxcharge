package com.hanxing.kxcharge.module.pile.mq;

import lombok.Data;

import java.io.Serializable;

/**
 * 远程停机命令回复
 */
@Data
public class RemoteShutdownCommandReply implements Serializable {

    // 必须有无参构造方法
    public RemoteShutdownCommandReply() {
    }

    public static final String QUEUE = "CHARGING_PILE_REMOTE_SHUTDOWN_QUEUE";
    // 桩编号 不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;
    // 停止结果 0x00失败 0x01成功
    private String stopResult;
    // 失败原因 0x00 无
    //0x01 设备编号不匹配
    //0x02 枪未处于充电状态
    //0x03 其他
    private String failureReason;

    public RemoteShutdownCommandReply(String data) {
        int PARM1=1,PARM2=7;
        int count = 0;
        this.pileCode = data.substring(count,count+=(PARM2*2));
        this.gunNo = data.substring(count,count+=(PARM1*2));
        this.stopResult = data.substring(count,count+=(PARM1*2));
        this.failureReason = data.substring(count,count+=(PARM1*2));
    }
}
