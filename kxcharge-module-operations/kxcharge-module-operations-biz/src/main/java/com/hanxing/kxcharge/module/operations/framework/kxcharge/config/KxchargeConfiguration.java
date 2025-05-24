package com.hanxing.kxcharge.module.operations.framework.kxcharge.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(KxchargeProperties.class)
public class KxchargeConfiguration {
}
