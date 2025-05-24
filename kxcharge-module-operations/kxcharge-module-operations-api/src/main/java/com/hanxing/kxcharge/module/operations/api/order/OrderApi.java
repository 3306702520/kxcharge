package com.hanxing.kxcharge.module.operations.api.order;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderChargeReqDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderChargeRespDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderInfoRespDto;
import com.hanxing.kxcharge.module.operations.enums.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC服务 - 订单模块")
public interface OrderApi {


    String PREFIX = ApiConstants.PREFIX + "/order";

    /**
     * 开始充电
     *
     * @param dto
     * @return
     */
    @PostMapping(PREFIX + "/startCharging")
    CommonResult<OrderChargeRespDto> startCharging(@RequestBody OrderChargeReqDto dto);


    /**
     * 结束充电
     *
     * @param dto
     * @return
     */
    @PostMapping(PREFIX + "/endCharging")
    CommonResult<OrderChargeRespDto> endCharging(@RequestBody OrderChargeReqDto dto);


    /**
     * 获取订单详情
     *
     * @param dto
     * @return
     */
    @GetMapping(PREFIX + "/info")
    CommonResult<OrderInfoRespDto> info(@RequestParam("tradeSeq") String tradeSeq);

    /**
     * 获取订单详情
     *
     * @param dto
     * @return
     */
    @GetMapping(PREFIX + "/infoList")
    CommonResult<List<OrderInfoRespDto>> infoList(@RequestParam("tradeSeqList") Collection<String> tradeSeqList);

    /**
     * 根据用户查询订单列表
     *
     * @param userId 用户id
     * @return
     */
    @GetMapping(PREFIX + "/infoByUser")
    CommonResult<List<OrderInfoRespDto>> info(@RequestParam("userId") Long userId);


    @GetMapping(PREFIX + "/getMonthOrder")
    CommonResult<List<OrderInfoRespDto>> getMonthOrder(@RequestParam("month") LocalDate month,
                                                       @RequestParam(value = "stationId", required = false) Long stationId);
}
