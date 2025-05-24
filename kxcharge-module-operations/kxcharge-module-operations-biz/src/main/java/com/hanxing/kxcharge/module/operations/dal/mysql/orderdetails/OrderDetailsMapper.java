package com.hanxing.kxcharge.module.operations.dal.mysql.orderdetails;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TimeDistributionDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetailsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单详情管理 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface OrderDetailsMapper extends BaseMapperX<OrderDetailsDO> {

    default PageResult<OrderDetailsDO> selectPage(OrderDetailsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrderDetailsDO>()
                .eqIfPresent(OrderDetailsDO::getChargedAllPower, reqVO.getChargedAllPower())
                .eqIfPresent(OrderDetailsDO::getChargedDiscountPrice, reqVO.getChargedDiscountPrice())
                .eqIfPresent(OrderDetailsDO::getChargedPower, reqVO.getChargedPower())
                .eqIfPresent(OrderDetailsDO::getChargedPrice, reqVO.getChargedPrice())
                .betweenIfPresent(OrderDetailsDO::getChargedTime, reqVO.getChargedTime())
                .betweenIfPresent(OrderDetailsDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(OrderDetailsDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(OrderDetailsDO::getOrderAmount, reqVO.getOrderAmount())
                .eqIfPresent(OrderDetailsDO::getPriceType, reqVO.getPriceType())
                .eqIfPresent(OrderDetailsDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(OrderDetailsDO::getServiceDiscountPrice, reqVO.getServiceDiscountPrice())
                .eqIfPresent(OrderDetailsDO::getServicePrice, reqVO.getServicePrice())
                .orderByDesc(OrderDetailsDO::getId));
    }

    default List<OrderDetailsDO> selectList(OrderDetailsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrderDetailsDO>()
                .eqIfPresent(OrderDetailsDO::getChargedAllPower, reqVO.getChargedAllPower())
                .eqIfPresent(OrderDetailsDO::getChargedDiscountPrice, reqVO.getChargedDiscountPrice())
                .eqIfPresent(OrderDetailsDO::getChargedPower, reqVO.getChargedPower())
                .eqIfPresent(OrderDetailsDO::getChargedPrice, reqVO.getChargedPrice())
                .betweenIfPresent(OrderDetailsDO::getChargedTime, reqVO.getChargedTime())
                .betweenIfPresent(OrderDetailsDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(OrderDetailsDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(OrderDetailsDO::getOrderAmount, reqVO.getOrderAmount())
                .eqIfPresent(OrderDetailsDO::getPriceType, reqVO.getPriceType())
                .eqIfPresent(OrderDetailsDO::getOrderId, reqVO.getOrderId())
                .eqIfPresent(OrderDetailsDO::getServiceDiscountPrice, reqVO.getServiceDiscountPrice())
                .eqIfPresent(OrderDetailsDO::getServicePrice, reqVO.getServicePrice())
                .orderByDesc(OrderDetailsDO::getId));
    }

    List<TimeDistributionDataRespVo> selectListByCustom(Integer type);
}
