package com.hanxing.kxcharge.module.operations.api.order;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderChargeReqDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderChargeRespDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderInfoRespDto;
import com.hanxing.kxcharge.module.operations.convert.interflow.InterconnectedConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Slf4j
public class OrderApiImpl implements OrderApi {

    @Autowired
    OrderService orderService;


    @Override
    public CommonResult<OrderChargeRespDto> startCharging(OrderChargeReqDto dto) {
        OrderDO orderDO = orderService.startChargeByTrade(dto);
        OrderChargeRespDto respDto = new OrderChargeRespDto();
        respDto.setGunId(orderDO.getGunId());
        respDto.setTradeSeq(orderDO.getTradeSeq());
        respDto.setTradeStatus(orderDO.getTradeStatus());
        if (orderDO.getStopReason() == null) {
            respDto.setFailReason(0);
        } else {
            respDto.setFailReason(orderDO.getStopReason());
        }
        return CommonResult.success(respDto);
    }

    @Override
    public CommonResult<OrderChargeRespDto> endCharging(OrderChargeReqDto dto) {
        OrderDO orderDO = orderService.endChargeByTrade(dto);
        OrderChargeRespDto respDto = new OrderChargeRespDto();
        respDto.setGunId(orderDO.getGunId());
        respDto.setTradeSeq(orderDO.getTradeSeq());
        respDto.setTradeStatus(orderDO.getTradeStatus());
        if (orderDO.getStopReason() == null) {
            respDto.setFailReason(0);
        } else {
            respDto.setFailReason(orderDO.getStopReason());
        }
        return CommonResult.success(respDto);
    }

    @Override
    public CommonResult<OrderInfoRespDto> info(String tradeSeq) {
        OrderDO orderDO = orderService.getOrderByTradeSeq(tradeSeq);
        OrderInfoRespDto respDto = InterconnectedConvert.INSTANCE.convert02(orderDO);
        return CommonResult.success(respDto);
    }

    @Override
    public CommonResult<List<OrderInfoRespDto>> infoList(Collection<String> tradeSeqList) {
        List<OrderDO> orderDOList = orderService.getOrderByTradeSeqList(tradeSeqList);
        List<OrderInfoRespDto> respDto = InterconnectedConvert.INSTANCE.convert03(orderDOList);
        return CommonResult.success(respDto);
    }

    @Override
    public CommonResult<List<OrderInfoRespDto>> info(Long userId) {
        return CommonResult.success(orderService.getOrderInfo(userId));
    }

    @Override
    public CommonResult<List<OrderInfoRespDto>> getMonthOrder(LocalDate month, Long stationId) {

        List<OrderDO> monthOrder = orderService.getMonthOrder(month, stationId);

        return CommonResult.success(InterconnectedConvert.INSTANCE.convert03(monthOrder));
    }
}
