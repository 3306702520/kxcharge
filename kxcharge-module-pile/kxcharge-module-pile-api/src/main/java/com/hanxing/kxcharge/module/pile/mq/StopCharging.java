package com.hanxing.kxcharge.module.pile.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class StopCharging implements Serializable {


    public static final String QUEUE = "CHARGING_PILE_REMOTE_STOP_CHARGING_QUEUE";

    // 交易流水号 16
    private String transactionSerialNo;
    // 桩编号 7
    private String pileCode;
    // 枪编号 1
    private String gunCode;
    // BMS 中止荷电状态 SOC 1
    private String bmsSoc;
    // BMS 动力蓄电池单体最低电压 2
    private String bmsMinVoltage;
    // BMS 动力蓄电池单体最高电压 2
    private String bmsMaxVoltage;
    // BMS 动力蓄电池最低温度 1
    private String bmsMinTemperature;
    // BMS 动力蓄电池最高温度 1
    private String bmsMaxTemperature;
    // 电桩累计充电时间 2
    private Integer chargingTime;
    // 电桩输出能量 2
    private Integer chargingPower;
    // 电桩充电机编号 4
    private String chargerCode;

    public StopCharging(){

    }

    public StopCharging(String data){
        int number1 = 1;
        int number2 = 2;
        int number3 = 4;
        int number4 = 7;
        int number5 = 16;
        int count = 0;
        this.transactionSerialNo = data.substring(count, count+=(2*number5));
        this.pileCode = data.substring(count, count+=(2*number4));
        this.gunCode = data.substring(count, count+=(2*number1));
        this.bmsSoc = data.substring(count, count+=(2*number1));
        this.bmsMinVoltage = data.substring(count, count+=(2*number2));
        this.bmsMaxVoltage = data.substring(count, count+=(2*number2));
        this.bmsMinTemperature = data.substring(count, count+=(2*number1));
        this.bmsMaxTemperature = data.substring(count, count+=(2*number1));
        this.chargingTime = Integer.parseInt(data.substring(count, count+=(2*number2)), 16);
        this.chargingPower = Integer.parseInt(data.substring(count, count+=(2*number2)), 16);
        this.chargerCode = data.substring(count,count+=(2*number3) );
    }



}
