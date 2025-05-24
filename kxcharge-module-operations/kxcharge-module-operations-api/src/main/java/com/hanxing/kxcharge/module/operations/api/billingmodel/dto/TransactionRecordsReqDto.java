package com.hanxing.kxcharge.module.operations.api.billingmodel.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionRecordsReqDto {
    // 交易流水号 见名词解释
    private String transactionSerialNo;
    // 桩编号 不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;
    // 开始时间 CP56Time2a 格式
    private Date startTime;
    // 结束时间  CP56Time2a 格式
    private Date endTime;
    // 尖单价  精确到小数点后五位（尖电费+尖服务费，见费率帧）
    private BigDecimal sharpUnitPrice;
    // 尖电量
    private BigDecimal sharpCharge;
    // 计损尖电量
    private BigDecimal calculationOfPeakPowerLoss;
    // 尖金额
    private BigDecimal sharpAmount;
    // 峰单价 精确到小数点后五位（峰电费+峰服务费）
    private BigDecimal peakUnitPrice;
    // 峰电量
    private BigDecimal peakPower;
    // 计损峰电量
    private BigDecimal peakPowerLossCalculation;
    // 峰金额
    private BigDecimal peakAmount;
    // 平单价 精确到小数点后五位（平电费+平服务费）
    private BigDecimal flatUnitPrice;
    // 平电量
    private BigDecimal averageElectricQuantity;
    // 计损平电量
    private BigDecimal lossLevelingElectricity;
    // 平金额
    private BigDecimal flatAmount;
    // 谷单价 精确到小数点后五位（谷电费+谷服务费）
    private BigDecimal valleyUnitPrice;
    // 谷电量
    private BigDecimal valleyPower;
    // 计损谷电量
    private BigDecimal lossValleyElectricity;
    // 谷金额
    private BigDecimal valleyAmount;
    // 电表总起值
    private BigDecimal totalStartingValueOfElectricityMeter;
    // 电表总止值
    private BigDecimal totalStopValueOfElectricityMeter;
    // 总电量
    private BigDecimal totalElectricity;
    // 计损总电量
    private BigDecimal totalElectricityLossCalculation;
    // 消费金额
    private BigDecimal consumptionAmount;
    // 电动汽车唯一标识 VIN 码，此处 VIN 码和充电时 VIN码不同，正序直接上传，无需补 0 和反序
    private String uniqueIdentificationOfElectricVehicles;
    /**
     * 交易标识
     * 0x01：app 启动
     * 0x02：卡启动
     * 0x04：离线卡启动
     * 0x05: vin 码启动充电
     */
    private String transactionID;
    // 交易日期、时间 CP56Time2a 格式
    private Date transactionDate;
    // 停止原因 见附录 11.1
    private String stoppingReason;
    // 物理卡号 不足 8 位补 0
    private String physicalCardNo;
}
