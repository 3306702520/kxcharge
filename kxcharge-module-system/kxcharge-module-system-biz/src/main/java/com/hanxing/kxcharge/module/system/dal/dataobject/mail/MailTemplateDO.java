package com.hanxing.kxcharge.module.system.dal.dataobject.mail;

import com.hanxing.kxcharge.framework.common.enums.CommonStatusEnum;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 邮件模版 DO
 *
 * @author wangjingyi
 * @since 2022-03-21
 */
@TableName(value = "system_mail_template", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class MailTemplateDO extends BaseDO {

    /**
     * 主键
     */
    private Long id;
    /**
     * 模版名称
     */
    private String name;
    /**
     * 模版编号
     */
    private String code;
    /**
     * 发送的邮箱账号编号
     *
     * 关联 {@link MailAccountDO#getId()}
     */
    private Long accountId;

    /**
     * 发送人名称
     */
    private String nickname;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 参数数组(自动根据内容生成)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;
    /**
     * 状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;

}
