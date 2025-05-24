package com.hanxing.kxcharge.module.operations.mq.producer.remoterestart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RemoteRestartProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;


    private final static String EXCHANGE = "CHARGING_SERVER_REMOTE_RESTART";


    public void sendRemoteRestart(String pileNumber){
        log.info("发送远程重启指令,桩号为:{}", pileNumber);
        rabbitTemplate.convertAndSend(EXCHANGE,
                "",
                pileNumber);
    }

}
