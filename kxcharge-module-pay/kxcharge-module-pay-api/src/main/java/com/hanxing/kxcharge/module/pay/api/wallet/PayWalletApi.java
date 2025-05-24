package com.hanxing.kxcharge.module.pay.api.wallet;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.pay.api.wallet.dto.ChargeOrderReqDTO;
import com.hanxing.kxcharge.module.pay.api.wallet.dto.ChargeOrderRespDTO;
import com.hanxing.kxcharge.module.pay.api.wallet.dto.PayWalletRespDTO;
import com.hanxing.kxcharge.module.pay.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 钱包")
public interface PayWalletApi {
    String PREFIX = ApiConstants.PREFIX + "/wallet";

    /**
     * 根据userId获取用户钱包(会员)
     * @param userId
     * @return
     */
    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获取钱包")
    CommonResult<PayWalletRespDTO> getWallet(@RequestParam(value = "id") Long id);

    @PostMapping(PREFIX + "/chargeOrder")
    @Operation(summary = "钱包处理充电订单")
    CommonResult<ChargeOrderRespDTO> chargeOrder(@RequestBody ChargeOrderReqDTO chargeOrderReqDTO);

}
