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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 待处理订单管理")
@RestController
@RequestMapping("/operations/pendingOrder")
@Validated
public class PendingOrderController {

    @Resource
    private OrderService orderService;


    @GetMapping("/page")
    @Operation(summary = "获得待处理订单管理分页")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<PageResult<OrderRespVO>> getPendingOrderPage(@Valid PendingOrderPageReqVO pageVO) {
        pageVO.setOrderStatus(OrderStatusEnum.IN_PROGRESS.getStatus());
        PageResult<OrderDO> pageResult = orderService.getPendingOrderPage(pageVO);
        return success(OrderConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/error/page")
    @Operation(summary = "获得异常订单管理分页")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<PageResult<OrderRespVO>> getErrorOrderPage(@Valid PendingOrderPageReqVO pageVO) {
        pageVO.setOrderStatus(OrderStatusEnum.ERROR.getStatus());
        PageResult<OrderDO> pageResult = orderService.getPendingOrderPage(pageVO);
        return success(OrderConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/get")
    @Operation(summary = "获得待处理订单管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:order:query')")
    public CommonResult<OrderRespVO> getOrder(@RequestParam("id") Long id) {
        OrderDO order = orderService.getOrder(id);
        return success(OrderConvert.INSTANCE.convert(order));
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
