package com.hanxing.kxcharge.module.operations.service.orderdetails;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.framework.tenant.core.context.TenantContextHolder;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsPageReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsUpdateReqVO;
import com.hanxing.kxcharge.module.operations.convert.orderdetails.OrderDetailsConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetailsDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.orderdetails.OrderDetailsMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.ORDER_DETAILS_NOT_EXISTS;

/**
 * 订单详情管理 Service 实现类
 *
 * @author kaixin
 */
@Service
@Validated
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Resource
    private OrderDetailsMapper orderDetailsMapper;

    @Override
    public Long createOrderDetails(OrderDetailsCreateReqVO createReqVO) {
        // 插入
        OrderDetailsDO orderDetails = OrderDetailsConvert.INSTANCE.convert(createReqVO);
        orderDetails.setTenantId(TenantContextHolder.getTenantId());
        orderDetailsMapper.insert(orderDetails);
        // 返回
        return orderDetails.getId();
    }

    @Override
    public void updateOrderDetails(OrderDetailsUpdateReqVO updateReqVO) {
        // 校验存在
        validateOrderDetailsExists(updateReqVO.getId());
        // 更新
        OrderDetailsDO updateObj = OrderDetailsConvert.INSTANCE.convert(updateReqVO);
        orderDetailsMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrderDetails(Long id) {
        // 校验存在
        validateOrderDetailsExists(id);
        // 删除
        orderDetailsMapper.deleteById(id);
    }

    private void validateOrderDetailsExists(Long id) {
        if (orderDetailsMapper.selectById(id) == null) {
            throw exception(ORDER_DETAILS_NOT_EXISTS);
        }
    }

    @Override
    public OrderDetailsDO getOrderDetails(Long id) {
        return orderDetailsMapper.selectById(id);
    }

    @Override
    public List<OrderDetailsDO> getOrderDetailsList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return orderDetailsMapper.selectBatchIds(ids);
    }

    @Override
    public List<OrderDetailsDO> getOrderDetailsList(Long pid) {
        return orderDetailsMapper.selectList(new LambdaQueryWrapperX<OrderDetailsDO>().eq(OrderDetailsDO::getOrderId, pid));
    }

    @Override
    public PageResult<OrderDetailsDO> getOrderDetailsPage(OrderDetailsPageReqVO pageReqVO) {
        return orderDetailsMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrderDetailsDO> getOrderDetailsList(OrderDetailsExportReqVO exportReqVO) {
        return orderDetailsMapper.selectList(exportReqVO);
    }

    @Override
    public List<OrderDetailsDO> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailsMapper.selectList(new QueryWrapper<OrderDetailsDO>().lambda()
                .eq(OrderDetailsDO::getOrderId, orderId));
    }

}
