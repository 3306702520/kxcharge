package com.hanxing.kxcharge.module.pay.job.order;

import com.hanxing.kxcharge.framework.tenant.core.job.TenantJob;
import com.hanxing.kxcharge.module.pay.service.order.PayOrderService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付订单的过期 Job
 *
 * 支付超过过期时间时，支付渠道是不会通知进行过期，所以需要定时进行过期关闭。
 *
 * @author 航兴新能源
 */
@Component
@Slf4j
public class PayOrderExpireJob {

    @Resource
    private PayOrderService orderService;

    @XxlJob("payOrderExpireJob")
    @TenantJob // 多租户
    public void execute(String param) {
        int count = orderService.expireOrder();
        log.info("[execute][支付过期 ({}) 个]", count);
    }

}
