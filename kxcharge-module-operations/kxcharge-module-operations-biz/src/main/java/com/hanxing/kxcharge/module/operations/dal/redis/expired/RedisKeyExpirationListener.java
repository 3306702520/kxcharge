package com.hanxing.kxcharge.module.operations.dal.redis.expired;

import com.hanxing.kxcharge.framework.common.enums.ChargeEquipStatusEnum;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.ConnectorStatusInfoReqDto;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.mq.producer.interflow.InteconnectedProducer;
import com.hanxing.kxcharge.module.operations.service.equipmentconnector.EquipmentConnectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Slf4j
@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    @Resource
    InteconnectedProducer inteconnectedProducer;

    @Resource
    EquipmentConnectorService equipmentConnectorService;

    public RedisKeyExpirationListener(RedisMessageListenerContainer redisMessageListenerContainer) {
        super(redisMessageListenerContainer);
    }

    /**
     * 针对 redis 数据失效事件，进行数据处理
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 拿到key
        String key = message.toString();
        log.info("监听Redis key过期，key：{}，channel：{}", key, new String(pattern));

        int colonIndex = key.indexOf(":");

        if (colonIndex != -1 && colonIndex < key.length() - 1) {
            String value = key.substring(colonIndex + 1);
            EquipmentConnectorDO equipmentConnector = equipmentConnectorService.getEquipmentConnectorTenantless(value);
            if (ObjectUtils.isEmpty(equipmentConnector)) {
                log.info("未找到对应枪设备");
                return;
            }
            ConnectorStatusInfoReqDto connectorStatusInfoReqDto = new ConnectorStatusInfoReqDto();
            connectorStatusInfoReqDto.setStationId(equipmentConnector.getStationId());
            connectorStatusInfoReqDto.setStatus(ChargeEquipStatusEnum.ERROR.getStatus());
            connectorStatusInfoReqDto.setConnectorId(equipmentConnector.getConnectorCode());
            connectorStatusInfoReqDto.setLockStatus(0);
            connectorStatusInfoReqDto.setParkStatus(0);
            inteconnectedProducer.sendConnectorStatusInfo(connectorStatusInfoReqDto);
        }


    }
}
