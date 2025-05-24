package com.hanxing.kxcharge.module.operations.mq.consumer.heartbeat;


import com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector.OnlineEquipmentConnectorService;
import com.hanxing.kxcharge.module.pile.mq.Heartbeat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RabbitListener(queues = Heartbeat.QUEUE)
@Slf4j
public class PileHeartBeatConsumer {


    @Resource
    OnlineEquipmentConnectorService onlineEquipmentConnectorService;


    @RabbitHandler
    @Async
    public void onMessage(Heartbeat heartbeat){
        log.info("收到心跳消息{}", heartbeat);
        //更新在线状态
        onlineEquipmentConnectorService.heartBeat(heartbeat.getPileCode(), heartbeat.getGunNo(), heartbeat.getGunStatus());
    }


}
