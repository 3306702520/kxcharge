package com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 设备型号管理 DO
 *
 * @author kaixin
 */
@TableName("operations_equipment_model")
@KeySequence("operations_equipment_model_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentModelDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
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
