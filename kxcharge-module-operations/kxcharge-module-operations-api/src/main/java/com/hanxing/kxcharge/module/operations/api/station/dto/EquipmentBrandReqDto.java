package com.hanxing.kxcharge.module.operations.api.station.dto;


import lombok.Data;

@Data
public class EquipmentBrandReqDto {


    /**
     * 主键
     */
    private Long id;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 运营商id
     */
    private Long orgId;

}
