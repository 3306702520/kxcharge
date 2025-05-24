package com.hanxing.kxcharge.framework.mq.redis.config;

import com.hanxing.kxcharge.framework.mq.redis.core.RedisMQTemplate;
import com.hanxing.kxcharge.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import com.hanxing.kxcharge.framework.redis.config.KxchargeRedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;

/**
 * Redis 消息队列 Producer 配置类
 *
 * @author 航兴新能源
 */
@Slf4j
@AutoConfiguration(after = KxchargeRedisAutoConfiguration.class)
public class KxchargeRedisMQProducerAutoConfiguration {

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate,
                                           List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 监听所有库的key过期事件
        container.setConnectionFactory(connectionFactory);
        return container;
    }

}
