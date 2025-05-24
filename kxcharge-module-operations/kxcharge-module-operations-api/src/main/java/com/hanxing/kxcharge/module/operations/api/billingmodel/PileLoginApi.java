package com.hanxing.kxcharge.module.operations.api.billingmodel;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.PileLoginReqDto;
import com.hanxing.kxcharge.module.operations.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC服务 - 充电桩登陆")
public interface PileLoginApi {

    String PREFIX = ApiConstants.PREFIX + "/pileLogin";

    @PostMapping(PREFIX)
    @Operation(summary = "充电桩登录")
    CommonResult<Boolean> pileLogin(@RequestBody PileLoginReqDto pileLoginReqDto);


}
