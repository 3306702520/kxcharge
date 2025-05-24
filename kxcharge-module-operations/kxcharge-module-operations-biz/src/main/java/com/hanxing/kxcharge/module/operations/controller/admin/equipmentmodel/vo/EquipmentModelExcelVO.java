package com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hanxing.kxcharge.framework.excel.core.annotations.DictFormat;
import com.hanxing.kxcharge.framework.excel.core.convert.DictConvert;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 设备型号管理 Excel VO
 *
 * @author kaixin
 */
@Data
public class EquipmentModelExcelVO {

    @ExcelProperty("品牌id")
    private Long equipmentBrandId;

    @ExcelProperty("电站型号")
    private String equipmentModel;

    @ExcelProperty("充电枪数量")
    private Integer number;

    @ExcelProperty(value = "电桩类型", converter = DictConvert.class)
    @DictFormat("electric_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer type;

    @ExcelProperty("电桩功率")
    private BigDecimal power;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
