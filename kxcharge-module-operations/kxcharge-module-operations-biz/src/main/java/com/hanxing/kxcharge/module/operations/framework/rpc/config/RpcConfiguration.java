package com.hanxing.kxcharge.module.operations.framework.rpc.config;

import com.hanxing.kxcharge.module.infra.api.file.FileApi;
import com.hanxing.kxcharge.module.member.api.plate.MemberPlateApi;
import com.hanxing.kxcharge.module.member.api.user.MemberUserApi;
import com.hanxing.kxcharge.module.pay.api.wallet.PayWalletApi;
import com.hanxing.kxcharge.module.system.api.operator.OperatorApi;
import com.hanxing.kxcharge.module.system.api.tenant.TenantApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {FileApi.class, MemberPlateApi.class, MemberUserApi.class, PayWalletApi.class, OperatorApi.class, TenantApi.class})
public class RpcConfiguration {
}
