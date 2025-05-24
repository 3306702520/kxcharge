package com.hanxing.kxcharge.module.operations.dal.mysql.order;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnore;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.ChargeDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TerminalOperationDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单管理 Mapper
 *
 * @author 小黑
 */
@Mapper
public interface OrderMapper extends BaseMapperX<OrderDO> {

    default PageResult<OrderDO> selectPage(HistoryOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getStationId, reqVO.getStationId())
                .likeIfPresent(OrderDO::getMobile, reqVO.getMobile())
                .likeIfPresent(OrderDO::getStationName, reqVO.getStationName())
                .eqIfPresent(OrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(OrderDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(OrderDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(OrderDO::getCompanyConsume, reqVO.getCompanyConsume())
                .eqIfPresent(OrderDO::getPersonalConsume, reqVO.getPersonalConsume())
                .eqIfPresent(OrderDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(OrderDO::getChargedAmount, reqVO.getChargedAmount())
                .betweenIfPresent(OrderDO::getChargedTime, reqVO.getChargedTime())
                .eqIfPresent(OrderDO::getCardNo, reqVO.getCardNo())
                .eqIfPresent(OrderDO::getPayer, reqVO.getPayer())
                .eqIfPresent(OrderDO::getChargedPower, reqVO.getChargedPower())
                .likeIfPresent(OrderDO::getNickName, reqVO.getNickName())
                .eqIfPresent(OrderDO::getUserAccount, reqVO.getUserAccount())
                .eqIfPresent(OrderDO::getParkedDiscountAmount, reqVO.getParkedDiscountAmount())
                .eqIfPresent(OrderDO::getInPay, reqVO.getInPay())
                .eqIfPresent(OrderDO::getStartSoc, reqVO.getStartSoc())
                .eqIfPresent(OrderDO::getEndSoc, reqVO.getEndSoc())
                .eqIfPresent(OrderDO::getCarType, reqVO.getCarType())
                .betweenIfPresent(OrderDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(OrderDO::getStationOrgType, reqVO.getStationOrgType())
                .eqIfPresent(OrderDO::getPlateNumber, reqVO.getPlateNumber())
                .eqIfPresent(OrderDO::getChargingSource, reqVO.getChargingSource())
                .eqIfPresent(OrderDO::getPileType, reqVO.getPileType())
                .eqIfPresent(OrderDO::getSeqNumber, reqVO.getSeqNumber())
                .eqIfPresent(OrderDO::getUserId, reqVO.getUserId())
                .betweenIfPresent(OrderDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(OrderDO::getId));
    }

    default PageResult<OrderDO> selectPage(PendingOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getStationId, reqVO.getStationId())
                .likeIfPresent(OrderDO::getMobile, reqVO.getMobile())
                .likeIfPresent(OrderDO::getStationName, reqVO.getStationName())
                .eqIfPresent(OrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(OrderDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(OrderDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(OrderDO::getCompanyConsume, reqVO.getCompanyConsume())
                .eqIfPresent(OrderDO::getPersonalConsume, reqVO.getPersonalConsume())
                .eqIfPresent(OrderDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(OrderDO::getChargedAmount, reqVO.getChargedAmount())
                .betweenIfPresent(OrderDO::getChargedTime, reqVO.getChargedTime())
                .eqIfPresent(OrderDO::getCardNo, reqVO.getCardNo())
                .eqIfPresent(OrderDO::getPayer, reqVO.getPayer())
                .eqIfPresent(OrderDO::getChargedPower, reqVO.getChargedPower())
                .likeIfPresent(OrderDO::getNickName, reqVO.getNickName())
                .eqIfPresent(OrderDO::getUserAccount, reqVO.getUserAccount())
                .eqIfPresent(OrderDO::getParkedDiscountAmount, reqVO.getParkedDiscountAmount())
                .eqIfPresent(OrderDO::getInPay, reqVO.getInPay())
                .eqIfPresent(OrderDO::getStartSoc, reqVO.getStartSoc())
                .eqIfPresent(OrderDO::getEndSoc, reqVO.getEndSoc())
                .eqIfPresent(OrderDO::getCarType, reqVO.getCarType())
                .betweenIfPresent(OrderDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(OrderDO::getStationOrgType, reqVO.getStationOrgType())
                .eqIfPresent(OrderDO::getPlateNumber, reqVO.getPlateNumber())
                .eqIfPresent(OrderDO::getChargingSource, reqVO.getChargingSource())
                .eqIfPresent(OrderDO::getPileType, reqVO.getPileType())
                .eqIfPresent(OrderDO::getSeqNumber, reqVO.getSeqNumber())
                .betweenIfPresent(OrderDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(OrderDO::getId));
    }

    default List<OrderDO> selectList(OrderExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getStationId, reqVO.getStationId())
                .likeIfPresent(OrderDO::getStationName, reqVO.getStationName())
                .eqIfPresent(OrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(OrderDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(OrderDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(OrderDO::getCompanyConsume, reqVO.getCompanyConsume())
                .eqIfPresent(OrderDO::getPersonalConsume, reqVO.getPersonalConsume())
                .eqIfPresent(OrderDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(OrderDO::getChargedAmount, reqVO.getChargedAmount())
                .betweenIfPresent(OrderDO::getChargedTime, reqVO.getChargedTime())
                .eqIfPresent(OrderDO::getCardNo, reqVO.getCardNo())
                .eqIfPresent(OrderDO::getPayer, reqVO.getPayer())
                .eqIfPresent(OrderDO::getChargedPower, reqVO.getChargedPower())
                .likeIfPresent(OrderDO::getNickName, reqVO.getNickName())
                .eqIfPresent(OrderDO::getUserAccount, reqVO.getUserAccount())
                .eqIfPresent(OrderDO::getParkedDiscountAmount, reqVO.getParkedDiscountAmount())
                .eqIfPresent(OrderDO::getInPay, reqVO.getInPay())
                .eqIfPresent(OrderDO::getStartSoc, reqVO.getStartSoc())
                .eqIfPresent(OrderDO::getEndSoc, reqVO.getEndSoc())
                .eqIfPresent(OrderDO::getCarType, reqVO.getCarType())
                .betweenIfPresent(OrderDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(OrderDO::getStationOrgType, reqVO.getStationOrgType())
                .eqIfPresent(OrderDO::getPlateNumber, reqVO.getPlateNumber())
                .eqIfPresent(OrderDO::getChargingSource, reqVO.getChargingSource())
                .eqIfPresent(OrderDO::getPileType, reqVO.getPileType())
                .eqIfPresent(OrderDO::getSeqNumber, reqVO.getSeqNumber())
                .betweenIfPresent(OrderDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(OrderDO::getId));
    }


    default List<OrderDO> selectList(CurrentOrderReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getStationId, reqVO.getStationId())
                .likeIfPresent(OrderDO::getStationName, reqVO.getStationName())
                .likeIfPresent(OrderDO::getMobile, reqVO.getMobile())
                .eqIfPresent(OrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(OrderDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(OrderDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(OrderDO::getCompanyConsume, reqVO.getCompanyConsume())
                .eqIfPresent(OrderDO::getPersonalConsume, reqVO.getPersonalConsume())
                .eqIfPresent(OrderDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(OrderDO::getChargedAmount, reqVO.getChargedAmount())
                .betweenIfPresent(OrderDO::getChargedTime, reqVO.getChargedTime())
                .eqIfPresent(OrderDO::getCardNo, reqVO.getCardNo())
                .eqIfPresent(OrderDO::getPayer, reqVO.getPayer())
                .eqIfPresent(OrderDO::getChargedPower, reqVO.getChargedPower())
                .likeIfPresent(OrderDO::getNickName, reqVO.getNickName())
                .eqIfPresent(OrderDO::getUserAccount, reqVO.getUserAccount())
                .eqIfPresent(OrderDO::getParkedDiscountAmount, reqVO.getParkedDiscountAmount())
                .eqIfPresent(OrderDO::getInPay, reqVO.getInPay())
                .eqIfPresent(OrderDO::getStartSoc, reqVO.getStartSoc())
                .eqIfPresent(OrderDO::getEndSoc, reqVO.getEndSoc())
                .eqIfPresent(OrderDO::getCarType, reqVO.getCarType())
                .betweenIfPresent(OrderDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(OrderDO::getStationOrgType, reqVO.getStationOrgType())
                .eqIfPresent(OrderDO::getPlateNumber, reqVO.getPlateNumber())
                .eqIfPresent(OrderDO::getChargingSource, reqVO.getChargingSource())
                .eqIfPresent(OrderDO::getPileType, reqVO.getPileType())
                .eqIfPresent(OrderDO::getSeqNumber, reqVO.getSeqNumber())
                .betweenIfPresent(OrderDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(OrderDO::getId));
    }

    default List<OrderDO> selectList(HistoryOrderReqVO reqVO){
        return selectList(new LambdaQueryWrapperX<OrderDO>()
                .eqIfPresent(OrderDO::getStationId, reqVO.getStationId())
                .likeIfPresent(OrderDO::getStationName, reqVO.getStationName())
                .eqIfPresent(OrderDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDO::getOrderType, reqVO.getOrderType())
                .eqIfPresent(OrderDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(OrderDO::getCompanyName, reqVO.getCompanyName())
                .eqIfPresent(OrderDO::getCompanyConsume, reqVO.getCompanyConsume())
                .eqIfPresent(OrderDO::getPersonalConsume, reqVO.getPersonalConsume())
                .eqIfPresent(OrderDO::getCompanyId, reqVO.getCompanyId())
                .eqIfPresent(OrderDO::getChargedAmount, reqVO.getChargedAmount())
                .betweenIfPresent(OrderDO::getChargedTime, reqVO.getChargedTime())
                .eqIfPresent(OrderDO::getCardNo, reqVO.getCardNo())
                .eqIfPresent(OrderDO::getPayer, reqVO.getPayer())
                .eqIfPresent(OrderDO::getChargedPower, reqVO.getChargedPower())
                .likeIfPresent(OrderDO::getNickName, reqVO.getNickName())
                .eqIfPresent(OrderDO::getUserAccount, reqVO.getUserAccount())
                .eqIfPresent(OrderDO::getParkedDiscountAmount, reqVO.getParkedDiscountAmount())
                .eqIfPresent(OrderDO::getInPay, reqVO.getInPay())
                .eqIfPresent(OrderDO::getStartSoc, reqVO.getStartSoc())
                .eqIfPresent(OrderDO::getEndSoc, reqVO.getEndSoc())
                .eqIfPresent(OrderDO::getCarType, reqVO.getCarType())
                .betweenIfPresent(OrderDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(OrderDO::getStationOrgType, reqVO.getStationOrgType())
                .eqIfPresent(OrderDO::getPlateNumber, reqVO.getPlateNumber())
                .eqIfPresent(OrderDO::getChargingSource, reqVO.getChargingSource())
                .eqIfPresent(OrderDO::getPileType, reqVO.getPileType())
                .eqIfPresent(OrderDO::getSeqNumber, reqVO.getSeqNumber())
                .betweenIfPresent(OrderDO::getEndTime, reqVO.getEndTime())
                .orderByDesc(OrderDO::getId));
    }

    ChargeDataRespVo chargeData();

    List<TerminalOperationDataRespVo> selectListByToday(Integer type);

    List<TerminalOperationDataRespVo> selectListByMonth(Integer type);

    List<TerminalOperationDataRespVo> selectListByYear(Integer type);

    @TenantIgnore
    int updateChargeState(@Param("gunCode") String gunCode,
                          @Param("seqNumber") String transactionSerialNo,
                          @Param("oldState") Integer oldState,
                          @Param("newState") Integer newState);
    @TenantIgnore
    void updateBySoc(@Param("soc") String soc, @Param("seqNumber") String transactionSerialNo);
}
