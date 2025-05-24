package com.hanxing.kxcharge.module.operations.api.station.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class EquipmentModelReqDto {

    /**
     * 主键
     */
    private Long id;
    /**
     * 运营商id
     */
    private Long orgId;
    /**
     * 品牌id
     */
    private Long equipmentBrandId;
    /**
     * 电站型号
     */
    private String equipmentModel;
    /**
     * 充电枪数量
     */
    private Integer number;
    /**
     * 电桩类型
     *
     * 枚举 {@link TODO electric_type 对应的类}
     */
    private Integer type;
    /**
     * 设备图片
     */
    private String img;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 电桩功率
     */
    private BigDecimal power;
    /**
     * 辅助电源
     */
    private BigDecimal supPower;
    /**
     * 枪线温度预警
     */
    private BigDecimal gunTemperatureWarn;
    /**
     * 枪线温度断充
     */
    private BigDecimal gunTemperatureStop;
    /**
     * 输入电压
     */
    private BigDecimal inputv;
    /**
     * 最大电压
     */
    private BigDecimal maxv;
    /**
     * 最小电压
     */
    private BigDecimal minv;
    /**
     * 最大电流
     */
    private BigDecimal maxa;
    /**
     * 最小电流
     */
    private BigDecimal mina;
    /**
     * 恒功率输出电压区间大
     */
    private BigDecimal maxp;
    /**
     * 恒功率输出电压区间小
     */
    private BigDecimal minp;

}
