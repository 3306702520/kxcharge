package com.hanxing.kxcharge.module.operations.controller.admin.station.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.hanxing.kxcharge.framework.excel.core.annotations.DictFormat;
import com.hanxing.kxcharge.framework.excel.core.convert.DictConvert;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 站点信息 Excel VO
 *
 * @author kaixin
 */
@Data
public class StationExcelVO {

    @ExcelProperty("运营商id")
    private Long orgId;

    @ExcelProperty("电站名称")
    private String stationName;

    @ExcelProperty("省市区中文名")
    private String areaCodeName;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty(value = "电站类型", converter = DictConvert.class)
    @DictFormat("station_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer stationType;

    @ExcelProperty("电站状态")
    private Integer stationStatus;

    @ExcelProperty("停车说明")
    private String parkExplain;

    @ExcelProperty("开放说明时间")
    private String openExplain;

    @ExcelProperty("电站全景")
    private String imgFullView;

    @ExcelProperty("是否启用电站")
    private Integer isEnableStart;

    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

    @ExcelProperty(value = "场地属性", converter = DictConvert.class)
    @DictFormat("station_property") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Integer property;

}
