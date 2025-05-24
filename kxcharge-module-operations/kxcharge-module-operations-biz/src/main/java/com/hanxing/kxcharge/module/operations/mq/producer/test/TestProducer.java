package com.hanxing.kxcharge.module.operations.mq.producer.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class TestProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;


    public void sendTest(String message){
        rabbitTemplate.convertAndSend("icpc.fanout", "", message);
    }



}
