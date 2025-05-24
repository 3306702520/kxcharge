package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备品牌管理 Excel VO
 *
 * @author kaixin
 */
@Data
public class EquipmentBrandExcelVO {

    @ExcelProperty("品牌名称")
    private String brandName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
