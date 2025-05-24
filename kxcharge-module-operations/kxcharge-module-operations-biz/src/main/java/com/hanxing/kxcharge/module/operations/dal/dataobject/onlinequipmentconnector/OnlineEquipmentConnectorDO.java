package com.hanxing.kxcharge.module.operations.dal.dataobject.onlinequipmentconnector;

import lombok.Data;

@Data
public class OnlineEquipmentConnectorDO {

    /**
     * 枪对象
     */
    private Long stationId;

    //枪编码 设备编码+01/02
    private String code;
    //状态: 00:正常 01:故障
    private String state;
    //上次心跳时间
    private Long latelyHeartBeatTime;
    //工作状态 00 离线, 01 故障, 02空闲, 03充电 04未知
    private String workState;
    //是否归位 00 否, 01 是, 02未知
    private String isHoming;
    //是否插枪 00 否, 01 是 02未知
    private String isInsertArms;
}
