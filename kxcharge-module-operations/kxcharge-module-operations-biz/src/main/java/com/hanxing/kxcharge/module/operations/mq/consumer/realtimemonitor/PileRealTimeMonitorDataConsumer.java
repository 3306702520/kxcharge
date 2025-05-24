package com.hanxing.kxcharge.module.operations.mq.consumer.realtimemonitor;

import cn.hutool.core.bean.BeanUtil;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationEquipChargeStatusReqDto;
import com.hanxing.kxcharge.module.operations.convert.interflow.InterconnectedConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.realtimeorder.RealTimeOrderDO;
import com.hanxing.kxcharge.module.operations.mq.producer.interflow.InteconnectedProducer;
import com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector.OnlineEquipmentConnectorService;
import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import com.hanxing.kxcharge.module.operations.service.realtimeorder.RealTimeOrderService;
import com.hanxing.kxcharge.module.pile.mq.RealTimeMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RabbitListener(queues = RealTimeMonitor.QUEUE)
@Slf4j
public class PileRealTimeMonitorDataConsumer {

    public static final String NOT_TRADE = "00000000000000000000000000000000";

    @Resource
    private OnlineEquipmentConnectorService onlineEquipmentConnectorService;

    @Resource
    private RealTimeOrderService realTimeOrderService;

    @Resource
    private InteconnectedProducer inteconnectedProducer;

    @Resource
    private OrderService orderService;



    @RabbitHandler
    @Async
    public void onMessage(RealTimeMonitor realTimeMonitor){
        log.info("收到实时检测消息{}", realTimeMonitor);
        //更新在线状态
        onlineEquipmentConnectorService.realTimeMonitor(realTimeMonitor.getPileCode(),
                realTimeMonitor.getGunNo(),
                realTimeMonitor.getState(),
                realTimeMonitor.getGunHomingStatus(),
                realTimeMonitor.getInsertGunStatus());
        // 更新订单状态
        orderService.errorStart(realTimeMonitor);

        if (NOT_TRADE.equals(realTimeMonitor.getTransactionSerialNo())){
            log.info("该桩未充电");

            //查询该桩是否有刚结束的订单
            orderService.monitoringCharge(realTimeMonitor);


        }else {
            log.info("该桩已在充电");
            RealTimeOrderDO realTimeOrderDO = BeanUtil.copyProperties(realTimeMonitor, RealTimeOrderDO.class);
            realTimeOrderService.save(realTimeOrderDO);

            OrderDO orderDO = orderService.checkBalance(realTimeMonitor);


            //发送推送消息
            NotificationEquipChargeStatusReqDto chargeStatusReqDto = InterconnectedConvert.INSTANCE.convert01(realTimeMonitor);
            chargeStatusReqDto.setStationId(onlineEquipmentConnectorService.getStationId(realTimeMonitor.getPileCode() + realTimeMonitor.getGunNo()));
            chargeStatusReqDto.setTradeSeq(orderDO.getTradeSeq());
            inteconnectedProducer.sendNotificationEquipChargeStatus(chargeStatusReqDto);

        }
        //todo zxy
    }
}
