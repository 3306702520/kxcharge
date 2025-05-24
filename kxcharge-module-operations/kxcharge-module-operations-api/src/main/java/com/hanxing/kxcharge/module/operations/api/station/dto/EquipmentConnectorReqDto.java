package com.hanxing.kxcharge.module.operations.api.station.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EquipmentConnectorReqDto {

    /**
     * 主键
     */
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
