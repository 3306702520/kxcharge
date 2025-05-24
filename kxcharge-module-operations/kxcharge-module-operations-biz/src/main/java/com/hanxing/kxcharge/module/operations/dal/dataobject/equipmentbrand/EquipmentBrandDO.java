package com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 设备品牌管理 DO
 *
 * @author kaixin
 */
@TableName("operations_equipment_brand")
@KeySequence("operations_equipment_brand_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentBrandDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 运营商id
     */
    private Long orgId;

}
