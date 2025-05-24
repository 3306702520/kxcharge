package com.hanxing.kxcharge.module.system.api.permission;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.system.service.permission.RoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class RoleApiImpl implements RoleApi {

    @Resource
    private RoleService roleService;

    @Override
    public CommonResult<Boolean> validRoleList(Collection<Long> ids) {
        roleService.validateRoleList(ids);
        return success(true);
    }
}
