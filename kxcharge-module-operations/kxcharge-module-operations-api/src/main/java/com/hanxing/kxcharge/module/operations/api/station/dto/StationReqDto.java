package com.hanxing.kxcharge.module.operations.api.station.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StationReqDto {

    /**
     * 主键
     */
    private Long id;
    /**
     * 运营商id
     */
    private Long orgId;
    /**
     * 电站名称
     */
    private String stationName;
    /**
     * 国家code
     */
    private Integer countryCode;
    /**
     * 省code
     */
    private String provinceCode;
    /**
     * 市code
     */
    private String cityCode;
    /**
     * 区code
     */
    private String areaCode;

    /**
     * 镇code
     */
    private String townCode;
    /**
     * 省市区中文名
     */
    private String areaCodeName;
    /**
     * 地址
     */
    private String address;
    /**
     * 服务电话
     */
    private String serviceTel;
    /**
     * 电站类型
     *
     * 枚举 {@link TODO station_type 对应的类}
     */
    private Integer stationType;
    /**
     * 电站状态
     */
    private Integer stationStatus;
    /**
     * 经度
     */
    private BigDecimal stationLng;
    /**
     * 纬度
     */
    private BigDecimal stationLat;
    /**
     * 建设场所
     *
     * 枚举 {@link TODO construction_site 对应的类}
     */
    private Integer construction;
    /**
     * 电站位置（地上、地下）
     */
    private Integer stationAddress;
    /**
     * 可用电容
     */
    private String capacitance;
    /**
     * 分润设置
     */
    private String shareType;
    /**
     * 是否收取占用费
     *
     * 枚举 {@link TODO occupation_cost 对应的类}
     */
    private Integer isOccupationCost;
    /**
     * 停车免费时间
     */
    private String parkBufferTime;
    /**
     * 超额费用
     */
    private BigDecimal overtimeMoney;
    /**
     * 超额min
     */
    private String overtimeMin;
    /**
     * 单次扣款限额
     */
    private BigDecimal singleDeductionLimit;
    /**
     * 道闸
     *
     * 枚举 {@link TODO barrier_gate 对应的类}
     */
    private Integer isBrake;
    /**
     * 停车说明
     */
    private String parkExplain;
    /**
     * 站点描述
     */
    private String stationDescribe;
    /**
     * 站点服务设施
     */
    private Integer serviceSetup;
    /**
     * 周边基础设施
     *
     * 枚举 {@link TODO base_setup 对应的类}
     */
    private Integer baseSetup;
    /**
     * 开放说明时间
     */
    private String openExplain;
    /**
     * 主入口图
     */
    private String imgMainEntrance;
    /**
     * 标志路劲
     */
    private String imgFlagPath;
    /**
     * 电站全景
     */
    private String imgFullView;
    /**
     * 电站特写
     */
    private String imgPilesFeature;
    /**
     * 其他图片
     */
    private String imgOther;
    /**
     * 资质
     */
    private String qualification;
    /**
     * 备注说明
     */
    private String remarks;
    /**
     * 是否启用电站
     */
    private Integer isEnableStart;
    /**
     * 客服电话
     */
    private String hotLine;
    /**
     * 站点code
     */
    private String stationCode;
    /**
     * 总电量
     */
    private BigDecimal sumPower;
    /**
     * 站点服务设施
     *
     * 枚举 {@link TODO service_setup 对应的类}
     */
    private Integer stationServiceSets;
    /**
     * 单价关联id
     */
    private Long costTemplateId;
    /**
     * 场地属性
     *
     * 枚举 {@link TODO station_property 对应的类}
     */
    private Integer property;

    /**
     * 设备信息
     */
    private List<EquipmentReqDto> equipmentReqDtoList;

    // 设备所属id
    private String equipmentOwnerId;

}
