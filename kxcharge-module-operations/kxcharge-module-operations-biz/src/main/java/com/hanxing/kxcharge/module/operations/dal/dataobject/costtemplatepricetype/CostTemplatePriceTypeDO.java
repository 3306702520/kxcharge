package com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 计费模板价格类别 DO
 *
 * @author kaixin
 */
@TableName("operations_cost_template_price_type")
@KeySequence("operations_cost_template_price_type_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostTemplatePriceTypeDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 模板id
     */
    private Long costTemplateId;
    /**
     * 更新版本
     */
    private String protocolVersion;
    /**
     * 运营商id
     */
    private Long orgId;
    /**
     * 排序
     */
    private Integer identification;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 电费价格
     */
    private BigDecimal pricePower;
    /**
     * 服务价格
     */
    private BigDecimal priceService;

}
