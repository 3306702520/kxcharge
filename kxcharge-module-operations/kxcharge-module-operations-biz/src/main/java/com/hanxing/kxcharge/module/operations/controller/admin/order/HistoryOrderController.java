package com.hanxing.kxcharge.module.operations.controller.admin.order;

import com.hanxing.kxcharge.framework.common.enums.OrderStatusEnum;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.*;
import com.hanxing.kxcharge.module.operations.convert.order.OrderConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.operations.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 历史订单管理")
@RestController
@RequestMapping("/operations/historyOrder")
@Validated
public class HistoryOrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "创建订单管理")
    @PreAuthorize("@ss.hasPermission('operations:order:create')")
    public CommonResult<Long> createOrder(@Valid @RequestBody OrderCreateReqVO createReqVO) {
        return success(orderService.createOrder(createReqVO));
    }


    @PutMapping("/update")
    @Operation(summary = "更新订单管理")
    @PreAuthorize("@ss.hasPermission('operations:order:update')")
    public CommonResult<Boolean> updateOrder(@Valid @RequestBody OrderUpdateReqVO updateReqVO) {
        orderService.updateOrder(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除订单管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:order:delete')")
    public CommonResult<Boolean> deleteOrder(@RequestParam("id") Long id) {
        orderService.deleteOrder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得订单管理")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<CurrentOrderRespVO> getOrder(@RequestParam("seqNumber") String seqNumber) {
        return CommonResult.success(orderService.getOrderBySeqNumber(seqNumber));
    }

    @GetMapping("/listAll")
    @Operation(summary = "获得历史订单管理列表（所有）")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<List<OrderRespVO>> getHistoryOrderList(@Valid HistoryOrderReqVO pageVO) {
        List<OrderDO> list = orderService.getHistoryOrderList(pageVO);
        return success(OrderConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得历史订单管理分页")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<PageResult<OrderRespVO>> getHistoryOrderPage(@Valid HistoryOrderPageReqVO pageVO) {
        pageVO.setOrderStatus(OrderStatusEnum.HAVE_FINISHED.getStatus());
        PageResult<OrderDO> pageResult = orderService.getOrderPage(pageVO);
        return success(OrderConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出订单管理 Excel")
    @PreAuthorize("@ss.hasPermission('operations:order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrderExcel(@Valid OrderExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<OrderDO> list = orderService.getOrderList(exportReqVO);
        // 导出 Excel
        List<OrderExcelVO> datas = OrderConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "订单管理.xls", "数据", OrderExcelVO.class, datas);
    }

}
