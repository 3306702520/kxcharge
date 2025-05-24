package com.hanxing.kxcharge.module.operations.api.order.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class OrderChargeRespDto implements Serializable {


    /**
     * 中电联订单号
     */
    private String tradeSeq;



    /**
     * 枪id
     */
    private String gunId;


    /**
     * 中电联订单状态  1 启动中  2充电中 。。。。
     */
    private String tradeStatus;


    /**
     * 失败原因
     */
    private Integer failReason;

}
