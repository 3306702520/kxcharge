package com.hanxing.kxcharge.framework.apilog.config;

import com.hanxing.kxcharge.framework.apilog.core.filter.ApiAccessLogFilter;
import com.hanxing.kxcharge.framework.apilog.core.interceptor.ApiAccessLogInterceptor;
import com.hanxing.kxcharge.framework.common.enums.WebFilterOrderEnum;
import com.hanxing.kxcharge.framework.web.config.WebProperties;
import com.hanxing.kxcharge.framework.web.config.KxchargeWebAutoConfiguration;
import com.hanxing.kxcharge.module.infra.api.logger.ApiAccessLogApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@AutoConfiguration(after = KxchargeWebAutoConfiguration.class)
public class KxchargeApiLogAutoConfiguration implements WebMvcConfigurer {

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "kxcharge.access-log", value = "enable", matchIfMissing = true) // 允许使用 kxcharge.access-log.enable=false 禁用访问日志
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogApi apiAccessLogApi) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogApi);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiAccessLogInterceptor());
    }

}
