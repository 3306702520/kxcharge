package com.hanxing.kxcharge.module.operations.enums.coupon;

import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 优惠劵模板的有限期类型的枚举
 *
 * @author 航兴新能源
 */
@AllArgsConstructor
@Getter
public enum CouponTemplateValidityTypeEnum implements IntArrayValuable {

    DATE(1, "固定日期"),
    TERM(2, "领取之后"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CouponTemplateValidityTypeEnum::getType).toArray();

    /**
     * 值
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
