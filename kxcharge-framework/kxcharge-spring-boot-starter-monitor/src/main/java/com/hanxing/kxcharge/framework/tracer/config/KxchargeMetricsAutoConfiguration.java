package com.hanxing.kxcharge.framework.tracer.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metrics 配置类
 *
 * @author 航兴新能源
 */
@AutoConfiguration
@ConditionalOnClass({MeterRegistryCustomizer.class})
@ConditionalOnProperty(prefix = "kxcharge.metrics", value = "enable", matchIfMissing = true) // 允许使用 kxcharge.metrics.enable=false 禁用 Metrics
public class KxchargeMetricsAutoConfiguration {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(
            @Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config().commonTags("application", applicationName);
    }

}
