package com.hanxing.kxcharge.module.operations.service.orderdetails;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsPageReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsUpdateReqVO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetailsDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 订单详情管理 Service 接口
 *
 * @author kaixin
 */
public interface OrderDetailsService {

    /**
     * 创建订单详情管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrderDetails(@Valid OrderDetailsCreateReqVO createReqVO);

    /**
     * 更新订单详情管理
     *
     * @param updateReqVO 更新信息
     */
    void updateOrderDetails(@Valid OrderDetailsUpdateReqVO updateReqVO);

    /**
     * 删除订单详情管理
     *
     * @param id 编号
     */
    void deleteOrderDetails(Long id);

    /**
     * 获得订单详情管理
     *
     * @param id 编号
     * @return 订单详情管理
     */
    OrderDetailsDO getOrderDetails(Long id);

    /**
     * 获得订单详情管理列表
     *
     * @param ids 编号
     * @return 订单详情管理列表
     */
    List<OrderDetailsDO> getOrderDetailsList(Collection<Long> ids);

    /**
     * 获得订单详情管理列表
     *
     * @param ids 编号
     * @return 订单详情管理列表
     */
    List<OrderDetailsDO> getOrderDetailsList(Long pid);

    /**
     * 获得订单详情管理分页
     *
     * @param pageReqVO 分页查询
     * @return 订单详情管理分页
     */
    PageResult<OrderDetailsDO> getOrderDetailsPage(OrderDetailsPageReqVO pageReqVO);

    /**
     * 获得订单详情管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 订单详情管理列表
     */
    List<OrderDetailsDO> getOrderDetailsList(OrderDetailsExportReqVO exportReqVO);

    /**
     * 根据订单id查询订单详情列表
     * @param orderId 订单id
     * @return
     */
    List<OrderDetailsDO> getOrderDetailsByOrderId(Long orderId);
}
