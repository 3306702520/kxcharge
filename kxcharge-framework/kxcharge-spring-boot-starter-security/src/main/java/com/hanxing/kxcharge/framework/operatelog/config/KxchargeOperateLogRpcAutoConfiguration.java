package com.hanxing.kxcharge.framework.operatelog.config;

import com.hanxing.kxcharge.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * OperateLog 使用到 Feign 的配置项
 *
 * @author 航兴新能源
 */
@AutoConfiguration
@EnableFeignClients(clients = {OperateLogApi.class}) // 主要是引入相关的 API 服务
public class KxchargeOperateLogRpcAutoConfiguration {
}
