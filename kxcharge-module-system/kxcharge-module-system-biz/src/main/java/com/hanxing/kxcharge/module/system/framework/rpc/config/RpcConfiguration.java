package com.hanxing.kxcharge.module.system.framework.rpc.config;

import com.hanxing.kxcharge.module.infra.api.config.ConfigApi;
import com.hanxing.kxcharge.module.infra.api.file.FileApi;
import com.hanxing.kxcharge.module.infra.api.websocket.WebSocketSenderApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, WebSocketSenderApi.class, ConfigApi.class})
public class RpcConfiguration {
}
