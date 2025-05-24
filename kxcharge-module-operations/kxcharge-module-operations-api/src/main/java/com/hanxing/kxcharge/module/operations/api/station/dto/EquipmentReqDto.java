package com.hanxing.kxcharge.module.operations.api.station.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EquipmentReqDto {


    /**
     * 主键
     */
    private Long id;
    /**
     * 运营商id
     */
    private Long orgId;
    /**
     * 电桩
     */
    private String equipmentOwnerId;
    /**
     * 设备品牌id
     */
    private Long equipmentBrandId;
    /**
     * 设备型号id
     */
    private Long equipmentModelId;
    /**
     * 版本号
     */
    private String version;
    /**
     * 枪数量
     */
    private Integer equipmentGunNumber;
    /**
     * 电桩名称
     */
    private String equipmentName;
    /**
     * 设备编码
     */
    private String equipmentCode;
    /**
     * 通信运营商
     * <p>
     * 枚举 {@link TODO communication_operator 对应的类}
     */
    private Integer communicationOperator;
    /**
     * sim卡
     */
    private Boolean isSim;
    /**
     * sim号码
     */
    private String numberSim;
    /**
     * 车位号
     */
    private String parkNo;
    /**
     * 有无车锁
     */
    private Integer parkLock;
    /**
     * 价格设置
     * <p>
     * 枚举 {@link TODO price_set 对应的类}
     */
    private Integer priceSet;
    /**
     * 固件版本
     */
    private String firmwareVersion;
    /**
     * 是否启用
     */
    private Integer isEnableStart;

    /**
     * 是否在线
     */
    private Integer isOnline;
    /**
     * 设备标准
     * <p>
     * 枚举 {@link TODO equipment_standard 对应的类}
     */
    private Integer equipmentStandard;
    /**
     * 充电模块品牌型号
     */
    private String chargingModuleBrandModel;
    /**
     * 充电枪线品牌型号
     */
    private String chargingGunCableBrandModel;
    /**
     * 电桩屏幕品牌
     */
    private String equipmentScreenBrand;
    /**
     * 电桩程序
     */
    private String equipmentProcedure;
    /**
     * 设备生产日期
     */
    private LocalDate productionDate;
    /**
     * 设备型号类型
     */
    private String equipmentModelType;
    /**
     * 设置价格时间
     */
    private LocalDateTime setPriceTime;
    /**
     * 电站id
     */
    private Long stationId;
    /**
     * 校价id,为计费模型id
     */
    private Long operatorId;
    /**
     * 校价时间
     */
    private LocalDateTime operatorTime;
    /**
     * 设备类型
     */
    private Integer communicationType;
    /**
     * 通信协议版本号
     */
    private String protocolVersion;
    /**
     * 网络链接类型
     */
    private Integer networkLinkType;

    /**
     * 关联id
     */
    private Long costTemplateId;



    private EquipmentBrandReqDto brandReqDto;

    private EquipmentModelReqDto modelReqDto;

    private List<EquipmentConnectorReqDto> connectorReqDtoList;
}
