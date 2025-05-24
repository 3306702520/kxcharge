package com.hanxing.kxcharge.module.member.controller.admin.plate.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hanxing.kxcharge.framework.excel.core.annotations.DictFormat;
import com.hanxing.kxcharge.framework.excel.core.convert.DictConvert;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 用户车牌信息 Excel VO
 *
 * @author 小黑
 */
@Data
public class PlateExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty(value = "车辆类型", converter = DictConvert.class)
    @DictFormat("plate_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer plateType;

    @ExcelProperty("车牌号")
    private String plateNumber;

    @ExcelProperty("用户ID")
    private Long userId;

    @ExcelProperty("最近充电地点")
    private String latelyPlace;

    @ExcelProperty("最近充电时间")
    private LocalDateTime latelyTime;

    @ExcelProperty("充电次数")
    private Integer chargeQuantity;

    @ExcelProperty("是否为默认")
    private Integer isDefault;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
