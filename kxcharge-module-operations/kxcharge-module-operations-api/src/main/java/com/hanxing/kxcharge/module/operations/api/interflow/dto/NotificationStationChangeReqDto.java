package com.hanxing.kxcharge.module.operations.api.interflow.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationStationChangeReqDto implements Serializable {

    public static final String QUEUE = "CHARGE_SERVER_NOTIFICATION_STATIONCHANGE";

    private Long stationId;

    /**
     * 变更类型
     */
    private Integer type;


}
