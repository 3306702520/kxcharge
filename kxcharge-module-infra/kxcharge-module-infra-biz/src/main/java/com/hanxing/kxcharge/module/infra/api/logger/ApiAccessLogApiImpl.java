package com.hanxing.kxcharge.module.infra.api.logger;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.infra.api.logger.dto.ApiAccessLogCreateReqDTO;
import com.hanxing.kxcharge.module.infra.service.logger.ApiAccessLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @Override
    public CommonResult<Boolean> createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        apiAccessLogService.createApiAccessLog(createDTO);
        return success(true);
    }

}
