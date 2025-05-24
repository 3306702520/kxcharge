package com.hanxing.kxcharge.module.operations.mq.producer.remoteissueqrcode;

import com.hanxing.kxcharge.module.pile.mq.RemoteIssueQRCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class RemoteIssueQRCodeProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;


    public void sendIssueQRCode(RemoteIssueQRCode remoteIssueQRCode){
        rabbitTemplate.convertAndSend(RemoteIssueQRCode.EXCHANGE,
                "",
                remoteIssueQRCode);
    }

}
