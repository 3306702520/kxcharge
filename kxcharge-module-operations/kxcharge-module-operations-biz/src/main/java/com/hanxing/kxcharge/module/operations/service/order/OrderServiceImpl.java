package com.hanxing.kxcharge.module.operations.service.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.enums.*;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.GeneratorUtil;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.framework.security.core.LoginUser;
import com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnore;
import com.hanxing.kxcharge.framework.tenant.core.context.TenantContextHolder;
import com.hanxing.kxcharge.module.member.api.plate.MemberPlateApi;
import com.hanxing.kxcharge.module.member.api.plate.dto.MemberPlateReqDTO;
import com.hanxing.kxcharge.module.member.api.user.MemberUserApi;
import com.hanxing.kxcharge.module.member.api.user.dto.MemberUserRespDTO;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionConfirmationRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionRecordsReqDto;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationChargeOrderInfoDetailsReqDto;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationChargeOrderInfoReqDto;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationStartChargeReqDto;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationStopChargeReqDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderChargeReqDto;
import com.hanxing.kxcharge.module.operations.api.order.dto.OrderInfoRespDto;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.*;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsBaseVO;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.OrderDetailsCreateReqVO;
import com.hanxing.kxcharge.module.operations.convert.interflow.InterconnectedConvert;
import com.hanxing.kxcharge.module.operations.convert.order.OrderConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.onlinequipmentconnector.OnlineEquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetail;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetailsDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderPriceType;
import com.hanxing.kxcharge.module.operations.dal.dataobject.realtimeorder.RealTimeOrderDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplatepricetype.CostTemplatePriceTypeMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipment.EquipmentMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.order.OrderMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.station.StationMapper;
import com.hanxing.kxcharge.module.operations.mq.producer.interflow.InteconnectedProducer;
import com.hanxing.kxcharge.module.operations.service.costtemplateprice.CostTemplatePriceService;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import com.hanxing.kxcharge.module.operations.service.equipmentconnector.EquipmentConnectorService;
import com.hanxing.kxcharge.module.operations.service.equipmentmodel.EquipmentModelService;
import com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector.OnlineEquipmentConnectorService;
import com.hanxing.kxcharge.module.operations.service.orderdetails.OrderDetailsService;
import com.hanxing.kxcharge.module.operations.service.realtimeorder.RealTimeOrderService;
import com.hanxing.kxcharge.module.pay.api.wallet.PayWalletApi;
import com.hanxing.kxcharge.module.pay.api.wallet.dto.ChargeOrderReqDTO;
import com.hanxing.kxcharge.module.pay.api.wallet.dto.ChargeOrderRespDTO;
import com.hanxing.kxcharge.module.pay.api.wallet.dto.PayWalletRespDTO;
import com.hanxing.kxcharge.module.pile.mq.RealTimeMonitor;
import com.hanxing.kxcharge.module.pile.mq.RemoteShutdownCommandReply;
import com.hanxing.kxcharge.module.pile.mq.StartChargingCommandReply;
import com.hanxing.kxcharge.module.pile.mq.StopCharging;
import com.hanxing.kxcharge.module.system.api.operator.OperatorApi;
import com.hanxing.kxcharge.module.system.api.operator.dto.OperatorRespDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.*;


/**
 * 订单管理 Service 实现类
 *
 * @author 小黑
 */
@Service
@Validated
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderDetailsService orderDetailsService;

    @Resource
    private EquipmentConnectorService equipmentConnectorService;

    @Resource
    private StationMapper stationMapper;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private MemberPlateApi memberPlateApi;

    @Resource
    private MemberUserApi memberUserApi;

    @Resource
    private OnlineEquipmentConnectorService onlineEquipmentConnectorService;

    @Resource
    private RealTimeOrderService realTimeOrderService;

    @Resource
    private EquipmentMapper equipmentMapper;


    @Resource
    private CostTemplatePriceService costTemplatePriceService;

    @Resource
    private CostTemplatePriceTypeMapper costTemplatePriceTypeMapper;

    @Resource
    private InteconnectedProducer inteconnectedProducer;

    @Resource
    private PayWalletApi payWalletApi;

    @Resource
    private OperatorApi operatorApi;

    @Resource
    private EquipmentModelService equipmentModelService;

    //尖
    private final static String POINTED = "pointed";
    //峰
    private final static String PEAK = "peak";
    //平
    private final static String FLAT = "flat";
    //谷
    private final static String VALLEY = "valley";

    @Override
    public Long createOrder(OrderCreateReqVO createReqVO) {
        // 插入
        OrderDO order = OrderConvert.INSTANCE.convert(createReqVO);
        orderMapper.insert(order);
        // 返回
        return order.getId();
    }

    @Override
    public void updateOrder(OrderUpdateReqVO updateReqVO) {
        // 校验存在
        validateOrderExists(updateReqVO.getId());
        // 更新
        OrderDO updateObj = OrderConvert.INSTANCE.convert(updateReqVO);
        orderMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrder(Long id) {
        // 校验存在
        validateOrderExists(id);
        // 删除
        orderMapper.deleteById(id);
    }

    private void validateOrderExists(Long id) {
        if (orderMapper.selectById(id) == null) {
            throw exception(ORDER_NOT_EXISTS);
        }
    }

    @Override
    public OrderDO getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public List<OrderDO> getOrderList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return orderMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OrderDO> getOrderPage(HistoryOrderPageReqVO pageReqVO) {
        return orderMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrderDO> getOrderList(OrderExportReqVO exportReqVO) {
        return orderMapper.selectList(exportReqVO);
    }

    @Override
    public List<CurrentOrderRespVO> getCurrentOrderList(CurrentOrderReqVO pageVO) {

        List<OrderDO> orderDOS = orderMapper.selectList(pageVO);
        // 需要判断出在充电的设备
        orderDOS = orderDOS.stream().filter(item -> {
            return ChargeStatusEnum.START_CHARGE.getStatus().equals(item.getChargeStatus());
        }).collect(Collectors.toList());
        return orderDOS.stream().map(this::getCurrentOrderRespVO).collect(Collectors.toList());
    }

    @Override
    @TenantIgnore
    public CurrentOrderRespVO getCurrentOrderBySeqNumber(String seqNumber) {
        OrderDO item = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda()
                .eq(OrderDO::getSeqNumber, seqNumber)
                .eq(OrderDO::getChargeStatus, ChargeStatusEnum.START_CHARGE.getStatus())
        );
        if (item != null) {
            return getCurrentOrderRespVO(item);
        }
        return null;
    }

    @Override
    @TenantIgnore
    public CurrentOrderRespVO getOrderBySeqNumber(String seqNumber) {
        OrderDO item = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda()
                .eq(OrderDO::getSeqNumber, seqNumber)
        );
        if (item != null) {
            return getCurrentOrderRespVO(item);
        } else {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionConfirmationRespDto transactionRecord(TransactionRecordsReqDto dto) {

        TransactionConfirmationRespDto transactionConfirmationRespDto = new TransactionConfirmationRespDto();
        transactionConfirmationRespDto.setConfirmResult("00");
        transactionConfirmationRespDto.setPhysicalCardNumber(dto.getTransactionSerialNo());
        // 防止消息重复接受
        OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda()
                .eq(OrderDO::getSeqNumber, dto.getTransactionSerialNo())
        );
        if (orderDO.getChargeStatus() >= 6) {
            return transactionConfirmationRespDto;
        }
        BigDecimal consumptionAmount = dto.getConsumptionAmount();
        // 应付金额
        BigDecimal money = consumptionAmount.setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualMoney = money;


        ChargeOrderReqDTO chargeOrderReqDTO = new ChargeOrderReqDTO();
        chargeOrderReqDTO.setOrderId(orderDO.getId());
        chargeOrderReqDTO.setUserId(orderDO.getUserId());
        chargeOrderReqDTO.setChargingCost(actualMoney);
        TenantContextHolder.setTenantId(orderDO.getTenantId());

        if (!ObjectUtils.isEmpty(orderDO.getTradeSeq())) {
            orderDO.setPayTime(LocalDateTime.now());
            orderDO.setInPay(actualMoney);
        } else {
            LoginUser loginUser = new LoginUser();
            loginUser.setId(orderDO.getUserId());
            loginUser.setUserType(UserTypeEnum.MEMBER.getValue());
            loginUser.setTenantId(orderDO.getTenantId());
            SecurityFrameworkUtils.setLoginUserHeader(loginUser);
            CommonResult<ChargeOrderRespDTO> chargeOrderRespDTOCommonResult = payWalletApi.chargeOrder(chargeOrderReqDTO);
            orderDO.setPayTime(chargeOrderRespDTOCommonResult.getData().getPayTime());
            orderDO.setInPay(new BigDecimal(chargeOrderRespDTOCommonResult.getData().getPayMoney())
                    .multiply(new BigDecimal("0.01")));

            if (StringUtils.isNotBlank(orderDO.getPlateNumber())) {
                MemberPlateReqDTO memberPlateReqDTO = new MemberPlateReqDTO();
                memberPlateReqDTO.setUserId(orderDO.getUserId());
                memberPlateReqDTO.setPlateNumber(orderDO.getPlateNumber());
                memberPlateReqDTO.setChargeTime(orderDO.getCreateTime());
                memberPlateReqDTO.setChargePlace(orderDO.getStationName());
                memberPlateApi.chargeOrder(memberPlateReqDTO);
            }
        }


        //订单已经结束
        orderDO.setChargeStatus(ChargeStatusEnum.ACCEPT_ORDER.getStatus());
        orderDO.setOrderStatus(OrderStatusEnum.HAVE_FINISHED.getStatus());
        orderDO.setStartTime(LocalDateTime.ofInstant(dto.getStartTime().toInstant(), ZoneId.systemDefault()));
        orderDO.setEndTime(LocalDateTime.ofInstant(dto.getEndTime().toInstant(), ZoneId.systemDefault()));
        orderDO.setAllAmount(money);
        orderDO.setChargedPower(dto.getTotalElectricity());
        int chargeTime = (int) ((dto.getEndTime().getTime() - dto.getStartTime().getTime()) / (60 * 1000L));
        orderDO.setChargedTime(String.valueOf(chargeTime));

        // 给订单详情新增租户
        TenantContextHolder.setTenantId(orderDO.getTenantId());
        // 增加orderDetail信息
        List<OrderDetailsCreateReqVO> orderDetailsCreateReqVOS = setDetails(dto, orderDO.getId(), orderDO.getOrgId());
        BigDecimal chargePrice = orderDetailsCreateReqVOS.stream().map(OrderDetailsBaseVO::getChargedPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderDO.setChargedAmount(chargePrice);
        orderMapper.updateById(orderDO);

        sendNotificationChargeOrderInfo(orderDO, orderDetailsCreateReqVOS);

        //处理车辆问题
        //todo zxy 还有积分，考虑使用订阅模式，后续修改
        //parkCouponService.sendParkCoupon(orderDO);


        return transactionConfirmationRespDto;
    }


    private void sendNotificationChargeOrderInfo(OrderDO orderDO, List<OrderDetailsCreateReqVO> orderDetailsCreateReqVOS) {
        NotificationChargeOrderInfoReqDto notificationChargeOrderInfoReqDto = BeanUtil.copyProperties(orderDO, NotificationChargeOrderInfoReqDto.class);
        List<NotificationChargeOrderInfoDetailsReqDto> notificationChargeOrderInfoDetailsReqDtos = BeanUtil.copyToList(orderDetailsCreateReqVOS, NotificationChargeOrderInfoDetailsReqDto.class);
        notificationChargeOrderInfoReqDto.setDetailsReqDtoList(notificationChargeOrderInfoDetailsReqDtos);
        inteconnectedProducer.sendNotificationChargeOrderInfo(notificationChargeOrderInfoReqDto);
    }


    /**
     * 新增订单详情(尖峰平谷)
     *
     * @param dto
     * @param orgId
     */
    @SneakyThrows
    private List<OrderDetailsCreateReqVO> setDetails(TransactionRecordsReqDto dto, Long orderId, Long orgId) {
        List<OrderDetail> chargeDetailTime = getChargeDetailTime(dto);
        if (CollUtil.isEmpty(chargeDetailTime)) {
            return new ArrayList<>();
        }
        List<OrderDetailsCreateReqVO> orderDetailsCreateReqVOS = getOrderDetailsCreateReqVOS(dto, orderId, orgId, chargeDetailTime);

        for (OrderDetailsCreateReqVO orderDetailsCreateReqVO : orderDetailsCreateReqVOS) {
            orderDetailsService.createOrderDetails(orderDetailsCreateReqVO);
        }

        return orderDetailsCreateReqVOS;
    }

    private List<OrderDetailsCreateReqVO> getOrderDetailsCreateReqVOS(TransactionRecordsReqDto dto, Long orderId, Long orgId, List<OrderDetail> chargeDetailTime) {
        List<OrderDetailsCreateReqVO> orderDetailsCreateReqVOS = chargeDetailTime.stream().map(orderDetail -> {
            OrderDetailsCreateReqVO createReqVO = new OrderDetailsCreateReqVO();
            createReqVO.setOrgId(orgId);
            createReqVO.setChargedAllPower(dto.getTotalElectricity());
            createReqVO.setChargedDiscountPrice(BigDecimal.ZERO);
            createReqVO.setChargedPower(orderDetail.getChargedPower());
            createReqVO.setChargedPrice(orderDetail.getChargedPrice());
            createReqVO.setChargedTime(orderDetail.getChargedTime() + "分钟");
            createReqVO.setStartTime(orderDetail.getStartTime() + "");
            createReqVO.setEndTime(orderDetail.getEndTime() + "");
            createReqVO.setOrderAmount(dto.getConsumptionAmount());
            createReqVO.setPriceType(orderDetail.getPriceTypeId());
            createReqVO.setOrderId(orderId + "");
            createReqVO.setServiceDiscountPrice(BigDecimal.ZERO);
            createReqVO.setServicePrice(orderDetail.getServicePrice());
            createReqVO.setTenantId(TenantContextHolder.getTenantId());
            return createReqVO;
        }).collect(Collectors.toList());
        BigDecimal consumptionAmount = dto.getConsumptionAmount().setScale(2, RoundingMode.HALF_UP);
        //防错,防止因为精确度问题出现数据差0.01，或者0.02这种问题
        BigDecimal chargedPrice = orderDetailsCreateReqVOS
                .stream()
                .map(OrderDetailsCreateReqVO::getChargedPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal servicePrice = orderDetailsCreateReqVOS
                .stream()
                .map(OrderDetailsCreateReqVO::getServicePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal allPrice = chargedPrice.add(servicePrice);
        if (allPrice.compareTo(consumptionAmount) != 0) {
            BigDecimal subtract = consumptionAmount.subtract(allPrice);
            orderDetailsCreateReqVOS.get(0).setChargedPrice(orderDetailsCreateReqVOS.get(0).getChargedPrice().add(subtract));
        }
        return orderDetailsCreateReqVOS;
    }

    /**
     * 基于设备编号，获取该设备的计费模板的时间段，进而通过本次充电时间，计算出尖峰平谷在本次充电中所占时长
     *
     * @param dto 交易记录
     * @return 尖峰平谷对应的时间(以分钟计)
     */
    private List<OrderDetail> getChargeDetailTime(TransactionRecordsReqDto dto) throws Exception {

        List<OrderDetail> result = new ArrayList<>();

        EquipmentDO equipmentByPileCode = equipmentService.getEquipmentByPileCode(dto.getPileCode());
        Long costTemplateId = equipmentByPileCode.getCostTemplateId();
        List<CostTemplatePriceDO> costTemplatePriceByTemplateId = costTemplatePriceService.getCostTemplatePriceByTemplateId(costTemplateId);

        // 获取该天的时间(以秒为单位)
        int startTime = this.toTier24TimeSeconds(dto.getStartTime());
        int endTime = this.toTier24TimeSeconds(dto.getEndTime());

        //获取该天的时间戳
        long startTimeDay = dto.getStartTime().getTime() / 1000 - (long) startTime;
        long endTimeDay = dto.getEndTime().getTime() / 1000 - (long) endTime;
        int i = 0;
        int flagStart = -1;
        int flagEnd = -1;
        //查到开始节点和结束节点
        for (CostTemplatePriceDO item : costTemplatePriceByTemplateId) {
            long iStartTime = item.getStartTime() * 30 * 60;
            long iEndTime = item.getEndTime() * 30 * 60;
            if (startTime >= iStartTime && startTime < iEndTime) {
                //说明开始节点在这个节点
                flagStart = i;
            }
            if (endTime > iStartTime && endTime <= iEndTime) {
                //说明结束节点在这个节点
                flagEnd = i;
            }
            i++;
        }

        //判断是否是同一天
        if (startTimeDay == endTimeDay) {

            //说明开始节点和结束节点都在一个时间段内，计算该时间段即可
            if (flagStart == flagEnd) {
                CostTemplatePriceDO costTemplatePriceDO = costTemplatePriceByTemplateId.get(flagStart);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPriceTypeId(costTemplatePriceDO.getPriceTypeId());
                orderDetail.setStartTime(dto.getStartTime().getTime());
                orderDetail.setEndTime(dto.getEndTime().getTime());
                orderDetail.setChargedPower(dto.getTotalElectricity());
                int chargeTime = (endTime - startTime) / 60;
                orderDetail.setChargedTime(chargeTime);
                CostTemplatePriceTypeDO costTemplatePriceTypeDO = costTemplatePriceTypeMapper.selectOne(new QueryWrapper<CostTemplatePriceTypeDO>()
                        .lambda()
                        .eq(CostTemplatePriceTypeDO::getCostTemplateId, costTemplateId)
                        .eq(CostTemplatePriceTypeDO::getTypeName, costTemplatePriceDO.getPriceTypeId())
                );
                BigDecimal servicePrice = dto.getConsumptionAmount()
                        .multiply(costTemplatePriceTypeDO.getPriceService())
                        .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);

                orderDetail.setServicePrice(servicePrice);

                BigDecimal chargedPrice = dto.getConsumptionAmount()
                        .multiply(costTemplatePriceTypeDO.getPricePower())
                        .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);

                orderDetail.setChargedPrice(chargedPrice);

                result.add(orderDetail);
                //说明开始时间小于结束时间,需要计算两头和其中的时间段
            } else if (flagStart < flagEnd) {
                Map<String, Integer> priceMap = new HashMap<>();
                for (int j = flagStart; j <= flagEnd; j++) {
                    CostTemplatePriceDO costTemplatePriceDO = costTemplatePriceByTemplateId.get(j);
                    long iStartTime = costTemplatePriceDO.getStartTime() * 30 * 60;
                    long iEndTime = costTemplatePriceDO.getEndTime() * 30 * 60;
                    int chargeTime;
                    if (startTime >= iStartTime && startTime < iEndTime) {
                        chargeTime = (int) ((iEndTime - startTime) / 60);
                    } else if (endTime > iStartTime && endTime <= iEndTime) {
                        chargeTime = (int) ((endTime - iStartTime) / 60);
                    } else {
                        chargeTime = (costTemplatePriceDO.getEndTime() - costTemplatePriceDO.getStartTime()) * 30;
                    }
                    if (chargeTime == 0) {
                        chargeTime = 1;
                    }
                    if (priceMap.get(costTemplatePriceDO.getPriceTypeId()) != null) {
                        Integer integer = priceMap.get(costTemplatePriceDO.getPriceTypeId());
                        chargeTime += integer;
                    }
                    priceMap.put(costTemplatePriceDO.getPriceTypeId(), chargeTime);
                }

                for (int j = flagStart; j <= flagEnd; j++) {
                    CostTemplatePriceDO costTemplatePriceDO = costTemplatePriceByTemplateId.get(j);
                    long iStartTime = costTemplatePriceDO.getStartTime() * 30 * 60;
                    long iEndTime = costTemplatePriceDO.getEndTime() * 30 * 60;
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setPriceTypeId(costTemplatePriceDO.getPriceTypeId());
                    int chargeTime;
                    if (startTime >= iStartTime && startTime < iEndTime) {
                        orderDetail.setStartTime(dto.getStartTime().getTime());
                        orderDetail.setEndTime((iEndTime * 1000) + startTimeDay * 1000);
                        chargeTime = (int) ((iEndTime - startTime) / 60);
                    } else if (endTime > iStartTime && endTime <= iEndTime) {
                        long startTime1 = iStartTime * 1000;
                        orderDetail.setStartTime(startTime1 + startTimeDay * 1000);
                        orderDetail.setEndTime(dto.getEndTime().getTime());
                        chargeTime = (int) ((endTime - iStartTime) / 60);
                    } else {
                        orderDetail.setStartTime((iStartTime * 1000) + startTimeDay * 1000);
                        orderDetail.setEndTime((iEndTime * 1000) + startTimeDay * 1000);
                        chargeTime = (int) ((iEndTime - iStartTime) / 60);
                    }
                    if (chargeTime == 0) {
                        chargeTime++;
                    }
                    OrderPriceType priceType = this.getPriceType(costTemplatePriceDO.getPriceTypeId(), dto);
                    BigDecimal typePrice = priceType.getPrice();
                    Integer totalTime = priceMap.get(costTemplatePriceDO.getPriceTypeId());
                    BigDecimal price = typePrice.multiply(new BigDecimal(chargeTime)).divide(new BigDecimal(totalTime), 2, RoundingMode.HALF_UP);

                    BigDecimal chargedPower = priceType.getChargedPower();
                    chargedPower = chargedPower.multiply(new BigDecimal(chargeTime)).divide(new BigDecimal(totalTime), 2, RoundingMode.HALF_UP);
                    orderDetail.setChargedPower(chargedPower);
                    orderDetail.setChargedTime(chargeTime);
                    CostTemplatePriceTypeDO costTemplatePriceTypeDO = costTemplatePriceTypeMapper.selectOne(new QueryWrapper<CostTemplatePriceTypeDO>()
                            .lambda()
                            .eq(CostTemplatePriceTypeDO::getCostTemplateId, costTemplateId)
                            .eq(CostTemplatePriceTypeDO::getTypeName, costTemplatePriceDO.getPriceTypeId())
                    );
                    BigDecimal chargedPrice = price
                            .multiply(costTemplatePriceTypeDO.getPricePower())
                            .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);
                    orderDetail.setChargedPrice(chargedPrice);

                    BigDecimal servicePrice = price
                            .multiply(costTemplatePriceTypeDO.getPriceService())
                            .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);
                    orderDetail.setServicePrice(servicePrice);
                    result.add(orderDetail);
                }
            } else {
                throw new Exception("结束时间早于开始时间的订单，存在错误");
            }
            return result;
        } else {

            //跨天最多跨一天
            Map<String, Integer> priceMap = new HashMap<>();

            for (int j = flagStart; j <= costTemplatePriceByTemplateId.size() - 1; j++) {
                CostTemplatePriceDO costTemplatePriceDO = costTemplatePriceByTemplateId.get(j);
                long iEndTime = costTemplatePriceDO.getEndTime() * 30 * 60;
                int chargeTime;
                if (j == flagStart) {
                    chargeTime = (int) ((iEndTime - startTime) / 60);
                } else {
                    chargeTime = (costTemplatePriceDO.getEndTime() - costTemplatePriceDO.getStartTime()) * 30;
                }
                if (priceMap.get(costTemplatePriceDO.getPriceTypeId()) != null) {
                    Integer integer = priceMap.get(costTemplatePriceDO.getPriceTypeId());
                    chargeTime += integer;
                }
                if (chargeTime == 0) {
                    chargeTime++;
                }
                priceMap.put(costTemplatePriceDO.getPriceTypeId(), chargeTime);
            }

            for (int j = 0; j <= flagEnd; j++) {
                CostTemplatePriceDO costTemplatePriceDO = costTemplatePriceByTemplateId.get(j);
                long iStartTime = costTemplatePriceDO.getStartTime() * 30 * 60;
                int chargeTime;
                if (j == flagEnd) {
                    chargeTime = (int) ((endTime - iStartTime) / 60);
                } else {
                    chargeTime = (costTemplatePriceDO.getEndTime() - costTemplatePriceDO.getStartTime()) * 30;
                }
                if (priceMap.get(costTemplatePriceDO.getPriceTypeId()) != null) {
                    Integer integer = priceMap.get(costTemplatePriceDO.getPriceTypeId());
                    chargeTime += integer;
                }
                if (chargeTime == 0) {
                    chargeTime++;
                }
                priceMap.put(costTemplatePriceDO.getPriceTypeId(), chargeTime);
            }


            for (int j = flagStart; j <= costTemplatePriceByTemplateId.size() - 1; j++) {
                CostTemplatePriceDO costTemplatePriceDO = costTemplatePriceByTemplateId.get(j);
                long iStartTime = costTemplatePriceDO.getStartTime() * 30 * 60;
                long iEndTime = costTemplatePriceDO.getEndTime() * 30 * 60;
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPriceTypeId(costTemplatePriceDO.getPriceTypeId());
                int chargeTime;
                if (startTime >= iStartTime && startTime < iEndTime) {
                    orderDetail.setStartTime(dto.getStartTime().getTime());
                    orderDetail.setEndTime((iEndTime * 1000) + startTimeDay * 1000);
                    chargeTime = (int) ((iEndTime - startTime) / 60);
                } else if (endTime > iStartTime && endTime <= iEndTime) {
                    long startTime1 = iStartTime * 1000;
                    orderDetail.setStartTime(startTime1 + startTimeDay * 1000);
                    orderDetail.setEndTime(dto.getEndTime().getTime());
                    chargeTime = (int) ((endTime - iStartTime) / 60);
                } else {
                    orderDetail.setStartTime((iStartTime * 1000) + startTimeDay * 1000);
                    orderDetail.setEndTime((iEndTime * 1000) + startTimeDay * 1000);
                    chargeTime = (int) ((iEndTime - iStartTime) / 60);
                }
                if (chargeTime == 0) {
                    chargeTime++;
                }
                OrderPriceType priceType = this.getPriceType(costTemplatePriceDO.getPriceTypeId(), dto);
                BigDecimal typePrice = priceType.getPrice();
                Integer totalTime = priceMap.get(costTemplatePriceDO.getPriceTypeId());
                BigDecimal price = typePrice.multiply(new BigDecimal(chargeTime)).divide(new BigDecimal(totalTime), 2, RoundingMode.HALF_UP);

                BigDecimal chargedPower = priceType.getChargedPower();
                chargedPower = chargedPower.multiply(new BigDecimal(chargeTime)).divide(new BigDecimal(totalTime), 2, RoundingMode.HALF_UP);
                orderDetail.setChargedPower(chargedPower);
                orderDetail.setChargedTime(chargeTime);
                CostTemplatePriceTypeDO costTemplatePriceTypeDO = costTemplatePriceTypeMapper.selectOne(new QueryWrapper<CostTemplatePriceTypeDO>()
                        .lambda()
                        .eq(CostTemplatePriceTypeDO::getCostTemplateId, costTemplateId)
                        .eq(CostTemplatePriceTypeDO::getTypeName, costTemplatePriceDO.getPriceTypeId())
                );
                BigDecimal chargedPrice = price
                        .multiply(costTemplatePriceTypeDO.getPricePower())
                        .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);
                orderDetail.setChargedPrice(chargedPrice);

                BigDecimal servicePrice = price
                        .multiply(costTemplatePriceTypeDO.getPriceService())
                        .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);
                orderDetail.setServicePrice(servicePrice);
                result.add(orderDetail);
            }

            for (int j = 0; j <= flagEnd; j++) {
                CostTemplatePriceDO costTemplatePriceDO = costTemplatePriceByTemplateId.get(j);
                long iStartTime = costTemplatePriceDO.getStartTime() * 30 * 60;
                long iEndTime = costTemplatePriceDO.getEndTime() * 30 * 60;
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setPriceTypeId(costTemplatePriceDO.getPriceTypeId());
                int chargeTime;
                if (startTime >= iStartTime && startTime < iEndTime) {
                    orderDetail.setStartTime(dto.getStartTime().getTime());
                    orderDetail.setEndTime((iEndTime * 1000) + endTimeDay * 1000);
                    chargeTime = (int) ((iEndTime - startTime) / 60);
                } else if (endTime > iStartTime && endTime <= iEndTime) {
                    long startTime1 = iStartTime * 1000;
                    orderDetail.setStartTime(startTime1 + endTimeDay * 1000);
                    orderDetail.setEndTime(dto.getEndTime().getTime());
                    chargeTime = (int) ((endTime - iStartTime) / 60);
                } else {
                    orderDetail.setStartTime((iStartTime * 1000) + endTimeDay * 1000);
                    orderDetail.setEndTime((iEndTime * 1000) + endTimeDay * 1000);
                    chargeTime = (int) ((iEndTime - iStartTime) / 60);
                }
                if (chargeTime == 0) {
                    chargeTime++;
                }
                OrderPriceType priceType = this.getPriceType(costTemplatePriceDO.getPriceTypeId(), dto);
                BigDecimal typePrice = priceType.getPrice();
                Integer totalTime = priceMap.get(costTemplatePriceDO.getPriceTypeId());
                BigDecimal price = typePrice.multiply(new BigDecimal(chargeTime)).divide(new BigDecimal(totalTime), 2, RoundingMode.HALF_UP);

                BigDecimal chargedPower = priceType.getChargedPower();
                chargedPower = chargedPower.multiply(new BigDecimal(chargeTime)).divide(new BigDecimal(totalTime), 2, RoundingMode.HALF_UP);
                orderDetail.setChargedPower(chargedPower);
                orderDetail.setChargedTime(chargeTime);
                CostTemplatePriceTypeDO costTemplatePriceTypeDO = costTemplatePriceTypeMapper.selectOne(new QueryWrapper<CostTemplatePriceTypeDO>()
                        .lambda()
                        .eq(CostTemplatePriceTypeDO::getCostTemplateId, costTemplateId)
                        .eq(CostTemplatePriceTypeDO::getTypeName, costTemplatePriceDO.getPriceTypeId())
                );
                BigDecimal chargedPrice = price
                        .multiply(costTemplatePriceTypeDO.getPricePower())
                        .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);
                orderDetail.setChargedPrice(chargedPrice);

                BigDecimal servicePrice = price
                        .multiply(costTemplatePriceTypeDO.getPriceService())
                        .divide((costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService())), 2, RoundingMode.HALF_UP);
                orderDetail.setServicePrice(servicePrice);
                result.add(orderDetail);
            }


            return result;
//        OrderDO item = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda().eq(OrderDO::getSeqNumber, seqNumber));
//        if (item == null) {
//            return null;
//        } else {
//            CurrentOrderRespVO orderRespVO = getCurrentOrderRespVO(item);
//            List<OrderDetailsDO> orderDetailsList = orderDetailsService.getOrderDetailsList(item.getId());
//            orderRespVO.setOrderDetailsRespVOList(OrderDetailsConvert.INSTANCE.convertList(orderDetailsList));
//            return orderRespVO;
//        }
        }
    }

    @Override
    public OrderDO getOrderByTradeSeq(String tradeSeq) {
        return orderMapper.selectOne(new LambdaQueryWrapperX<OrderDO>().eq(OrderDO::getTradeSeq, tradeSeq));
    }

    @Override
    public List<OrderDO> getOrderByTradeSeqList(Collection<String> tradeSeqList) {
        return orderMapper.selectList(new LambdaQueryWrapperX<OrderDO>().in(OrderDO::getTradeSeq, tradeSeqList));
    }


    @Override
    public OrderDO startChargeByTrade(OrderChargeReqDto dto) {
        EquipmentConnectorDO equipmentConnector = equipmentConnectorService.getEquipmentConnector(dto.getGunId());

        if (equipmentConnector == null) {
            throw exception(EQUIPMENT_CONNECTOR_NOT_EXISTS);
        }

        if (EnableStartEnum.DISABLE.getStatus().equals(equipmentConnector.getIsEnableStart())) {
            throw exception(EQUIPMENT_CONNECTOR_NOT_ENABLED);
        }

        OnlineEquipmentConnectorDO connectorDO = onlineEquipmentConnectorService.getObject(dto.getGunId());
        //开始充电，需要判断枪 状态    todo
        if (connectorDO.getIsInsertArms() == null || !connectorDO.getIsInsertArms().equals("01")) {
            throw exception(EQUIPMENT_CONNECTOR_INSERT_ARMS);
        }
        if (connectorDO.getWorkState() == null || !connectorDO.getWorkState().equals("02")) {
            throw exception(EQUIPMENT_CONNECTOR_WORKING_POSITION);
        }

        //根据电桩去查站点,一定可以查到
        StationDO stationDO = stationMapper.selectById(equipmentConnector.getStationId());
        if (stationDO == null) {
            throw exception(STATION_NOT_EXISTS);
        }

        dto.setChargingWay(5);// 互联互通过来的
        OrderDO orderDO = buildOrderDoByTrade(equipmentConnector, stationDO, dto);
        String logicNumber = "1234567890123456";
        String physicalNumber = "1234567890123456";
        BigDecimal bigDecimal = new BigDecimal("100");

        Long equipmentId = equipmentConnector.getEquipmentId();
        EquipmentDO equipment = equipmentService.getEquipment(equipmentId);

        equipmentConnectorService.startCharge(equipment.getEquipmentCode(),
                equipmentConnector.getConnectorCode().substring(equipmentConnector.getConnectorCode().length() - 2),
                orderDO.getSeqNumber(),
                logicNumber,
                physicalNumber,
                bigDecimal);

        //发送启动结果
        if (StringUtils.isNoneEmpty(orderDO.getTradeSeq())) {
            NotificationStartChargeReqDto notificationStartChargeReqDto = new NotificationStartChargeReqDto();
            notificationStartChargeReqDto.setStationId(orderDO.getStationId());
            notificationStartChargeReqDto.setTradeSeq(orderDO.getTradeSeq());
            notificationStartChargeReqDto.setStartTime(orderDO.getStartTime());
            notificationStartChargeReqDto.setState(orderDO.getOrderStatus());
            notificationStartChargeReqDto.setGunNo(orderDO.getGunId());
            notificationStartChargeReqDto.setPileCode(orderDO.getGunId().substring(0, orderDO.getGunId().length() - 2));
            inteconnectedProducer.sendNotificationStartCharge(notificationStartChargeReqDto);
        }

        return orderDO;
    }

    @Override
    public OrderDO endChargeByTrade(OrderChargeReqDto dto) {
        equipmentConnectorService.endCharge(dto.getGunId().substring(0, dto.getGunId().length() - 2), dto.getGunId().substring(dto.getGunId().length() - 2));
        //修改订单状态
        OrderDO orderDO = new OrderDO();
        orderDO.setTradeStatus("3");
        orderDO.setTradeSeq(dto.getTradeSeq());
        orderDO.setGunId(dto.getGunId());

        //sendNotificationStopCharge
        OrderDO selectOne = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda().eq(OrderDO::getTradeSeq, dto.getTradeSeq()));
        NotificationStopChargeReqDto reqDto = new NotificationStopChargeReqDto();
        reqDto.setTradeSeq(selectOne.getTradeSeq());
        reqDto.setStationId(selectOne.getStationId());
        reqDto.setState(OrderStatusEnum.IN_PROGRESS.getStatus());
        reqDto.setGunNo(selectOne.getGunId());
        reqDto.setPileCode(selectOne.getGunId().substring(0, selectOne.getGunId().length() - 2));
        reqDto.setSuccStat(0);
        reqDto.setFailReason(0);
        inteconnectedProducer.sendNotificationStopCharge(reqDto);
        return orderDO;

    }

    @TenantIgnore
    @Override
    public OrderDO checkBalance(RealTimeMonitor realTimeMonitor) {

        String gunId = realTimeMonitor.getPileCode() + realTimeMonitor.getGunNo();

        int i = orderMapper.updateChargeState(gunId,
                realTimeMonitor.getTransactionSerialNo(),
                ChargeStatusEnum.INIT_CHARGE.getStatus(),
                ChargeStatusEnum.START_CHARGE.getStatus());
        if (i == 1) {
            log.info("开启充电成功");
            OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda()
                    .eq(OrderDO::getGunId, gunId)
                    .eq(OrderDO::getSeqNumber, realTimeMonitor.getTransactionSerialNo())
            );
            orderDO.setOrderStatus(OrderStatusEnum.CHARGING.getStatus());
            orderMapper.updateById(orderDO);
            //发送启动结果
            if (StringUtils.isNoneEmpty(orderDO.getTradeSeq())) {
                NotificationStartChargeReqDto dto = new NotificationStartChargeReqDto();
                dto.setStationId(orderDO.getStationId());
                dto.setTradeSeq(orderDO.getTradeSeq());
                dto.setStartTime(orderDO.getStartTime());
                dto.setState(orderDO.getOrderStatus());
                dto.setGunNo(orderDO.getGunId());
                dto.setPileCode(orderDO.getGunId().substring(0, orderDO.getGunId().length() - 2));
                inteconnectedProducer.sendNotificationStartCharge(dto);
            }
        }

        String transactionSerialNo = realTimeMonitor.getTransactionSerialNo();
        OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda().eq(OrderDO::getSeqNumber, transactionSerialNo));
        // 通联订单不进行余额查询
        if (ObjectUtils.isEmpty(orderDO) || StringUtils.isNoneEmpty(orderDO.getTradeSeq())) {
            return orderDO;
        }
        Long userId = orderDO.getUserId();
        TenantContextHolder.setTenantId(orderDO.getTenantId());
        CommonResult<PayWalletRespDTO> wallet = payWalletApi.getWallet(userId);
        if (wallet.getCode() == 0) {
            Integer balance = wallet.getData().getBalance();
            BigDecimal chargedAmount = realTimeMonitor.getChargedAmount();
            int chargedAmountInt = chargedAmount.multiply(new BigDecimal(100)).intValue();
            if ((balance - chargedAmountInt) < 100) {
                this.endCharge(realTimeMonitor.getTransactionSerialNo(), StopChargeMessageEnum.INSUFFICIENT_BALANCE);
            }
        } else {
            log.error(wallet.getMsg());
        }
        return orderDO;
    }

    @Override
    public List<OrderInfoRespDto> getOrderInfo(Long userId) {
        List<OrderDO> orderDOS = orderMapper.selectList(new QueryWrapper<OrderDO>().lambda().eq(OrderDO::getUserId, userId));
        return InterconnectedConvert.INSTANCE.convert03(orderDOS);
    }

    @TenantIgnore
    @Override
    public void errorStart(RealTimeMonitor realTimeMonitor) {
        String gunId = realTimeMonitor.getPileCode() + realTimeMonitor.getGunNo();
        List<OrderDO> orderDOS = orderMapper.selectList(new QueryWrapper<OrderDO>().lambda()
                .eq(OrderDO::getGunId, gunId)
                .eq(OrderDO::getChargeStatus, ChargeStatusEnum.INIT_CHARGE.getStatus()));
        LocalDateTime now = LocalDateTime.now();
        orderDOS.stream()
                .filter(item -> Duration.between(item.getStartTime(), now).getSeconds() >= 60)
                .forEach(item -> {
                    startError(item.getSeqNumber(), gunId);
                });
    }

    @TenantIgnore
    @Override
    public void monitoringCharge(RealTimeMonitor realTimeMonitor) {
        String pileCode = realTimeMonitor.getPileCode() + realTimeMonitor.getGunNo();
        List<OrderDO> orderDOS = orderMapper.selectList(new QueryWrapper<OrderDO>().lambda()
                .eq(OrderDO::getGunId, pileCode)
                .eq(OrderDO::getChargeStatus, ChargeStatusEnum.START_CHARGE.getStatus())
        );
        orderDOS.forEach(item -> {
            LocalDateTime updateTime = item.getUpdateTime();
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(updateTime, now);
            if (duration.toHours() >= 6) {
                item.setChargeStatus(ChargeStatusEnum.ORDER_ERROR.getStatus());
                item.setOrderStatus(OrderStatusEnum.ERROR.getStatus());
                orderMapper.updateById(item);
            }
        });
    }


    @Override
    public List<OrderDO> getMonthOrder(LocalDate month, Long stationId) {
        LocalDateTime startTime = month.atStartOfDay();
        LocalDateTime endTime = month.plusMonths(1).atStartOfDay();
        return orderMapper.selectList(new QueryWrapper<OrderDO>()
                .lambda()
                .eq(OrderDO::getChargeStatus, ChargeStatusEnum.ACCEPT_ORDER.getStatus())
                .eq(stationId != null, OrderDO::getStationId, stationId)
                .between(OrderDO::getCreateTime, startTime, endTime)
        );

    }

    @Override
    public void orderRethrust(Long id) {
        OrderDO orderDO = orderMapper.selectById(id);
        if (orderDO.getOrderStatus() != 4) {
            throw exception(ORDER_STATUS_NOT_COMPLETED);
        }
        if (StringUtils.isEmpty(orderDO.getTradeSeq())) {
            throw exception(ORDER_NOT_INTERCONNECTED);
        }
        List<OrderDetailsDO> orderDetailsCreateReqVOS = orderDetailsService.getOrderDetailsByOrderId(id);
        NotificationChargeOrderInfoReqDto notificationChargeOrderInfoReqDto = BeanUtil.copyProperties(orderDO, NotificationChargeOrderInfoReqDto.class);
        List<NotificationChargeOrderInfoDetailsReqDto> notificationChargeOrderInfoDetailsReqDtos = BeanUtil.copyToList(orderDetailsCreateReqVOS, NotificationChargeOrderInfoDetailsReqDto.class);
        notificationChargeOrderInfoReqDto.setDetailsReqDtoList(notificationChargeOrderInfoDetailsReqDtos);
        inteconnectedProducer.sendNotificationChargeOrderInfo(notificationChargeOrderInfoReqDto);
    }


    private OrderDO buildOrderDoByTrade(EquipmentConnectorDO equipmentConnector, StationDO stationDO, OrderChargeReqDto dto) {

        //生成编号
        String orderNo = GeneratorUtil.genOrderId(equipmentConnector.getConnectorCode());
        OrderDO orderDO = new OrderDO();
        orderDO.setSeqNumber(orderNo);
        orderDO.setTradeSeq(dto.getTradeSeq());
        orderDO.setTradeStatus("1");
        orderDO.setTradeTime(LocalDateTime.now());
        orderDO.setChargingWay(dto.getChargingWay());
        orderDO.setStationId(stationDO.getId());
        orderDO.setStationName(stationDO.getStationName());
        orderDO.setOrgId(stationDO.getOrgId());
        orderDO.setStartTime(LocalDateTime.now());
        //先设为1,后续修改
        orderDO.setChargeStatus(ChargeStatusEnum.INIT_CHARGE.getStatus());
        orderDO.setOrderStatus(OrderStatusEnum.BE_STARTING.getStatus());
        orderDO.setGunId(equipmentConnector.getConnectorCode());
        orderDO.setGunName(equipmentConnector.getConnectorName());

        //车牌号
        orderDO.setPlateNumber(dto.getPlateNumber());

        if (orderMapper.insert(orderDO) != 1) {
            throw exception(ORDER_ADD_FAILURE);
        }
        return orderDO;
    }


    private OrderPriceType getPriceType(String priceTypeId, TransactionRecordsReqDto dto) {
        OrderPriceType orderPriceType = new OrderPriceType();
        if (POINTED.equals(priceTypeId)) {
            orderPriceType.setChargedPower(dto.getSharpCharge());
            orderPriceType.setPrice(dto.getSharpAmount());
        } else if (PEAK.equals(priceTypeId)) {
            orderPriceType.setChargedPower(dto.getPeakPower());
            orderPriceType.setPrice(dto.getPeakAmount());
        } else if (FLAT.equals(priceTypeId)) {
            orderPriceType.setChargedPower(dto.getAverageElectricQuantity());
            orderPriceType.setPrice(dto.getFlatAmount());
        } else if (VALLEY.equals(priceTypeId)) {
            orderPriceType.setChargedPower(dto.getValleyPower());
            orderPriceType.setPrice(dto.getValleyAmount());
        }
        return orderPriceType;
    }

    //以秒为单位进行计算，才方便对分钟是否+1进行判断
    public int toTier24TimeSeconds(Date date) {
        // 获取小时、分钟和秒
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat secondFormat = new SimpleDateFormat("ss");
        int hour = Integer.parseInt(hourFormat.format(date));
        int minute = Integer.parseInt(minuteFormat.format(date));
        int second = Integer.parseInt(secondFormat.format(date));
        // 计算总秒数
        return hour * 3600 + minute * 60 + second;
    }


    /**
     * 开始充电回复命令处理，知道是否充电成功
     *
     * @param startChargingCommandReply
     */
    @Override
    @TenantIgnore
    @Transactional
    public void startReplyCharge(StartChargingCommandReply startChargingCommandReply) {
        String gunId = startChargingCommandReply.getPileCode() + startChargingCommandReply.getGunNo();
        if ("00".equals(startChargingCommandReply.getLaunchResult())) {
            log.error("收到电桩回复，充电开启失败，原因:{}", startChargingCommandReply.getFailureReason());
            startError(startChargingCommandReply.getTransactionSerialNo(), gunId);
        }

    }

    private void startError(String transactionSerialNo, String gunId) {
        int i = orderMapper.updateChargeState(gunId,
                transactionSerialNo,
                ChargeStatusEnum.INIT_CHARGE.getStatus(),
                ChargeStatusEnum.INIT_CHARGE_ERROR.getStatus());
        if (i != 1) {
            log.error("{}更新状态失败,已经被更新，或者未查到该订单", gunId);
        } else {
            OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda()
                    .eq(OrderDO::getGunId, gunId)
                    .eq(OrderDO::getSeqNumber, transactionSerialNo)
            );
            orderDO.setOrderStatus(OrderStatusEnum.UNKNOWN.getStatus());
            orderDO.setStopReason(StopChargeMessageEnum.ERROR.getStatus());
            orderMapper.updateById(orderDO);
        }
    }

    /**
     * 结束充电命令处理，知道是否充电结束成功
     *
     * @param remoteShutdownCommandReply
     */
    @Override
    @TenantIgnore
    @Transactional
    public void endReplyCharge(RemoteShutdownCommandReply remoteShutdownCommandReply) {
        String gunId = remoteShutdownCommandReply.getPileCode() + remoteShutdownCommandReply.getGunNo();
        if ("01".equals(remoteShutdownCommandReply.getStopResult())) {

            OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>()
                    .lambda()
                    .eq(OrderDO::getGunId, gunId)
                    .eq(OrderDO::getChargeStatus, ChargeStatusEnum.INIT_END_CHARGE.getStatus())
            );
            if (orderDO == null) {
                log.error("有一条结束充电命令没有找到对应订单,枪号为:{}", gunId);
                return;
            }
            int i = orderMapper.updateChargeState(remoteShutdownCommandReply.getPileCode() + remoteShutdownCommandReply.getGunNo(),
                    orderDO.getSeqNumber(),
                    ChargeStatusEnum.INIT_END_CHARGE.getStatus(),
                    ChargeStatusEnum.INIT_END_CHARGE_REPLY.getStatus()
            );
            if (i != 1) {
                log.error("有一条结束充电命令没有找到对应订单,枪号为:{}", gunId);
            } else {
                log.error("成功收到结束充电回复确认命令");
            }
        } else {
            OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>()
                    .lambda()
                    .eq(OrderDO::getGunId, gunId)
                    .eq(OrderDO::getChargeStatus, ChargeStatusEnum.INIT_END_CHARGE.getStatus())
            );
            if (orderDO == null) {
                log.error("有一条结束充电命令没有找到对应订单,枪号为:{}", gunId);
                return;
            }
            int i = orderMapper.updateChargeState(remoteShutdownCommandReply.getPileCode() + remoteShutdownCommandReply.getGunNo(),
                    orderDO.getSeqNumber(),
                    ChargeStatusEnum.INIT_END_CHARGE.getStatus(),
                    ChargeStatusEnum.END_CHARGE_ERROR.getStatus()
            );
            if (i != 1) {
                log.error("有一条结束充电命令没有找到对应订单,枪号为:{}", gunId);
            }
        }
    }

    @Override
    @TenantIgnore
    public void stopCharging(StopCharging stopCharging) {
        String gunId = stopCharging.getPileCode() + stopCharging.getGunCode();

        OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>().lambda()
                .eq(OrderDO::getGunId, gunId)
                .eq(OrderDO::getSeqNumber, stopCharging.getTransactionSerialNo())
                .ne(OrderDO::getChargeStatus, ChargeStatusEnum.END_CHARGE.getStatus())
        );
        if (orderDO == null) {
            return;
        }
        if (orderDO.getStopReason() == null) {
            orderDO.setStopReason(StopChargeMessageEnum.FULL.getStatus());
        }
        orderDO.setChargeStatus(ChargeStatusEnum.END_CHARGE.getStatus());
        orderDO.setOrderStatus(OrderStatusEnum.HAVE_FINISHED.getStatus());
        orderMapper.updateById(orderDO);
        if (!StringUtils.isEmpty(orderDO.getTradeSeq())) {
            //sendNotificationStopCharge
            NotificationStopChargeReqDto reqDto = new NotificationStopChargeReqDto();
            reqDto.setTradeSeq(orderDO.getTradeSeq());
            reqDto.setStationId(orderDO.getStationId());
            reqDto.setState(OrderStatusEnum.HAVE_FINISHED.getStatus());
            reqDto.setGunNo(orderDO.getGunId());
            reqDto.setPileCode(orderDO.getGunId().substring(0, orderDO.getGunId().length() - 2));
            reqDto.setSuccStat(0);
            reqDto.setFailReason(0);
            inteconnectedProducer.sendNotificationStopCharge(reqDto);
        }
    }


    private CurrentOrderRespVO getCurrentOrderRespVO(OrderDO item) {

        CurrentOrderRespVO currentOrderRespVO = new CurrentOrderRespVO();
        currentOrderRespVO.setSeqNumber(item.getSeqNumber());
        currentOrderRespVO.setUserId(item.getUserId());
        currentOrderRespVO.setUserPhone(item.getMobile());
        currentOrderRespVO.setUserAccount(item.getUserId());
        currentOrderRespVO.setUserName(item.getNickName());
        currentOrderRespVO.setCardNo(item.getCardNo());
        currentOrderRespVO.setCompanyName(item.getCompanyName());
        currentOrderRespVO.setCompanyId(item.getCompanyId());
        currentOrderRespVO.setCarNumber(item.getPlateNumber());
        currentOrderRespVO.setStationId(item.getStationId());
        currentOrderRespVO.setStationName(item.getStationName());
        currentOrderRespVO.setOrderType(item.getOrderType());
        currentOrderRespVO.setEquipmentConnectorCode(item.getGunId());
        currentOrderRespVO.setOrgId(item.getOrgId());
        currentOrderRespVO.setChargingSource(item.getChargingSource());
        currentOrderRespVO.setChargingWay(item.getChargingWay());
        currentOrderRespVO.setStartTime(item.getStartTime());
        currentOrderRespVO.setEndTime(item.getEndTime());
        currentOrderRespVO.setEquipmentConnectorPower(item.getPilePower());
        currentOrderRespVO.setChargeStatus(item.getChargeStatus());
        currentOrderRespVO.setOrderStatus(item.getOrderStatus());
        currentOrderRespVO.setCreateTime(item.getCreateTime());
        currentOrderRespVO.setUpdateTime(item.getUpdateTime());
        currentOrderRespVO.setCommunicationType(item.getPileType());
        currentOrderRespVO.setStopReason(item.getStopReason());

        // todo zxy 异常原因
        currentOrderRespVO.setErrorMessage("");
        //todo zxy 电桩功率还没做，做了后修改，目前默认100
        currentOrderRespVO.setGunPower(100);
        CommonResult<OperatorRespDTO> operator = operatorApi.getOperator(item.getOrgId());
        currentOrderRespVO.setOperatorName(operator.getData().getOrgName());

        if (item.getOrderStatus().equals(OrderStatusEnum.HAVE_FINISHED.getStatus())) {
            if (item.getPayTime() != null) {
                currentOrderRespVO.setChargeTime(item.getChargedTime());
                currentOrderRespVO.setPayTime(item.getPayTime().toString());
                currentOrderRespVO.setChargedPower(item.getChargedPower());
                currentOrderRespVO.setChargedAmount(item.getChargedAmount());
                currentOrderRespVO.setChargedDiscountAmount(new BigDecimal(StringUtils.isEmpty(item.getChargedDiscountAmount()) ? "0" : item.getChargedDiscountAmount()));
                currentOrderRespVO.setRedPacketConsume(item.getRedPacketConsume());
                currentOrderRespVO.setAllAmount(item.getAllAmount());
                currentOrderRespVO.setInPay(item.getInPay());
                currentOrderRespVO.setStartTime(item.getStartTime());
                currentOrderRespVO.setEndTime(item.getEndTime());
                currentOrderRespVO.setServiceAmount(item.getAllAmount().subtract(item.getChargedAmount()));
                List<OrderDetailsDO> orderDetailsByOrderId = orderDetailsService.getOrderDetailsByOrderId(item.getId());
                List<OrderDetailsVO> collect = orderDetailsByOrderId.stream().map(orderDetailsDO -> {
                    OrderDetailsVO orderDetailsVO = BeanUtil.copyProperties(orderDetailsDO, OrderDetailsVO.class);
                    orderDetailsVO.setStartTime(Long.valueOf(orderDetailsDO.getStartTime()));
                    orderDetailsVO.setEndTime(Long.valueOf(orderDetailsDO.getEndTime()));
                    return orderDetailsVO;
                }).collect(Collectors.toList());
                currentOrderRespVO.setOrderDetailsRespVOList(collect);
            }
        } else {
            RealTimeOrderDO realTimeOrderDO = realTimeOrderService.get(item.getSeqNumber());
            if (realTimeOrderDO != null) {
                currentOrderRespVO.setChargeTime(realTimeOrderDO.getAccumulatedChargingTime());
                currentOrderRespVO.setChargedPower(realTimeOrderDO.getChargingDegree());
                currentOrderRespVO.setSoc(realTimeOrderDO.getSOC());
                currentOrderRespVO.setChargedAmount(realTimeOrderDO.getChargedAmount());
                currentOrderRespVO.setOutputCurrent(realTimeOrderDO.getOutputCurrent());
                currentOrderRespVO.setOutputVoltage(realTimeOrderDO.getOutputVoltage());
            }
        }

        //根据电桩去查站点,一定可以查到
        StationDO stationDO = stationMapper.selectById(item.getStationId());
        currentOrderRespVO.setAddress(stationDO.getAddress());
        currentOrderRespVO.setStationLat(stationDO.getStationLat());
        currentOrderRespVO.setStationLng(stationDO.getStationLng());

        EquipmentDO equipmentDO = equipmentMapper.selectOne(new QueryWrapper<EquipmentDO>()
                .lambda()
                .eq(EquipmentDO::getEquipmentCode, item.getGunId().substring(item.getGunId().length() - 2)));
        if (equipmentDO != null) {
            currentOrderRespVO.setEquipmentModelType(equipmentDO.getEquipmentModelType());
        }
        EquipmentConnectorDO equipmentConnectorDO = equipmentConnectorService.getEquipmentConnector(item.getGunId());
        if (equipmentConnectorDO != null) {
            currentOrderRespVO.setEquipmentConnectorName(equipmentConnectorDO.getConnectorName());
            currentOrderRespVO.setEquipmentCode(equipmentConnectorDO.getConnectorCode().substring(0, 14));
        }


        return currentOrderRespVO;
    }

    /**
     * 电桩id
     *
     * @param connectorCode 电桩code
     * @param plateNumber
     * @return
     */
    @Override
    public String startCharge(String connectorCode, String plateNumber) {

        EquipmentConnectorDO equipmentConnector = equipmentConnectorService.getEquipmentConnector(connectorCode);


        if (equipmentConnector == null) {
            throw exception(EQUIPMENT_CONNECTOR_NOT_EXISTS);
        }

        if (EnableStartEnum.DISABLE.getStatus().equals(equipmentConnector.getIsEnableStart())) {
            throw exception(EQUIPMENT_CONNECTOR_NOT_ENABLED);
        }

        OnlineEquipmentConnectorDO connectorDO = onlineEquipmentConnectorService.getObject(connectorCode);
        if (connectorDO == null) {
            throw exception(EQUIPMENT_CONNECTOR_NOT_EXISTS);
        }


        //开始充电，需要判断枪 状态    todo
        if (connectorDO.getIsInsertArms() == null || !connectorDO.getIsInsertArms().equals("01")) {
            throw exception(EQUIPMENT_CONNECTOR_INSERT_ARMS);
        }
        if (connectorDO.getWorkState() == null || !connectorDO.getWorkState().equals("02")) {
            throw exception(EQUIPMENT_CONNECTOR_WORKING_POSITION);
        }

        //根据电桩去查站点,一定可以查到
        StationDO stationDO = stationMapper.selectById(equipmentConnector.getStationId());
        if (stationDO == null) {
            throw exception(STATION_NOT_EXISTS);
        }

        Long uid = SecurityFrameworkUtils.getLoginUserId();
        if (StringUtils.isNotBlank(plateNumber)) {
            memberPlateApi.addPlateNumber(uid, plateNumber);
        }
        MemberUserRespDTO user = memberUserApi.getUser(uid).getCheckedData();

        // 查询没有结束的订单
        List<OrderDO> orderDOS = orderMapper.selectList(new QueryWrapper<OrderDO>()
                .lambda()
                .eq(OrderDO::getUserId, uid)
                .ne(OrderDO::getChargeStatus, ChargeStatusEnum.ACCEPT_ORDER.getStatus())
        );
        // 如果存在,选择报错
        for (OrderDO orderDO : orderDOS) {
            if (ChargeStatusEnum.INIT_CHARGE.getStatus().equals(orderDO.getChargeStatus())) {
                if (connectorCode.equals(orderDO.getGunId())) {
                    throw exception(ORDER_NOT_REPEAT_START);
                } else {
                    throw exception(ORDER_NOT_END);
                }
            } else if (ChargeStatusEnum.START_CHARGE.getStatus().equals(orderDO.getChargeStatus())) {
                throw exception(ORDER_NOT_END);
            } else if (ChargeStatusEnum.INIT_END_CHARGE.getStatus().equals(orderDO.getChargeStatus()) ||
                    ChargeStatusEnum.INIT_END_CHARGE_REPLY.getStatus().equals(orderDO.getChargeStatus()) ||
                    ChargeStatusEnum.END_CHARGE.getStatus().equals(orderDO.getChargeStatus())) {
                throw exception(ORDER_NOT_HANDLER);
            } else if (ChargeStatusEnum.END_CHARGE_ERROR.getStatus().equals(orderDO.getChargeStatus()) ||
                    ChargeStatusEnum.ORDER_ERROR.getStatus().equals(orderDO.getChargeStatus())) {
                throw exception(ORDER_ERROR_ORDER);
            }
        }


        CommonResult<PayWalletRespDTO> wallet = payWalletApi.getWallet(user.getId());
        if (wallet.getCode() != 0) {
            throw exception(MONEY_NOT_FIND);
        }
        Integer balance = wallet.getData().getBalance();
        if (balance < 100) {
            throw exception(MONEY_NOT_ENOUGH);
        }

        EquipmentModelDO equipmentModel = equipmentModelService.getEquipmentModel(equipmentConnector.getEquipmentModelId());


        OrderDO orderDO = buildOrderDO(user, equipmentConnector, stationDO, plateNumber, equipmentModel.getType());

//        Long orderId = orderDO.getId();
        String logicNumber = "1234567890123456";
        String physicalNumber = "1234567890123456";
        BigDecimal bigDecimal = new BigDecimal("100");
        Long equipmentId = equipmentConnector.getEquipmentId();

        EquipmentDO equipment = equipmentService.getEquipment(equipmentId);


        equipmentConnectorService.startCharge(equipment.getEquipmentCode(),
                equipmentConnector.getConnectorCode().substring(equipmentConnector.getConnectorCode().length() - 2),
                orderDO.getSeqNumber(),
                logicNumber,
                physicalNumber,
                bigDecimal);
        return orderDO.getSeqNumber();
    }

    private OrderDO buildOrderDO(MemberUserRespDTO uid, EquipmentConnectorDO equipmentConnector, StationDO stationDO, String plateNumber, Integer communicationType) {

        //生成编号
        String orderNo = GeneratorUtil.genOrderId(equipmentConnector.getConnectorCode());
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(uid.getId());
        orderDO.setUserAccount(uid.getAccount());
        orderDO.setNickName(uid.getNickname());
        orderDO.setMobile(uid.getMobile());
        orderDO.setCompanyId(uid.getCompanyId());
        orderDO.setOrderType("1");
        orderDO.setChargingWay(1);
        // 订单流水号
        orderDO.setSeqNumber(orderNo);
        orderDO.setStationId(stationDO.getId());
        orderDO.setStationName(stationDO.getStationName());
        orderDO.setOrgId(stationDO.getOrgId());
        orderDO.setStartTime(LocalDateTime.now());
        //先设为1,后续修改
        orderDO.setChargeStatus(ChargeEquipStatusEnum.FREE_TIME.getStatus());
        orderDO.setOrderStatus(OrderStatusEnum.BE_STARTING.getStatus());
        orderDO.setGunId(equipmentConnector.getConnectorCode());
        orderDO.setGunName(equipmentConnector.getConnectorName());
        orderDO.setPileType(communicationType.toString());
        // zxy 20240103

        //车牌号
        if (StringUtils.isNotEmpty(plateNumber)) {
            orderDO.setPlateNumber(plateNumber);
        }


        if (orderMapper.insert(orderDO) != 1) {
            throw exception(ORDER_ADD_FAILURE);
        }
        return orderDO;
    }

    /**
     * 结束充电
     *
     * @param seqNumber 订单id
     * @return
     */
    @Override
    @Transactional
    public Boolean endCharge(String seqNumber, StopChargeMessageEnum stopChargeMessageEnum) {

        OrderDO orderDO = orderMapper.selectOne(new QueryWrapper<OrderDO>()
                .lambda()
                .eq(OrderDO::getSeqNumber, seqNumber)
                .eq(OrderDO::getChargeStatus, ChargeStatusEnum.START_CHARGE.getStatus())
        );
        if (orderDO == null) {
            throw exception(ORDER_NOT_EXISTS);
        }
        int i = orderMapper.updateChargeState(orderDO.getGunId(),
                orderDO.getSeqNumber(),
                ChargeStatusEnum.START_CHARGE.getStatus(),
                ChargeStatusEnum.INIT_END_CHARGE.getStatus());

        orderDO.setStopReason(stopChargeMessageEnum.getStatus());
        orderDO.setOrderStatus(OrderStatusEnum.IN_PROGRESS.getStatus());
        orderMapper.updateById(orderDO);
        if (i == 1) {
            //发送停止指令
            String connectorCode = orderDO.getGunId();
            equipmentConnectorService.endCharge(connectorCode.substring(0, connectorCode.length() - 2), connectorCode.substring(connectorCode.length() - 2));
            //修改订单状态
            return true;
        } else {
            return false;
        }
    }

    /**
     * 历史订单
     *
     * @param pageVO
     * @return
     */
    @Override
    public List<OrderDO> getHistoryOrderList(HistoryOrderReqVO pageVO) {
        return orderMapper.selectList(pageVO);
    }

    @Override
    public PageResult<OrderDO> getPendingOrderPage(PendingOrderPageReqVO pageVO) {
        return orderMapper.selectPage(pageVO);
    }


}
