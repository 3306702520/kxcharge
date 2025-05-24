package com.hanxing.kxcharge.framework.common.enums;

import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum IntegralRuleEnum implements IntArrayValuable {
    /**
     * 充电积分
     */
    CHARGING_INTEGRAL(1, "充电积分"),
    /**
     * 注册积分
     */
    REGISTRATION(2, "注册积分"),
    /**
     * 停车优惠券
     */
    PARKING_COUPON(3, "停车优惠卷"),
    /**
     * 充电优惠券
     */
    CHARGING_COUPON(4, "充电优惠券"),
    /**
     * 订单
     */
    ORDER_FORM(5, "订单");


    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(IntegralRuleEnum::getStatus).toArray();

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
