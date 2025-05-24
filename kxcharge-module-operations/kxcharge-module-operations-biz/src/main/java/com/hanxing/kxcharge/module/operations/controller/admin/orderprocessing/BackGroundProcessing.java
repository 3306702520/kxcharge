package com.hanxing.kxcharge.module.operations.controller.admin.orderprocessing;

import cn.hutool.core.bean.BeanUtil;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionConfirmationRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionRecordsReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.orderprocessing.vo.TransactionRecordVO;
import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

/**
 * 这个类的接口是用于后端做线上订单(或者其它)处理使用
 */
@Tag(name = "管理后台 - 后台处理")
@RestController
@RequestMapping("/operations/backGroundProcess")
public class BackGroundProcessing {




    @Resource
    private OrderService orderService;




    @PostMapping("/order")
    @Operation(summary = "处理订单")
    public CommonResult<TransactionConfirmationRespDto> getOrderList(@RequestBody TransactionRecordVO vo) {
        TransactionRecordsReqDto transactionRecordsReqDto = BeanUtil.copyProperties(vo, TransactionRecordsReqDto.class);
        transactionRecordsReqDto.setStartTime(new Date(vo.getStartTime()));
        transactionRecordsReqDto.setEndTime(new Date(vo.getEndTime()));
        transactionRecordsReqDto.setTransactionDate(new Date(vo.getTransactionDate()));
        TransactionConfirmationRespDto transactionConfirmationRespDto = orderService.transactionRecord(transactionRecordsReqDto);
        return success(transactionConfirmationRespDto);
    }


    @PostMapping("/orderRethrust")
    @Operation(summary = "互联互通订单结算信息重发")
    public CommonResult<Boolean> orderRethrust(@RequestParam("id") Long id) {
        orderService.orderRethrust(id);
        return success(true);
    }


}
