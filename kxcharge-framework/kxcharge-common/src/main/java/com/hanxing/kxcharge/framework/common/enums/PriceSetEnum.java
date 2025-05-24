package com.hanxing.kxcharge.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 价格设置类型枚举
 */
@AllArgsConstructor
@Getter
public enum PriceSetEnum implements IntArrayValuable {

    STATIONPRICE(1, "按电站定价"), // 按电站定价
    SEPARATEPRICE(2, "单独定价"); // 单独定价

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PriceSetEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    public static PriceSetEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value), PriceSetEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
