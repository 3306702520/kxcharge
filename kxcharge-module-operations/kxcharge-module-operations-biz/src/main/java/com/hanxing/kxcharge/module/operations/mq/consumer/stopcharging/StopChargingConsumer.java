package com.hanxing.kxcharge.module.operations.mq.consumer.stopcharging;

import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import com.hanxing.kxcharge.module.pile.mq.StopCharging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RabbitListener(queues = StopCharging.QUEUE)
@Slf4j
public class StopChargingConsumer {

    @Resource
    private OrderService orderService;


    @RabbitHandler
    @Async
    public void onMessage(StopCharging stopCharging){

        log.info("收到停止充电消息{}", stopCharging);
        orderService.stopCharging(stopCharging);
    }
}
