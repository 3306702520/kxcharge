package com.hanxing.kxcharge.module.infra.framework.file.config;

import com.hanxing.kxcharge.module.infra.framework.file.core.client.FileClientFactory;
import com.hanxing.kxcharge.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 航兴新能源
 */
@Configuration(proxyBeanMethods = false)
public class KxchargeFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
