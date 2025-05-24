package com.hanxing.kxcharge.module.operations.mq.consumer.remotestartcommand;

import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import com.hanxing.kxcharge.module.pile.mq.StartChargingCommandReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RabbitListener(queues = StartChargingCommandReply.QUEUE)
@Slf4j
public class RemoteStartCommandConsumer {

    @Resource
    private OrderService orderService;

    @RabbitHandler
    @Async
    public void onMessage(StartChargingCommandReply startChargingCommandReply){

        log.info("收到开启充电回复消息{}", startChargingCommandReply);
        orderService.startReplyCharge(startChargingCommandReply);
    }

}
