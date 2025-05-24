package com.hanxing.kxcharge.module.operations.service.order;

import com.hanxing.kxcharge.framework.common.enums.StopChargeMessageEnum;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionConfirmationRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionRecordsReqDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderChargeReqDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderInfoRespDto;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.pile.mq.RealTimeMonitor;
import com.hanxing.kxcharge.module.pile.mq.RemoteShutdownCommandReply;
import com.hanxing.kxcharge.module.pile.mq.StartChargingCommandReply;
import com.hanxing.kxcharge.module.pile.mq.StopCharging;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * 订单管理 Service 接口
 *
 * @author 小黑
 */
public interface OrderService {

    /**
     * 创建订单管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrder(@Valid OrderCreateReqVO createReqVO);

    /**
     * 更新订单管理
     *
     * @param updateReqVO 更新信息
     */
    void updateOrder(@Valid OrderUpdateReqVO updateReqVO);

    /**
     * 删除订单管理
     *
     * @param id 编号
     */
    void deleteOrder(Long id);

    /**
     * 获得订单管理
     *
     * @param id 编号
     * @return 订单管理
     */
    OrderDO getOrder(Long id);

    /**
     * 获得订单管理列表
     *
     * @param ids 编号
     * @return 订单管理列表
     */
    List<OrderDO> getOrderList(Collection<Long> ids);

    /**
     * 获得订单管理分页
     *
     * @param pageReqVO 分页查询
     * @return 订单管理分页
     */
    PageResult<OrderDO> getOrderPage(HistoryOrderPageReqVO pageReqVO);

    /**
     * 获得订单管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 订单管理列表
     */
    List<OrderDO> getOrderList(OrderExportReqVO exportReqVO);

    /**
     * 获得实时订单
     *
     * @param exportReqVO 查询条件
     * @return 订单管理列表
     */
    List<CurrentOrderRespVO> getCurrentOrderList(CurrentOrderReqVO pageVO);

    /**
     * 开始充电
     * @param connectorCode 电桩id
     * @param plateNumber 车牌号
     * @return 是否成功
     */
    String startCharge(String connectorCode, String plateNumber);

    /**
     * 结束充电
     * @param id 订单id
     * @return 是否成功
     */
    Boolean endCharge(String seqNumber, StopChargeMessageEnum reason);

    /**
     * 获取历史订单
     * @param pageVO
     * @return
     */
    List<OrderDO> getHistoryOrderList(HistoryOrderReqVO pageVO);

    /**
     * 获取待处理订单页面
     * @param pageVO
     * @return
     */
    PageResult<OrderDO> getPendingOrderPage(PendingOrderPageReqVO pageVO);

    /**
     * 根据订单号获取实时订单
     * @param seqNumber 订单号
     * @return 实时订单
     */
    CurrentOrderRespVO getCurrentOrderBySeqNumber(String seqNumber);

    /**
     * 根据订单号获取订单
     * @param seqNumber 订单号
     * @return
     */
    CurrentOrderRespVO getOrderBySeqNumber(String seqNumber);

    /**
     * 中电联订单查询订单信息
     * @param tradeSeq
     * @return
     */
    OrderDO getOrderByTradeSeq(String tradeSeq);

    /**
     * 查询中电联订单集合
     * @param tradeSeqList
     * @return
     */
    List<OrderDO> getOrderByTradeSeqList(Collection<String> tradeSeqList);
    /**
     * 收到交易记录
     * @param dto 交易记录
     * @return 确认账单实体
     */
    TransactionConfirmationRespDto transactionRecord(TransactionRecordsReqDto dto);

    /**
     * 开始充电回执
     * @param startChargingCommandReply
     */
    void startReplyCharge(StartChargingCommandReply startChargingCommandReply);

    /**
     * 结束充电回执
     * @param remoteShutdownCommandReply
     */
    void endReplyCharge(RemoteShutdownCommandReply remoteShutdownCommandReply);

    /**
     * 结束充电
     * @param stopCharging
     */
    void stopCharging(StopCharging stopCharging);
    /**
     * 开始充电-中电联
     * @param dto
     * @return
     */
    OrderDO startChargeByTrade(OrderChargeReqDto dto);

    /**
     * 停止充电-中电联
     * @param dto
     * @return
     */
    OrderDO endChargeByTrade(OrderChargeReqDto dto);

    /**
     * 检查余额(充电中)并更新状态
     * @param realTimeMonitor
     */
    OrderDO checkBalance(RealTimeMonitor realTimeMonitor);

    List<OrderInfoRespDto> getOrderInfo(Long userId);

    /**
     * 基于实时数据处理失败的充电请求
     * @param realTimeMonitor
     */
    void errorStart(RealTimeMonitor realTimeMonitor);


    /**
     * 检查异常订单
     * @param realTimeMonitor
     */
    void monitoringCharge(RealTimeMonitor realTimeMonitor);


    /**
     * 获取本月的订单
     * @param month 月份
     * @param stationId 场站id
     */
    List<OrderDO> getMonthOrder(LocalDate month, Long stationId);

    /**
     * 互联互通订单号重推
     * @param id
     */
    void orderRethrust(Long id);
}
