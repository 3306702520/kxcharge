package com.hanxing.kxcharge.module.operations.enums.coupon;

import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 优惠劵领取方式
 *
 * @author 航兴新能源
 */
@AllArgsConstructor
@Getter
public enum CouponTakeTypeEnum implements IntArrayValuable {

    USER(1, "直接领取"), // 用户可在首页、每日领劵直接领取
    ADMIN(2, "指定发放"), // 后台指定会员赠送优惠劵
    REGISTER(3, "新人券"), // 注册时自动领取
    BUY(4, "积分商城购买")
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CouponTakeTypeEnum::getValue).toArray();

    /**
     * 值
     */
    private final Integer value;
    /**
     * 名字
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
