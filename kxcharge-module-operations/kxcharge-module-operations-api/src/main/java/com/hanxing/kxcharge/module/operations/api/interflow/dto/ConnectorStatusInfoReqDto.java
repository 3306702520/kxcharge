package com.hanxing.kxcharge.module.operations.api.interflow.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConnectorStatusInfoReqDto implements Serializable {

    public static final String QUEUE = "CHARGE_SERVER_CONNECTOR_STATUS_INFO";

    private Long stationId;

    /**
     * 充电设备接口编号
     */
    private String connectorId;


    /**
     * 设备接口状态 0 离网 1 空闲  2 占用  未充电  3 占用 充电中   4 占用 预约锁定  255 故障
     */
    private Integer status;


    /**
     * 车位状态  0 未知 10 空闲 50 占用
     */
    private Integer parkStatus;

    /**
     * 地锁 0 未知 10 空闲 50 已上锁
     */
    private Integer lockStatus;



}
