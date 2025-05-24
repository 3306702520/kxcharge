package com.hanxing.kxcharge.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.hanxing.kxcharge.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 充电设备接口状态
 *
 * @author 航兴新能源
 */
@Getter
@AllArgsConstructor
public enum ChargeEquipStatusEnum implements IntArrayValuable {

    DISCONNECT(0, "未连接"),
    FREE_TIME(1, "空闲"),
    OCCUPATION_UN_CHARGE(2, "占用(未充电)"),
    OCCUPATION_CHARGING(3, "占用(充电中)"),
    OCCUPATION_BOOKING(4, "占用(预约锁定)"),
    UNKNOWN(5, "未知"),
    ERROR(255, "故障");


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ChargeEquipStatusEnum::getStatus).toArray();

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;


    public static ChargeEquipStatusEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(item -> item.getStatus().equals(value), ChargeEquipStatusEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
