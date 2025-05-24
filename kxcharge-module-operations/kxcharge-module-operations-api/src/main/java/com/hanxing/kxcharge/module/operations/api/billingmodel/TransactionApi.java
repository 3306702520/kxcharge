package com.hanxing.kxcharge.module.operations.api.billingmodel;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionConfirmationRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.TransactionRecordsReqDto;
import com.hanxing.kxcharge.module.operations.enums.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ApiConstants.NAME)//,url = "localhost:48088"
@Tag(name = "RPC服务 - 交易")
public interface TransactionApi {


    String PREFIX = ApiConstants.PREFIX + "/transaction";


    @PostMapping(PREFIX + "/record")
    CommonResult<TransactionConfirmationRespDto> record(@RequestBody TransactionRecordsReqDto dto);


}
