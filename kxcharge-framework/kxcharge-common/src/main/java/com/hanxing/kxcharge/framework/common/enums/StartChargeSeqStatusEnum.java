package com.hanxing.kxcharge.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 充电订单状态
 */
@Getter
@AllArgsConstructor
public enum StartChargeSeqStatusEnum implements IntArrayValuable{

    CHARGE_STARTING(1, "启动中"),
    CHARGING(2, "充电中"),
    CHARGE_STOPPING(3, "停止中"),
    CHARGE_FINISHED(4, "已结束"),
    UNKNOWN(5, "未知");


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(StartChargeSeqStatusEnum::getStatus).toArray();

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

    public static StartChargeSeqStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(item -> item.getStatus().equals(value), StartChargeSeqStatusEnum.values());
    }
}
