

package com.hanxing.kxcharge.module.operations.controller.app.order;

import com.hanxing.kxcharge.framework.common.enums.StopChargeMessageEnum;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.security.core.annotations.PreAuthenticated;
import com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.CurrentOrderRespVO;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.HistoryOrderPageReqVO;
import com.hanxing.kxcharge.module.operations.controller.app.order.vo.AppOrderRespVO;
import com.hanxing.kxcharge.module.operations.convert.order.OrderConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "app - 订单管理")
@RestController
@RequestMapping("/operations/app/order")
@Validated
public class AppOrderController {


    @Resource
    private OrderService orderService;

    @GetMapping("/startCharge")
    @Operation(summary = "开始充电")
    @PreAuthenticated
    public CommonResult<String> startCharge(@NotNull String connectorCode, @RequestParam(value = "plateNumber", required = false) String plateNumber) {
        return CommonResult.success(orderService.startCharge(connectorCode, plateNumber));
    }


    @GetMapping("/endCharge")
    @Operation(summary = "结束充电")
    @PreAuthenticated
    public CommonResult<Boolean> endCharge(@NotNull String seqNumber) {
        return CommonResult.success(orderService.endCharge(seqNumber, StopChargeMessageEnum.INITIATIVE));
    }

    /**
     * 订单详情
     */
    @GetMapping("/{seqNumber}")
    @Operation(summary = "app获得订单详情")
    @PreAuthenticated
    public CommonResult<CurrentOrderRespVO> chargeOrderInfo(@NotNull(message = "主键不能为空") @PathVariable String seqNumber) {
        return CommonResult.success(orderService.getOrderBySeqNumber(seqNumber));
    }

    /**
     * 订单列表
     *
     * @return
     */
    @GetMapping("/page")
    @Operation(summary = "app获得历史订单管理分页")
    @PreAuthenticated
    public CommonResult<PageResult<AppOrderRespVO>> getHistoryOrderPage(@Valid HistoryOrderPageReqVO pageVO) {
        pageVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        PageResult<OrderDO> pageResult = orderService.getOrderPage(pageVO);
        return success(OrderConvert.INSTANCE.convertPage01(pageResult));
    }

}
