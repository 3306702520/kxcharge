package com.hanxing.kxcharge.framework.pay.config;

import com.hanxing.kxcharge.framework.pay.core.client.PayClientFactory;
import com.hanxing.kxcharge.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author 航兴新能源
 */
@AutoConfiguration
public class KxchargePayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
