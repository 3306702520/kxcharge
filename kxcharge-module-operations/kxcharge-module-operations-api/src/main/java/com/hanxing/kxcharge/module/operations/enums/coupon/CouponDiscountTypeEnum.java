package com.hanxing.kxcharge.module.operations.enums.coupon;

import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 优惠类型枚举
 *
 * @author 航兴新能源
 */
@Getter
@AllArgsConstructor
public enum CouponDiscountTypeEnum implements IntArrayValuable {

    PRICE(1, "满减"), // 具体金额
    PERCENT(2, "折扣"), // 百分比
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CouponDiscountTypeEnum::getType).toArray();

    /**
     * 优惠类型
     */
    private final Integer type;
    /**
     * 名字
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
