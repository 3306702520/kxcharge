package com.hanxing.kxcharge.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author 航兴新能源
 */
@ConfigurationProperties(prefix = "kxcharge.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "kxcharge.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
