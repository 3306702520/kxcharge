package com.hanxing.kxcharge.module.pay.job.transfer;

import com.hanxing.kxcharge.framework.tenant.core.job.TenantJob;
import com.hanxing.kxcharge.module.pay.service.transfer.PayTransferService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 转账订单的同步 Job
 *
 * 由于转账订单的转账结果，有些渠道是异步通知进行同步的，考虑到异步通知可能会失败（小概率），所以需要定时进行同步。
 *
 * @author jason
 */
@Component
@Slf4j
public class PayTransferSyncJob {

    @Resource
    private PayTransferService transferService;

    @XxlJob("payTransferSyncJob")
    @TenantJob // 多租户
    public void execute(String param) {
        int count = transferService.syncTransfer();
        log.info("[execute][同步转账订单 ({}) 个]", count);
    }
}
