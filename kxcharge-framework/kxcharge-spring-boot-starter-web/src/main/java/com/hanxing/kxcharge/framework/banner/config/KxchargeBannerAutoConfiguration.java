package com.hanxing.kxcharge.framework.banner.config;

import com.hanxing.kxcharge.framework.banner.core.BannerApplicationRunner;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * Banner 的自动配置类
 *
 * @author 航兴新能源
 */
@AutoConfiguration
public class KxchargeBannerAutoConfiguration {

    @Bean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }

}
