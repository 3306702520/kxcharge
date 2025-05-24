package com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 电枪管理 Excel VO
 *
 * @author kaixin
 */
@Data
public class EquipmentConnectorExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("电站id")
    private Long stationId;

    @ExcelProperty("运营商id")
    private Long orgId;

    @ExcelProperty("操作人id")
    private Long operatorId;

    @ExcelProperty("电枪编号")
    private String connectorCode;

    @ExcelProperty("电枪简短编号")
    private String shortcutCode;

    @ExcelProperty("电枪名称")
    private String connectorName;

    @ExcelProperty("是否启用")
    private Integer isEnableStart;

    @ExcelProperty("工作状态")
    private Integer workStatus;

    @ExcelProperty("版本号")
    private String version;

    @ExcelProperty("是否单独价格")
    private Boolean isAlonePrice;

    @ExcelProperty("操作时间")
    private LocalDateTime operationDate;

}
