package com.hanxing.kxcharge.module.system.api.operator;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.system.api.operator.dto.OperatorRespDTO;
import com.hanxing.kxcharge.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 运营商数据")
public interface OperatorApi {


    String PREFIX = ApiConstants.PREFIX + "/operator";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "校验字典数据们是否有效")
    CommonResult<OperatorRespDTO> getOperator(@RequestParam("id") Long id);
}
