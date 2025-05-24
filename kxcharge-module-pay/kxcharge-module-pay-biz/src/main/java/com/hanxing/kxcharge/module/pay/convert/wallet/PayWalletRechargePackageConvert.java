package com.hanxing.kxcharge.module.pay.convert.wallet;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageCreateReqVO;
import com.hanxing.kxcharge.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageRespVO;
import com.hanxing.kxcharge.module.pay.controller.admin.wallet.vo.rechargepackage.WalletRechargePackageUpdateReqVO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.wallet.PayWalletRechargePackageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PayWalletRechargePackageConvert {

    PayWalletRechargePackageConvert INSTANCE = Mappers.getMapper(PayWalletRechargePackageConvert.class);

    PayWalletRechargePackageDO convert(WalletRechargePackageCreateReqVO bean);

    PayWalletRechargePackageDO convert(WalletRechargePackageUpdateReqVO bean);

    WalletRechargePackageRespVO convert(PayWalletRechargePackageDO bean);

    List<WalletRechargePackageRespVO> convertList(List<PayWalletRechargePackageDO> list);

    PageResult<WalletRechargePackageRespVO> convertPage(PageResult<PayWalletRechargePackageDO> page);

}
