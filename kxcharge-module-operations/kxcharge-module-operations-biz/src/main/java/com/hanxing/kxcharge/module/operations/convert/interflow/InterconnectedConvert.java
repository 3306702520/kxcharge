package com.hanxing.kxcharge.module.operations.convert.interflow;

import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationEquipChargeStatusReqDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderInfoRespDto;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.pile.mq.RealTimeMonitor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 互联互通 Convert
 *
 * @author kaixin
 */
@Mapper
public interface InterconnectedConvert {

    InterconnectedConvert INSTANCE = Mappers.getMapper(InterconnectedConvert.class);


    NotificationEquipChargeStatusReqDto convert01(RealTimeMonitor realTimeMonitor);

    OrderInfoRespDto convert02(OrderDO orderDO);

    List<OrderInfoRespDto> convert03(List<OrderDO> orderDO);
}
