package com.hanxing.kxcharge.framework.tenant.config;

import com.hanxing.kxcharge.framework.common.enums.WebFilterOrderEnum;
import com.hanxing.kxcharge.framework.mybatis.core.util.MyBatisUtils;
import com.hanxing.kxcharge.framework.redis.config.KxchargeCacheProperties;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnoreAspect;
import com.hanxing.kxcharge.framework.tenant.core.db.TenantDatabaseInterceptor;
import com.hanxing.kxcharge.framework.tenant.core.job.TenantJobAspect;
import com.hanxing.kxcharge.framework.tenant.core.mq.rabbitmq.TenantRabbitMQInitializer;
import com.hanxing.kxcharge.framework.tenant.core.mq.redis.TenantRedisMessageInterceptor;
import com.hanxing.kxcharge.framework.tenant.core.mq.rocketmq.TenantRocketMQInitializer;
import com.hanxing.kxcharge.framework.tenant.core.redis.TenantRedisCacheManager;
import com.hanxing.kxcharge.framework.tenant.core.security.TenantSecurityWebFilter;
import com.hanxing.kxcharge.framework.tenant.core.service.TenantFrameworkService;
import com.hanxing.kxcharge.framework.tenant.core.service.TenantFrameworkServiceImpl;
import com.hanxing.kxcharge.framework.tenant.core.web.TenantContextWebFilter;
import com.hanxing.kxcharge.framework.web.config.WebProperties;
import com.hanxing.kxcharge.framework.web.core.handler.GlobalExceptionHandler;
import com.hanxing.kxcharge.module.system.api.tenant.TenantApi;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.BatchStrategies;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

@AutoConfiguration
@ConditionalOnProperty(prefix = "kxcharge.tenant", value = "enable", matchIfMissing = true) // 允许使用 kxcharge.tenant.enable=false 禁用多租户
@EnableConfigurationProperties(TenantProperties.class)
public class KxchargeTenantAutoConfiguration {

    @Bean
    public TenantFrameworkService tenantFrameworkService(TenantApi tenantApi) {
        return new TenantFrameworkServiceImpl(tenantApi);
    }

    // ========== AOP ==========

    @Bean
    public TenantIgnoreAspect tenantIgnoreAspect() {
        return new TenantIgnoreAspect();
    }

    // ========== DB ==========

    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties properties,
                                                                 MybatisPlusInterceptor interceptor) {
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(new TenantDatabaseInterceptor(properties));
        // 添加到 interceptor 中
        // 需要加在首个，主要是为了在分页插件前面。这个是 MyBatis Plus 的规定
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    // ========== WEB ==========

    @Bean
    public FilterRegistrationBean<TenantContextWebFilter> tenantContextWebFilter() {
        FilterRegistrationBean<TenantContextWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextWebFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_CONTEXT_FILTER);
        return registrationBean;
    }

    // ========== Security ==========

    @Bean
    public FilterRegistrationBean<TenantSecurityWebFilter> tenantSecurityWebFilter(TenantProperties tenantProperties,
                                                                                   WebProperties webProperties,
                                                                                   GlobalExceptionHandler globalExceptionHandler,
                                                                                   TenantFrameworkService tenantFrameworkService) {
        FilterRegistrationBean<TenantSecurityWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantSecurityWebFilter(tenantProperties, webProperties,
                globalExceptionHandler, tenantFrameworkService));
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_SECURITY_FILTER);
        return registrationBean;
    }

    // ========== Job ==========

    @Bean
    @ConditionalOnClass(name = "com.xxl.job.core.handler.annotation.XxlJob")
    public TenantJobAspect tenantJobAspect(TenantFrameworkService tenantFrameworkService) {
        return new TenantJobAspect(tenantFrameworkService);
    }

    // ========== MQ ==========

    /**
     * 多租户 Redis 消息队列的配置类
     *
     * 为什么要单独一个配置类呢？如果直接把 TenantRedisMessageInterceptor Bean 的初始化放外面，会报 RedisMessageInterceptor 类不存在的错误
     */
    @Configuration
    @ConditionalOnClass(name = "com.hanxing.kxcharge.framework.mq.redis.core.RedisMQTemplate")
    public static class TenantRedisMQAutoConfiguration {

        @Bean
        public TenantRedisMessageInterceptor tenantRedisMessageInterceptor() {
            return new TenantRedisMessageInterceptor();
        }

    }

    @Bean
    @ConditionalOnClass(name = "org.springframework.amqp.rabbit.core.RabbitTemplate")
    public TenantRabbitMQInitializer tenantRabbitMQInitializer() {
        return new TenantRabbitMQInitializer();
    }

    @Bean
    @ConditionalOnClass(name = "org.apache.rocketmq.spring.core.RocketMQTemplate")
    public TenantRocketMQInitializer tenantRocketMQInitializer() {
        return new TenantRocketMQInitializer();
    }

    // ========== Redis ==========

    @Bean
    @Primary // 引入租户时，tenantRedisCacheManager 为主 Bean
    public RedisCacheManager tenantRedisCacheManager(RedisTemplate<String, Object> redisTemplate,
                                                     RedisCacheConfiguration redisCacheConfiguration,
                                                     KxchargeCacheProperties kxchargeCacheProperties,
                                                     TenantProperties tenantProperties) {
        // 创建 RedisCacheWriter 对象
        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory,
                BatchStrategies.scan(kxchargeCacheProperties.getRedisScanBatchSize()));
        // 创建 TenantRedisCacheManager 对象
        return new TenantRedisCacheManager(cacheWriter, redisCacheConfiguration, tenantProperties.getIgnoreCaches());
    }
}
