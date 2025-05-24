package com.hanxing.kxcharge.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 车辆类型枚举
 */
@AllArgsConstructor
@Getter
public enum PlateTypeEnum implements IntArrayValuable {

    NEWENERGY(1, "新能源"), // 新能源
    HYBRID(2, "混动"); // 混动"

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PlateTypeEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    public static PlateTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value), PlateTypeEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
