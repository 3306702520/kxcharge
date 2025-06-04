package com.hanxing.kxcharge.module.system.controller.admin.operator.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 运营商新增/修改 Request VO")
@Data
public class OperatorSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "25509")
    private Long id;

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
    private LocalDateTime signStartTime;

    @Schema(description = "签约结束时间")
    private LocalDateTime signEndTime;

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

}
