package com.hanxing.kxcharge.module.operations.mq.message;

import lombok.Data;

import java.io.Serializable;

@Data
public class Heartbeat implements Serializable {

    public static final String QUEUE = "CHARGING_PILE_HEART_BEAT_QUEUE";

    // 桩编码 不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;
    // 枪状态 0x00：正常 0x01：故障
    private String GunStatus;

    public Heartbeat(String data) {
        int PARM1=1,PARM2=7;
        int count = 0;
        this.pileCode = data.substring(count,count+=(PARM2*2));
        this.gunNo = data.substring(count,count+=(PARM1*2));
        this.GunStatus = data.substring(count,count+=(PARM1*2));
    }

    public Heartbeat(){
    }
}
