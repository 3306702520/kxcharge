package com.hanxing.kxcharge.module.operations.framework.kxcharge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * domainName 配置属性
 *
 * @author 航兴新能源
 */
@ConfigurationProperties("kxcharge.domain-name")
@Data
public class KxchargeProperties {


    /**
     * URL
     */
    private String url;

}
