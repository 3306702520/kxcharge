package com.hanxing.kxcharge.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements IntArrayValuable {

    BE_STARTING(1, "启动中"),
    CHARGING(2, "充电中"),
    IN_PROGRESS(3, "停止中"),
    HAVE_FINISHED(4, "已结束"),
    UNKNOWN(5, "未知"),
    ERROR(6,"异常");


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(OrderStatusEnum::getStatus).toArray();

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;


    public static OrderStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(item -> item.getStatus().equals(value), OrderStatusEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
