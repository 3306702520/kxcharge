package com.hanxing.kxcharge.module.pile.mq;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实时监测数据
 */
@Data
public class RealTimeMonitor implements Serializable {
    public static final String QUEUE = "CHARGING_PILE_REAL_TIME_MONITOR_DATA_QUEUE";

    // 交易流水号 见名词解释
    private String transactionSerialNo;
    // 桩编号 不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;
    /**
     * 状态
     * 0x00：离线
     * 0x01：故障
     * 0x02：空闲
     * 0x03：充电
     * 需做到变位上送
     */
    private String state;
    // 枪是否归位 0x00 否 0x01 是 0x02 未知（无法检测到枪是否插回枪座即未知）
    private String gunHomingStatus;
    // 是否插枪 0x00 否 0x01 是 需做到变位上送
    private String insertGunStatus;
    // 输出电压 精确到小数点后一位；待机置零
    private BigDecimal outputVoltage;
    // 输出电流 精确到小数点后一位；待机置零
    private BigDecimal outputCurrent;
    // 枪线温度 整形，偏移量-50；待机置零
    private String gunWireTemperature;
    // 枪线编码 没有置零
    private String gunWireCoding;
    // SOC 待机置零；交流桩置零
    private String SOC;
    // 电池组最高温度 整形，偏移量-50 ºC；待机置零；交流桩置零
    private String maximumTemperatureOfBatteryPack;
    // 累计充电时间 单位：min；待机置零
    private String accumulatedChargingTime;
    // 剩余时间  单位：min；待机置零、交流桩置零
    private String remainingTime;
    // 充电度数 精确到小数点后四位；待机置零
    private BigDecimal chargingDegree;
    // 计损充电度数 精确到小数点后四位；待机置零 未设置计损比例时等于充电度数
    private BigDecimal lossChargingDegree;
    // 已充金额 单位：min；待机置 零精确到小数点后四位；待机置零（电费+服务费）*计损充电度数
    private BigDecimal chargedAmount;
    /**
     * 硬件故障 单位：min；待机置零
     * Bit 位表示（0 否 1 是），低位到高位顺序
     * Bit1：急停按钮动作故障；
     * Bit2：无可用整流模块；
     * Bit3：出风口温度过高；
     * Bit4：交流防雷故障；
     * Bit5：交直流模块 DC20 通信中断；
     * Bit6：绝缘检测模块 FC08 通信中断；
     * Bit7：电度表通信中断；
     * Bit8：读卡器通信中断；
     * Bit9：RC10 通信中断；
     * Bit10：风扇调速板故障；
     * Bit11：直流熔断器故障；
     * Bit12：高压接触器故障；
     * Bit13：门打开；
     */
    private String hardwareFailure;
}
