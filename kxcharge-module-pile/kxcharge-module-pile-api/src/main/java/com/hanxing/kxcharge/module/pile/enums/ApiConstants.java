package com.hanxing.kxcharge.module.pile.enums;

import com.hanxing.kxcharge.framework.common.enums.RpcConstants;

public class ApiConstants {

    /**
     * 服务名
     * 注意，需要保证和 spring.application.name 保持一致
     */
    public static final String NAME = "pile-server";

    public static final String PREFIX = RpcConstants.RPC_API_PREFIX +  "/pile";

    public static final String VERSION = "1.0.0";
}
