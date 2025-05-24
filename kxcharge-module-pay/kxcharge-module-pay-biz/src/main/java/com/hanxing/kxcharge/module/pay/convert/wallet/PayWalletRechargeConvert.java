package com.hanxing.kxcharge.module.pay.convert.wallet;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils;
import com.hanxing.kxcharge.framework.common.util.collection.MapUtils;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.framework.dict.core.DictFrameworkUtils;
import com.hanxing.kxcharge.module.pay.controller.app.wallet.vo.recharge.AppPayWalletRechargeCreateRespVO;
import com.hanxing.kxcharge.module.pay.controller.app.wallet.vo.recharge.AppPayWalletRechargeRespVO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.order.PayOrderDO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.wallet.PayWalletRechargeDO;
import com.hanxing.kxcharge.module.pay.enums.DictTypeConstants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayWalletRechargeConvert {

    PayWalletRechargeConvert INSTANCE = Mappers.getMapper(PayWalletRechargeConvert.class);

    @Mapping(target = "totalPrice", expression = "java( payPrice + bonusPrice)")
    PayWalletRechargeDO convert(Long walletId, Integer payPrice, Integer bonusPrice, Long packageId);

    AppPayWalletRechargeCreateRespVO convert(PayWalletRechargeDO bean);

    default PageResult<AppPayWalletRechargeRespVO> convertPage(PageResult<PayWalletRechargeDO> pageResult,
                                                               List<PayOrderDO> payOrderList) {
        PageResult<AppPayWalletRechargeRespVO> voPageResult = BeanUtils.toBean(pageResult, AppPayWalletRechargeRespVO.class);
        Map<Long, PayOrderDO> payOrderMap = CollectionUtils.convertMap(payOrderList, PayOrderDO::getId);
        voPageResult.getList().forEach(recharge -> {
            recharge.setPayChannelName(DictFrameworkUtils.getDictDataLabel(
                    DictTypeConstants.CHANNEL_CODE, recharge.getPayChannelCode()));
            MapUtils.findAndThen(payOrderMap, recharge.getPayOrderId(),
                    order -> recharge.setPayOrderChannelOrderNo(order.getChannelOrderNo()));
        });
        return voPageResult;
    }

}
