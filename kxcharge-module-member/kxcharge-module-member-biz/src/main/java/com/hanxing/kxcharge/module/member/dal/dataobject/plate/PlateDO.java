package com.hanxing.kxcharge.module.member.dal.dataobject.plate;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hanxing.kxcharge.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 用户车牌信息 DO
 *
 * @author 航兴新能源
 */
@TableName("member_plate")
@KeySequence("member_plate_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlateDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 车辆类型
     *
     * 枚举 {@link TODO plate_type 对应的类}
     */
    private Integer plateType;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 最近充电地点
     */
    private String latelyPlace;
    /**
     * 最近充电时间
     */
    private LocalDateTime latelyTime;
    /**
     * 充电次数
     */
    private Integer chargeQuantity;
    /**
     * 是否为默认
     */
    private Integer isDefault;

}
