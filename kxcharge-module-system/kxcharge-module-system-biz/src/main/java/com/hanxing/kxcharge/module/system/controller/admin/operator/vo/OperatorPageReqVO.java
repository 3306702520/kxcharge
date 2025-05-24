package com.hanxing.kxcharge.module.system.controller.admin.operator.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运营商分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OperatorPageReqVO extends PageParam {

    @Schema(description = "企业名称", example = "李四")
    private String orgName;

    @Schema(description = "运营商名称", example = "李四")
    private String operatorName;

    @Schema(description = "企业注册地址")
    private String address;

    @Schema(description = "统一社会信用代码")
    private String code;

    @Schema(description = "城市code")
    private String cityCode;

    @Schema(description = "省份code")
    private String provinceCode;

    @Schema(description = "城市名称", example = "芋艿")
    private String cityName;

    @Schema(description = "签约开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] signStartTime;

    @Schema(description = "签约结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] signEndTime;

    @Schema(description = "企业图标")
    private String businessLogo;

    @Schema(description = "备注说明", example = "你猜")
    private String remark;

    @Schema(description = "license截取")
    private String codeAbbr;

    @Schema(description = "联系人")
    private String contactUser;

    @Schema(description = "联系方式")
    private String contactNum;

    @Schema(description = "联系地址")
    private String contactAddress;

    @Schema(description = "联系邮箱")
    private String email;

    @Schema(description = "营业执照")
    private String businessLicense;

    @Schema(description = "状态")
    private Integer isFrozen;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
