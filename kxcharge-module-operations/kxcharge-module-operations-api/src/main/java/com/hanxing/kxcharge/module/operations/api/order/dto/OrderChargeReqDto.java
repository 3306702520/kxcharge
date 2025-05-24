package com.hanxing.kxcharge.module.operations.api.order.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class OrderChargeReqDto implements Serializable {

    /**
     * 中电联订单号
     */
    private String tradeSeq;



    /**
     * 枪id
     */
    private String gunId;

    /**
     * 车牌号
     */
    private String plateNumber;



    /**
     * 二维码字符串
     */
    private String QRCode;


    /**
     * 充电方式
     */
    private Integer chargingWay;


}
