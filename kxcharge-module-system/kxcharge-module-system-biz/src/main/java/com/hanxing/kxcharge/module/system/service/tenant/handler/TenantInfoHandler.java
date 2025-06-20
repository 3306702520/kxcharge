package com.hanxing.kxcharge.module.system.service.tenant.handler;

import com.hanxing.kxcharge.module.system.dal.dataobject.tenant.TenantDO;

/**
 * 租户信息处理
 * 目的：尽量减少租户逻辑耦合到系统中
 *
 * @author 航兴新能源
 */
public interface TenantInfoHandler {

    /**
     * 基于传入的租户信息，进行相关逻辑的执行
     * 例如说，创建用户时，超过最大账户配额
     *
     * @param tenant 租户信息
     */
    void handle(TenantDO tenant);

}
