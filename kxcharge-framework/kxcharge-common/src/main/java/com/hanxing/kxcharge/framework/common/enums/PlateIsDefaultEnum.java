package com.hanxing.kxcharge.framework.common.enums;

import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PlateIsDefaultEnum implements IntArrayValuable {

    IS_DEFAULT(1),
    NOT_DEFAULT(0);


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PlateIsDefaultEnum::getStatus).toArray();

    private final Integer status;

    @Override
    public int[] array() {
        return new int[0];
    }
}
