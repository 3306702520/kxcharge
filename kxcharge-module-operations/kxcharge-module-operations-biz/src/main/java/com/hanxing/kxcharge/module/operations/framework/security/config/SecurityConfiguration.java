package com.hanxing.kxcharge.module.operations.framework.security.config;

import com.hanxing.kxcharge.framework.security.config.AuthorizeRequestsCustomizer;
import com.hanxing.kxcharge.module.operations.enums.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * operations 模块的 web 组件的 Configuration
 *
 * @author kxcharge
 */
@Configuration("operationsSecurityConfiguration")
public class SecurityConfiguration {

    @Bean("operationsAuthorizeRequestsCustomizer")
    public AuthorizeRequestsCustomizer authorizeRequestsCustomizer() {
        return new AuthorizeRequestsCustomizer() {

            @Override
            public void customize(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
                // Swagger 接口文档
                registry.antMatchers("/v3/api-docs/**").permitAll()
                        .antMatchers("/webjars/**").permitAll()
                        .antMatchers("/swagger-ui").permitAll()
                        .antMatchers("/swagger-ui/**").permitAll();
                // Spring Boot Actuator 的安全配置
                registry.antMatchers("/actuator").anonymous()
                        .antMatchers("/actuator/**").anonymous();
                // Druid 监控
                registry.antMatchers("/druid/**").anonymous();
                // RPC 服务的安全配置
                registry.antMatchers(ApiConstants.PREFIX + "/**").permitAll();
            }

        };
    }

}
