package com.hanxing.kxcharge.module.member.api.address;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.member.api.address.dto.MemberAddressRespDTO;
import com.hanxing.kxcharge.module.member.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO fallbackFactory =
@Tag(name = "RPC 服务 - 用户收件地址")
public interface MemberAddressApi {

    String PREFIX = ApiConstants.PREFIX + "/address";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "获得用户收件地址")
    @Parameters({
            @Parameter(name = "id", description = "收件地址编号", required = true, example = "1024"),
            @Parameter(name = "userId", description = "用户编号", required = true, example = "2048"),
    })
    CommonResult<MemberAddressRespDTO> getAddress(@RequestParam("id") Long id,
                                                  @RequestParam("userId") Long userId);

    @GetMapping(PREFIX + "/get-default")
    @Operation(summary = "获得用户默认收件地址")
    @Parameter(name = "userId", description = "用户编号", required = true, example = "2048")
    CommonResult<MemberAddressRespDTO> getDefaultAddress(@RequestParam("userId") Long userId);

}
