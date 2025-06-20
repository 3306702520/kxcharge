package com.hanxing.kxcharge.module.system.framework.sms.config;

import com.hanxing.kxcharge.module.system.framework.sms.core.client.SmsClientFactory;
import com.hanxing.kxcharge.module.system.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 短信配置类，包括短信客户端、短信验证码两部分
 *
 * @author 航兴新能源
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(SmsCodeProperties.class)
public class SmsConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
