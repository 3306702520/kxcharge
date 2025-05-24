package com.hanxing.kxcharge.module.operations.controller.admin.orderdetails;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.*;
import com.hanxing.kxcharge.module.operations.convert.orderdetails.OrderDetailsConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetailsDO;
import com.hanxing.kxcharge.module.operations.service.orderdetails.OrderDetailsService;
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
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 订单详情管理")
@RestController
@RequestMapping("/operations/order-details")
@Validated
public class OrderDetailsController {

    @Resource
    private OrderDetailsService orderDetailsService;

    @PostMapping("/create")
    @Operation(summary = "创建订单详情管理")
    @PreAuthorize("@ss.hasPermission('operations:order-details:create')")
    public CommonResult<Long> createOrderDetails(@Valid @RequestBody OrderDetailsCreateReqVO createReqVO) {
        return success(orderDetailsService.createOrderDetails(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新订单详情管理")
    @PreAuthorize("@ss.hasPermission('operations:order-details:update')")
    public CommonResult<Boolean> updateOrderDetails(@Valid @RequestBody OrderDetailsUpdateReqVO updateReqVO) {
        orderDetailsService.updateOrderDetails(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除订单详情管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:order-details:delete')")
    public CommonResult<Boolean> deleteOrderDetails(@RequestParam("id") Long id) {
        orderDetailsService.deleteOrderDetails(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得订单详情管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:order-details:query')")
    public CommonResult<OrderDetailsRespVO> getOrderDetails(@RequestParam("id") Long id) {
        OrderDetailsDO orderDetails = orderDetailsService.getOrderDetails(id);
        return success(OrderDetailsConvert.INSTANCE.convert(orderDetails));
    }

    @GetMapping("/list")
    @Operation(summary = "获得订单详情管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:order-details:query')")
    public CommonResult<List<OrderDetailsRespVO>> getOrderDetailsList(@RequestParam("ids") Collection<Long> ids) {
        List<OrderDetailsDO> list = orderDetailsService.getOrderDetailsList(ids);
        return success(OrderDetailsConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得订单详情管理分页")
    @PreAuthorize("@ss.hasPermission('operations:order-details:query')")
    public CommonResult<PageResult<OrderDetailsRespVO>> getOrderDetailsPage(@Valid OrderDetailsPageReqVO pageVO) {
        PageResult<OrderDetailsDO> pageResult = orderDetailsService.getOrderDetailsPage(pageVO);
        return success(OrderDetailsConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出订单详情管理 Excel")
    @PreAuthorize("@ss.hasPermission('operations:order-details:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOrderDetailsExcel(@Valid OrderDetailsExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<OrderDetailsDO> list = orderDetailsService.getOrderDetailsList(exportReqVO);
        // 导出 Excel
        List<OrderDetailsExcelVO> datas = OrderDetailsConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "订单详情管理.xls", "数据", OrderDetailsExcelVO.class, datas);
    }

}
