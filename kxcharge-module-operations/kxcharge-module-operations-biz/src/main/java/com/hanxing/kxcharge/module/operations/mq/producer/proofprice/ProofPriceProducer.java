package com.hanxing.kxcharge.module.operations.mq.producer.proofprice;

import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.BillingModelRespDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ProofPriceProducer {

    @Resource
    RabbitTemplate rabbitTemplate;

    public void sendProofPrice(BillingModelRespDto billingModelRespDto){
        log.info("发送计费模型");
        rabbitTemplate.convertAndSend(BillingModelRespDto.EXCHANGE,
                "",
                billingModelRespDto);
    }


}
