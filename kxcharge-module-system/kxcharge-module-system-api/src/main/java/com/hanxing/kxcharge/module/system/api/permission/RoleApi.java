package com.hanxing.kxcharge.module.system.api.permission;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(name = ApiConstants.NAME) // TODO fallbackFactory =
@Tag(name = "RPC 服务 - 角色")
public interface RoleApi {

    String PREFIX = ApiConstants.PREFIX + "/role";

    @GetMapping(PREFIX + "/valid")
    @Operation(summary = "校验角色是否合法")
    @Parameter(name = "ids", description = "角色编号数组", example = "1,2", required = true)
    CommonResult<Boolean> validRoleList(@RequestParam("ids") Collection<Long> ids);

}