package com.hanxing.kxcharge.module.operations.api.interflow.dto;

import lombok.Data;

@Data
public class NotifacationEquipBusinessPolicyInfoRespDto {

    // 起始时间点
    private String startTime;

    // 时段电费
    private Float elecPrice;

    // 时段服务费
    private Float servicePrice;
}
