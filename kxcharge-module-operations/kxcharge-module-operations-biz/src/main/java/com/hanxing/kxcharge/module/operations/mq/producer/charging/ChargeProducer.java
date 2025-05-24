package com.hanxing.kxcharge.module.operations.mq.producer.charging;

import com.hanxing.kxcharge.module.pile.mq.RemoteControlOfStartupOnTheOperationPlatformDto;
import com.hanxing.kxcharge.module.pile.mq.RemoteShutdownOfOperationPlatformDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class ChargeProducer {


    @Resource
    RabbitTemplate rabbitTemplate;



    public void startCharge(RemoteControlOfStartupOnTheOperationPlatformDto remoteControlOfStartupOnTheOperationPlatformDto){
        rabbitTemplate.convertAndSend(RemoteControlOfStartupOnTheOperationPlatformDto.EXCHANGE,
                "",
                remoteControlOfStartupOnTheOperationPlatformDto);
    }

    public void endCharge(RemoteShutdownOfOperationPlatformDto remoteShutdownOfOperationPlatformDto){
        rabbitTemplate.convertAndSend(RemoteShutdownOfOperationPlatformDto.EXCHANGE,
                "",
                remoteShutdownOfOperationPlatformDto);
    }


}
