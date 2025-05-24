package com.hanxing.kxcharge.framework.common.enums;

import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum StopChargeMessageEnum implements IntArrayValuable {
    INITIATIVE(0, "主动结束"),
    FULL(1, "充满电"),
    INSUFFICIENT_BALANCE(2, "余额不足"),
    ERROR(3, "异常");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(StopChargeMessageEnum::getStatus).toArray();

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;


    @Override
    public int[] array() {
        return new int[0];
    }
}
