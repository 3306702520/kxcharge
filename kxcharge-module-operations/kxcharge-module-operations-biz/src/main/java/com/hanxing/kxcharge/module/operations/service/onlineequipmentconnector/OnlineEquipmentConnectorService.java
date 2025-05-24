package com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector;

import com.hanxing.kxcharge.module.operations.dal.dataobject.onlinequipmentconnector.OnlineEquipmentConnectorDO;

public interface OnlineEquipmentConnectorService {


    /**
     * 收到心跳帧
     * @param pileCode 桩编码 14位16进制
     * @param gunNumber 枪号 2位16进制
     * @param state 状态 2位16进制 0x00：正常 0x01：故障
     */
    void heartBeat(String pileCode, String gunNumber, String state);


    /**
     * 登陆
     * @param stationId
     * @param pileCode 桩编码 14位16进制
     * @param number 枪数量
     */
    void login(Long stationId, String pileCode, Integer number);


    /**
     * 实时监测数据
     * @param pileCode  桩编码 14位16进制
     * @param gunNumber  枪号 2位16进制
     * @param workState  工作状态 2位16进制 0x00：离线 0x01：故障 0x02：空闲 0x03：充电
     * @param gunHomingStatus 枪是否归位 2位16进制  0x00 否 0x01 是 0x02 未知（无法检测到枪是否插回枪座即未知）
     * @param insertGunStatus 是否插枪 2位16进制 0x00 否 0x01
     */
    void realTimeMonitor(String pileCode,
                         String gunNumber,
                         String workState,
                         String gunHomingStatus,
                         String insertGunStatus);

    /**
     * 获取电枪是否在线状态
     * @param connectorCode 枪编码：桩编码+枪号
     */
    Integer getGunInfo(String connectorCode);


    /**
     * 获取电枪是否在线状态
     * @param connectorCode 枪编码：桩编码+枪号
     * @return 电枪状态 -1 下线，0 正常， 1 故障
     */
    Integer getGunState(String connectorCode);


    /**
     * 获取电桩是否是在线状态
     * @param pileCode 桩编码
     * @param number 枪数量
     * @return 电桩状态 -1 下线, 0正常, 1 故障
     */
    Integer getPileState(String pileCode, Integer number);

    /**
     * 获取电枪工作状态
     * @param connectorCode 枪编码：桩编码+枪号
     * @return 工作状态 0 离线, 1 故障, 2空闲, 3充电 4未知
     */
    Integer getGunWorkState(String connectorCode);

    /**
     * 获取电站id
     * @param connectorCode 枪编码：桩编码+枪号
     * @return ID
     */
    Long getStationId(String connectorCode);

    /**
     * 获取电枪工作状态
     * @param connectorCode 枪编码：桩编码+枪号
     * @return 工作状态 0 否, 1 是 2未知
     */
    Integer getInsertArmsState(String connectorCode);

    /**
     * 获取枪所有信息
     * @param connectorCode
     */
    OnlineEquipmentConnectorDO getObject(String connectorCode);
}
