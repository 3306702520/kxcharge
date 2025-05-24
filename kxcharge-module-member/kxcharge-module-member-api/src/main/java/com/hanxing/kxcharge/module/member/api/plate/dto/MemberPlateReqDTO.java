package com.hanxing.kxcharge.module.member.api.plate.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberPlateReqDTO {

    // 用户id
    private Long userId;

    // 车牌号
    private String plateNumber;

    // 充电地点
    private String chargePlace;

    // 充电时间
    private LocalDateTime chargeTime;
}
