package com.hanxing.kxcharge.framework.signature.config;

import com.hanxing.kxcharge.framework.redis.config.KxchargeRedisAutoConfiguration;
import com.hanxing.kxcharge.framework.signature.core.aop.ApiSignatureAspect;
import com.hanxing.kxcharge.framework.signature.core.redis.ApiSignatureRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * HTTP API 签名的自动配置类
 *
 * @author Zhougang
 */
@AutoConfiguration(after = KxchargeRedisAutoConfiguration.class)
public class KxchargeApiSignatureAutoConfiguration {

    @Bean
    public ApiSignatureAspect signatureAspect(ApiSignatureRedisDAO signatureRedisDAO) {
        return new ApiSignatureAspect(signatureRedisDAO);
    }

    @Bean
    public ApiSignatureRedisDAO signatureRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new ApiSignatureRedisDAO(stringRedisTemplate);
    }

}
