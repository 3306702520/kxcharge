package com.hanxing.kxcharge.module.operations.controller.admin.order;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.*;
import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 实时订单管理")
@RestController
@RequestMapping("/operations/currentOrder")
@Validated
public class CurrentOrderController {

    @Resource
    private OrderService orderService;




    @GetMapping("/list")
    @Operation(summary = "获得实时订单管理列表（不分页）")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<List<CurrentOrderRespVO>> getOrderList(@Valid CurrentOrderReqVO pageVO) {
        List<CurrentOrderRespVO> list = orderService.getCurrentOrderList(pageVO);
        return success(list);
    }


    @GetMapping("/selectById")
    @Operation(summary = "获得实时订单")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<CurrentOrderRespVO> getOrder(@RequestParam String seqNumber) {
        CurrentOrderRespVO currentOrderBySeqNumber = orderService.getCurrentOrderBySeqNumber(seqNumber);
        return success(currentOrderBySeqNumber);
    }



}
