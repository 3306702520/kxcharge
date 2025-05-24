package com.hanxing.kxcharge.module.operations.mq.producer.interflow;

import com.hanxing.kxcharge.module.operations.api.interflow.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 对系统所有的站点产生信息，传站点id.互联互通模块进行判断发送
 */
@Slf4j
@Component
public class InteconnectedProducer {

    @Resource
    RabbitTemplate rabbitTemplate;

    public void sendConnectorStatusInfo(ConnectorStatusInfoReqDto connectorStatusInfoReqDto){
        log.info("发送设备状态变化");
        rabbitTemplate.convertAndSend(ConnectorStatusInfoReqDto.QUEUE, connectorStatusInfoReqDto);
    }


    /**
     * 实时订单详情
     * @param sendNotificationEquipChargeStatus
     */
    public void sendNotificationEquipChargeStatus(NotificationEquipChargeStatusReqDto sendNotificationEquipChargeStatus){
        log.info("发送实时订单详情");
        rabbitTemplate.convertAndSend(NotificationEquipChargeStatusReqDto.QUEUE, sendNotificationEquipChargeStatus);
    }


    /**
     * 订单结算详情
     * @param sendNotificationChargeOrderInfo
     */
    public void sendNotificationChargeOrderInfo(NotificationChargeOrderInfoReqDto sendNotificationChargeOrderInfo){
        log.info("发送订单结算");
        rabbitTemplate.convertAndSend(NotificationChargeOrderInfoReqDto.QUEUE, sendNotificationChargeOrderInfo);
    }



    /**
     * 站点状态变更
     * @param stationChangeReqDto
     */
    public void sendNotificationStationChange(NotificationStationChangeReqDto stationChangeReqDto){
        log.info("发送站点状态变更");
        rabbitTemplate.convertAndSend(NotificationStationChangeReqDto.QUEUE, stationChangeReqDto);
    }


    /**
     * 开启充电结果
     * @param startChargeReqDto
     */
    public void sendNotificationStartCharge(NotificationStartChargeReqDto startChargeReqDto){
        log.info("生产者-发送开启充电结果");
        rabbitTemplate.convertAndSend(NotificationStartChargeReqDto.QUEUE, startChargeReqDto);
    }



    /**
     * 结束结果
     * @param stopChargeReqDto
     */
    public void sendNotificationStopCharge(NotificationStopChargeReqDto stopChargeReqDto){
        log.info("发送停止充电结果");
        rabbitTemplate.convertAndSend(NotificationStopChargeReqDto.QUEUE, stopChargeReqDto);
    }


}
