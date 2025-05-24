package com.hanxing.kxcharge.framework.tenant.core.rpc;

import com.hanxing.kxcharge.framework.tenant.core.context.TenantContextHolder;
import com.hanxing.kxcharge.framework.web.core.util.WebFrameworkUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import static com.hanxing.kxcharge.framework.web.core.util.WebFrameworkUtils.HEADER_TENANT_ID;

/**
 * Tenant 的 RequestInterceptor 实现类：Feign 请求时，将 {@link TenantContextHolder} 设置到 header 中，继续透传给被调用的服务
 *
 * @author 航兴新能源
 */
public class TenantRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            requestTemplate.header(HEADER_TENANT_ID, String.valueOf(tenantId));
        }
    }

}
