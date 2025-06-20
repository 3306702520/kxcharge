package com.hanxing.kxcharge.module.pay.dal.mysql.order;

import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.module.pay.dal.dataobject.order.PayOrderExtensionDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PayOrderExtensionMapper extends BaseMapperX<PayOrderExtensionDO> {

    default PayOrderExtensionDO selectByNo(String no) {
        return selectOne(PayOrderExtensionDO::getNo, no);
    }

    default int updateByIdAndStatus(Long id, Integer status, PayOrderExtensionDO update) {
        return update(update, new LambdaQueryWrapper<PayOrderExtensionDO>()
                .eq(PayOrderExtensionDO::getId, id).eq(PayOrderExtensionDO::getStatus, status));
    }

    default List<PayOrderExtensionDO> selectListByOrderId(Long orderId) {
        return selectList(PayOrderExtensionDO::getOrderId, orderId);
    }

    default List<PayOrderExtensionDO> selectListByStatusAndCreateTimeGe(Integer status, LocalDateTime minCreateTime) {
        return selectList(new LambdaQueryWrapper<PayOrderExtensionDO>()
                .eq(PayOrderExtensionDO::getStatus, status)
                .ge(PayOrderExtensionDO::getCreateTime, minCreateTime));
    }

}
