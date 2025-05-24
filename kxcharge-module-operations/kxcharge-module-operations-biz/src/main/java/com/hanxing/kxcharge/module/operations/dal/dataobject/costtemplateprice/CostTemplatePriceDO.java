package com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 计费模板价格时段 DO
 *
 * @author 航兴新能源
 */
@TableName("operations_cost_template_price")
@KeySequence("operations_cost_template_price_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CostTemplatePriceDO extends BaseDO {

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
     * 48段开始
     */
    private Integer startTime;
    /**
     * 48段结束
     */
    private Integer endTime;
    /**
     * 开始时间
     */
    private String startTimeStr;
    /**
     * 结束时间
     */
    private String endTimeStr;
    /**
     * 价格类型id
     */
    private String priceTypeId;

}
