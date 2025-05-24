package com.hanxing.kxcharge.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ChargeStatusEnum  implements IntArrayValuable {

    INIT_CHARGE(1, "发起开始充电"),
    START_CHARGE(2, "收到成功充电通知,正式开始充电"),
    INIT_END_CHARGE(3, "发起结束充电"),
    INIT_END_CHARGE_REPLY(4, "收到结束充电通知"),
    END_CHARGE(5, "结束充电"),
    ACCEPT_ORDER(6, "接受订单"),
    INIT_CHARGE_ERROR(7, "发起充电失败"),
    END_CHARGE_ERROR(8, "发起结束充电失败"),
    ORDER_ERROR(9, "订单处理异常");


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ChargeStatusEnum::getStatus).toArray();

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;


    public static ChargeStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(item -> item.getStatus().equals(value), ChargeStatusEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
