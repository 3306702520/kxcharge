package com.hanxing.kxcharge.module.member.api.plate;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.member.api.plate.dto.MemberPlateReqDTO;
import com.hanxing.kxcharge.module.member.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 车辆信息")
public interface MemberPlateApi {

    String PREFIX = ApiConstants.PREFIX + "/plate";


    @PostMapping(PREFIX + "/addPlateNumber")
    @Operation(summary = "新增会员对应的车牌号")
    @Parameters({
            @Parameter(name = "userId", description = "会员编号", required = true, example = "1024"),
            @Parameter(name = "plateNumber", description = "车牌号", required = true, example = "100")
    })
    CommonResult<Boolean> addPlateNumber(@RequestParam("userId") Long userId,
                                         @RequestParam("plateNumber") String plateNumber);


    @PostMapping(PREFIX + "/chargeOrder")
    @Operation(summary = "处理充电订单信息")
    CommonResult<Boolean> chargeOrder(@RequestBody MemberPlateReqDTO memberPlateReqDTO);

}
