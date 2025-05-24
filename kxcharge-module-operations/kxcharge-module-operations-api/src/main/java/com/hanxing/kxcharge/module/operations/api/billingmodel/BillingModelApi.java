package com.hanxing.kxcharge.module.operations.api.billingmodel;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.BillingModelRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.BillingModelValidationRespDto;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotifacationEquipBusinessPolicyRespDto;
import com.hanxing.kxcharge.module.operations.enums.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC服务 - 计费模型")
public interface BillingModelApi {

    String PREFIX = ApiConstants.PREFIX + "/billing_model";

    @GetMapping(PREFIX + "/validation")
    CommonResult<BillingModelValidationRespDto> validation(@RequestParam("pileCode") String pileCode, @RequestParam("billingModelNo") String billingModelNo);


    @GetMapping(PREFIX + "/getModel")
    CommonResult<BillingModelRespDto> getModel(@RequestParam("pileCode") String pileCode);


    @GetMapping(PREFIX + "/getPolicy")
    CommonResult<NotifacationEquipBusinessPolicyRespDto> getPolicy(@RequestParam("connectorId") String connectorId);


//    @PostMapping(PREFIX + "/getTransaction")
//    CommonResult<TransactionConfirmationRespDto> getTransaction(@RequestBody TransactionRecordsReqDto dto);


}
