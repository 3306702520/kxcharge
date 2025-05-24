package com.hanxing.kxcharge.module.operations.api.transaction;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.TransactionApi;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionConfirmationRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionRecordsReqDto;
import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Slf4j
public class TransactionApiImpl implements TransactionApi {

    @Resource
    OrderService orderService;


    /**
     * 交易记录
     * @param dto
     * @return
     */
    @Override
    public CommonResult<TransactionConfirmationRespDto> record(TransactionRecordsReqDto dto) {
        return CommonResult.success(orderService.transactionRecord(dto));
    }

}
