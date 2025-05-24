package com.hanxing.kxcharge.module.operations.api.billingmodel.dto;

import lombok.Data;

@Data
public class PileLoginReqDto {


    // 桩编码 不足 7 位补 0
    private String pileCode;
    // 桩类型 0 表示直流桩，1 表示交流桩
    private String pileType;
    // 充电枪数量
    private String chargingGunNum;
    // 通信协议版本 版本号乘 10，v1.0 表示 0x0A
    private String protocolVersion;
    // 程序版本 不足 8 位补零
    private String programVersion;
    // 网络链接类型
    // 0x00 SIM 卡
    // 0x01 LAN
    // 0x02 WAN
    // 0x03 其他
    private String networkLinkType;
    // Sim 卡 不足 10 位补零，取不到置零
    private String SimId;
    // 运营商
    // 0x00 移动
    // 0x02 电信
    // 0x03 联通
    // 0x04 其他
    private String sim;

}
