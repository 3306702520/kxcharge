package com.hanxing.kxcharge.module.operations.mq.consumer.remoteendcommand;

import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import com.hanxing.kxcharge.module.pile.mq.RemoteShutdownCommandReply;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RabbitListener(queues = RemoteShutdownCommandReply.QUEUE)
@Slf4j
public class RemoteEndCommandConsumer {

    @Resource
    OrderService orderService;

    @RabbitHandler
    @Async
    public void onMessage(RemoteShutdownCommandReply remoteShutdownCommandReply){
        log.info("收到结束充电回复消息{}", remoteShutdownCommandReply);

        orderService.endReplyCharge(remoteShutdownCommandReply);
    }


}
