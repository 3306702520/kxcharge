package com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


/**
 * 电桩管理 Excel VO
 *
 * @author kaixin
 */
@Data
public class EquipmentExcelVO {

    @ExcelProperty("电桩名称")
    private String equipmentName;

    @ExcelProperty("设备编码")
    private String equipmentCode;

    @ExcelProperty("是否在线")
    private Integer isOnline;

    @ExcelProperty("电站id")
    private Long stationId;

    @ExcelProperty("校价id")
    private Long operatorId;

}
