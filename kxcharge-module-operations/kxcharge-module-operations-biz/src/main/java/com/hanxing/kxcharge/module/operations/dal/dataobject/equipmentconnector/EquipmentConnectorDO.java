package com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 电枪管理 DO
 *
 * @author kaixin
 */
@TableName("operations_equipment_connector")
@KeySequence("operations_equipment_connector_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentConnectorDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 电站id
     */
    private Long stationId;
    /**
     * 电桩id
     */
    private Long equipmentId;

    /**
     * 设备品牌id
     */
    private Long equipmentBrandId;
    /**
     * 设备型号id
     */
    private Long equipmentModelId;

    /**
     * 运营商id
     */
    private Long orgId;
    /**
     * 操作人id
     */
    private Long operatorId;
    /**
     * 电枪编号
     */
    private String connectorCode;
    /**
     * 电枪简短编号
     */
    private String shortcutCode;
    /**
     * 电枪名称
     */
    private String connectorName;
    /**
     * 是否启用
     */
    private Integer isEnableStart;
    /**
     * 工作状态
     */
    private Integer workStatus;
    /**
     * 版本号
     */
    private String version;
    /**
     * 是否单独价格
     */
    private Boolean isAlonePrice;
    /**
     * 操作时间
     */
    private LocalDateTime operationDate;

}
