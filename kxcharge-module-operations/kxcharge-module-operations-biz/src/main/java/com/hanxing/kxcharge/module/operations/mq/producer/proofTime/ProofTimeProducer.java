package com.hanxing.kxcharge.module.operations.mq.producer.proofTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ProofTimeProducer {



    @Resource
    private RabbitTemplate rabbitTemplate;


    private final static String EXCHANGE = "CHARGING_SERVER_PROOF_TIME";


    public void sendProofTime(String pileNumber){
        log.info("发送远程命令指令,桩号为:{}", pileNumber);
        rabbitTemplate.convertAndSend(EXCHANGE,
                "",
                pileNumber);
    }
}
