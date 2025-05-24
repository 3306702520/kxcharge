package com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 计费模板 DO
 *
 * @author kaixin
 */
@TableName("operations_cost_template")
@KeySequence("operations_cost_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostTemplateDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 模板名称
     */
    private String templateName;
    /**
     * 更新版本
     */
    private Long protocolVersion;
    /**
     * 是否使用
     *
     * 枚举 {@link TODO cost_template_used 对应的类}
     */
    private Integer isUsed;
    /**
     * 运营商id
     */
    private Long orgId;

    /**
     * 备注
     */
    private String remark;

}
