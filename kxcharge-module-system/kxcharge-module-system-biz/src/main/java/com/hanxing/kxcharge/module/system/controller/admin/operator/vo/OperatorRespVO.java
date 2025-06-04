package com.hanxing.kxcharge.module.system.controller.admin.operator.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运营商 Response VO")
@Data
@ExcelIgnoreUnannotated
public class OperatorRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25509")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "企业名称", example = "李四")
    @ExcelProperty("企业名称")
    private String orgName;

    @Schema(description = "运营商名称", example = "李四")
    @ExcelProperty("运营商名称")
    private String operatorName;

    @Schema(description = "企业注册地址")
    @ExcelProperty("企业注册地址")
    private String address;

    @Schema(description = "统一社会信用代码")
    @ExcelProperty("统一社会信用代码")
    private String code;

    @Schema(description = "城市code")
    @ExcelProperty("城市code")
    private String cityCode;

    @Schema(description = "省份code")
    @ExcelProperty("省份code")
    private String provinceCode;

    @Schema(description = "城市名称", example = "芋艿")
    @ExcelProperty("城市名称")
    private String cityName;

    @Schema(description = "签约开始时间")
    @ExcelProperty("签约开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] signStartTime;

    @Schema(description = "签约结束时间")
    @ExcelProperty("签约结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] signEndTime;

    @Schema(description = "企业图标")
    @ExcelProperty("企业图标")
    private String businessLogo;

    @Schema(description = "备注说明", example = "你猜")
    @ExcelProperty("备注说明")
    private String remark;

    @Schema(description = "license截取")
    @ExcelProperty("license截取")
    private String codeAbbr;

    @Schema(description = "联系人")
    @ExcelProperty("联系人")
    private String contactUser;

    @Schema(description = "联系方式")
    @ExcelProperty("联系方式")
    private String contactNum;

    @Schema(description = "联系地址")
    @ExcelProperty("联系地址")
    private String contactAddress;

    @Schema(description = "联系邮箱")
    @ExcelProperty("联系邮箱")
    private String email;

    @Schema(description = "营业执照")
    @ExcelProperty("营业执照")
    private String businessLicense;

    @Schema(description = "状态")
    @ExcelProperty("状态")
    private Integer isFrozen;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
