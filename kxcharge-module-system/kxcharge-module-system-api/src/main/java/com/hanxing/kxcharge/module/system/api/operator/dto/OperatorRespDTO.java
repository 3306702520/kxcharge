package com.hanxing.kxcharge.module.system.api.operator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "")
@Data
public class OperatorRespDTO {

    /**
     * 主键
     */
    private Long id;
    /**
     * 企业名称
     */
    private String orgName;
    /**
     * 运营商名称
     */
    private String operatorName;
    /**
     * 企业注册地址
     */
    private String address;
    /**
     * 统一社会信用代码
     */
    private String code;
    /**
     * license截取
     */
    private String codeAbbr;
    /**
     * 联系人
     */
    private String contactUser;
    /**
     * 联系方式
     */
    private String contactNum;
    /**
     * 联系地址
     */
    private String contactAddress;
    /**
     * 联系邮箱
     */
    private String email;
    /**
     * 城市code
     */
    private String cityCode;
    /**
     * 省份code
     */
    private String provinceCode;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 签约开始时间
     */
    private LocalDateTime signStartTime;
    /**
     * 签约结束时间
     */
    private LocalDateTime signEndTime;
    /**
     * 企业图标
     */
    private String businessLogo;
    /**
     * 备注说明
     */
    private String remark;
    /**
     * 营业执照
     */
    private String businessLicense;
    /**
     * 状态
     */
    private Integer isFrozen;


    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    private String creator;
    /**
     * 更新者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    private String updater;
    /**
     * 是否删除
     */
    private Boolean deleted;
}
