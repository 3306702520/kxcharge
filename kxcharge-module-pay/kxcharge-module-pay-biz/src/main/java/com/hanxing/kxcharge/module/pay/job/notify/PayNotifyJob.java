package com.hanxing.kxcharge.module.pay.job.notify;

import com.hanxing.kxcharge.framework.tenant.core.job.TenantJob;
import com.hanxing.kxcharge.module.pay.service.notify.PayNotifyService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付通知 Job
 * 通过不断扫描待通知的 PayNotifyTaskDO 记录，回调业务线的回调接口
 *
 * @author 航兴新能源
 */
@Component
@Slf4j
public class PayNotifyJob {

    @Resource
    private PayNotifyService payNotifyService;

    @XxlJob("payNotifyJob")
    @TenantJob // 多租户
    public void execute() throws Exception {
        int notifyCount = payNotifyService.executeNotify();
        log.info("[execute][执行支付通知 ({}) 个]", notifyCount);
    }

}
