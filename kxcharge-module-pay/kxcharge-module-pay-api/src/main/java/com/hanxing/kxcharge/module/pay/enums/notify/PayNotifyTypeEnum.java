package com.hanxing.kxcharge.module.pay.enums.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付通知类型
 *
 * @author 航兴新能源
 */
@Getter
@AllArgsConstructor
public enum PayNotifyTypeEnum {

    ORDER(1, "支付单"),
    REFUND(2, "退款单"),
    TRANSFER(3, "转账单")
    ;

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 名字
     */
    private final String name;

}
