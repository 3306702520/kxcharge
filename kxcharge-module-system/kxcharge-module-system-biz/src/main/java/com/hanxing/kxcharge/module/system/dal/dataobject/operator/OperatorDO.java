package com.hanxing.kxcharge.module.system.dal.dataobject.operator;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;

/**
 * 运营商 DO
 *
 * @author 航兴新能源
 */
@TableName("system_operator")
@KeySequence("system_operator_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperatorDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
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
     * 营业执照
     */
    private String businessLicense;
    /**
     * 状态
     */
    private Integer isFrozen;

}
