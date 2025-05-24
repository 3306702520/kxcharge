package com.hanxing.kxcharge.module.operations.controller.app.station.vo;

import com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo.EquipmentBrandRespVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.EquipmentModelRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EquipmentConnectorInfoVO {
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

    /**
     * 工作状态
     */
    private Integer workState;

    /**
     * 是否插墙
     */
    private Integer insertArmsState;


    @Schema(description = "品牌")
    private EquipmentBrandRespVO equipmentBrandRespVO;


    @Schema(description = "型号")
    private EquipmentModelRespVO equipmentModelRespVO;


    @Schema(description = "站点信息")
    private StationInfoVO stationVo;

}
