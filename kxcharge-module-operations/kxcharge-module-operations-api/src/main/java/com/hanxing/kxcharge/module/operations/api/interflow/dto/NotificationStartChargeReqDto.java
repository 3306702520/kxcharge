package com.hanxing.kxcharge.module.operations.api.interflow.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class NotificationStartChargeReqDto implements Serializable {

    public static final String QUEUE = "CHARGE_SERVER_NOTIFICATION_STARTCHARGE";

    private Long stationId;

    /**
     * 中电联订单号
     */
    private String tradeSeq;

    // 桩编号 不足 7 位补 0
    private String pileCode;
    // 枪号
    private String gunNo;

    /**
     BE_STARTING(1, "启动中"),
     CHARGING(2, "充电中"),
     IN_PROGRESS(3, "停止中"),
     HAVE_FINISHED(4, "已结束"),
     UNKNOWN(5, "未知"),
     ERROR(6,"异常")
     */
    private Integer state;

    /**
     * 开始充电
     */
    private LocalDateTime startTime;

}
