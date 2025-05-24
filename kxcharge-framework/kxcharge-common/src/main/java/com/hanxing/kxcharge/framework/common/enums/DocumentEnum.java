package com.hanxing.kxcharge.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档地址
 *
 * @author 航兴新能源
 */
@Getter
@AllArgsConstructor
public enum DocumentEnum {

    REDIS_INSTALL("https://gitee.com/zhijiantianya/ruoyi-vue-pro/issues/I4VCSJ", "Redis 安装文档"),
    TENANT("https://cdzdoc.kxmall.vip", "SaaS 多租户文档");

    private final String url;
    private final String memo;

}
