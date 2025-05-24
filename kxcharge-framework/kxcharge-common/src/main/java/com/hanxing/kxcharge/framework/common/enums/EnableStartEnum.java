package com.hanxing.kxcharge.framework.common.enums;

import cn.hutool.core.util.ObjUtil;
import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 启停枚举
 *
 * @author 航兴新能源
 */
@Getter
@AllArgsConstructor
public enum EnableStartEnum implements IntArrayValuable {

    ENABLE(1, "开启"),
    DISABLE(0, "关闭");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(EnableStartEnum::getStatus).toArray();

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
        return ARRAYS;
    }

    public static boolean isEnable(Integer status) {
        return ObjUtil.equal(ENABLE.status, status);
    }

    public static boolean isDisable(Integer status) {
        return ObjUtil.equal(DISABLE.status, status);
    }

}
