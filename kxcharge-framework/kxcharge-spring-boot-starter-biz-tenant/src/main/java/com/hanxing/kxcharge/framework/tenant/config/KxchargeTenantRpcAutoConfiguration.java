package com.hanxing.kxcharge.framework.tenant.config;

import com.hanxing.kxcharge.framework.tenant.core.rpc.TenantRequestInterceptor;
import com.hanxing.kxcharge.module.system.api.tenant.TenantApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
@ConditionalOnProperty(prefix = "kxcharge.tenant", value = "enable", matchIfMissing = true) // 允许使用 kxcharge.tenant.enable=false 禁用多租户
@EnableFeignClients(clients = TenantApi.class) // 主要是引入相关的 API 服务
public class KxchargeTenantRpcAutoConfiguration {

    @Bean
    public TenantRequestInterceptor tenantRequestInterceptor() {
        return new TenantRequestInterceptor();
    }

}
