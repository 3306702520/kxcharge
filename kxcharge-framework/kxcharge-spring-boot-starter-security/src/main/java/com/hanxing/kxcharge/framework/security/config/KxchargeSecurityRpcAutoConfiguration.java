package com.hanxing.kxcharge.framework.security.config;

import com.hanxing.kxcharge.framework.security.core.rpc.LoginUserRequestInterceptor;
import com.hanxing.kxcharge.module.system.api.oauth2.OAuth2TokenApi;
import com.hanxing.kxcharge.module.system.api.permission.PermissionApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author 航兴新能源
 */
@AutoConfiguration
@EnableFeignClients(clients = {OAuth2TokenApi.class, // 主要是引入相关的 API 服务
        PermissionApi.class})
public class KxchargeSecurityRpcAutoConfiguration {

    @Bean
    public LoginUserRequestInterceptor loginUserRequestInterceptor() {
        return new LoginUserRequestInterceptor();
    }

}
