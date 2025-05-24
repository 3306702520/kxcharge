package com.hanxing.kxcharge.module.system.api.logger;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.hanxing.kxcharge.module.system.service.logger.LoginLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class LoginLogApiImpl implements LoginLogApi {

    @Resource
    private LoginLogService loginLogService;

    @Override
    public CommonResult<Boolean> createLoginLog(LoginLogCreateReqDTO reqDTO) {
        loginLogService.createLoginLog(reqDTO);
        return success(true);
    }

}
