package com.hanxing.kxcharge.module.operations.api.station;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.station.dto.StationReqDto;
import com.hanxing.kxcharge.module.operations.enums.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC服务 - 站点信息")
public interface StationApi {


    String PREFIX = ApiConstants.PREFIX + "/station";


    @GetMapping(PREFIX + "/info")
    CommonResult<StationReqDto> stationInfo(@RequestParam("id") Long id);


}
