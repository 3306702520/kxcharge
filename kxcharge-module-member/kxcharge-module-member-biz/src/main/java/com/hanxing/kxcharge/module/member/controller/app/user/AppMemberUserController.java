package com.hanxing.kxcharge.module.member.controller.app.user;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.security.core.annotations.PreAuthenticated;
import com.hanxing.kxcharge.module.member.controller.app.user.vo.*;
import com.hanxing.kxcharge.module.member.convert.user.MemberUserConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.level.MemberLevelDO;
import com.hanxing.kxcharge.module.member.dal.dataobject.user.MemberUserDO;
import com.hanxing.kxcharge.module.member.service.level.MemberLevelService;
import com.hanxing.kxcharge.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 用户个人中心")
@RestController
@RequestMapping("/member/user")
@Validated
@Slf4j
public class AppMemberUserController {

    @Resource
    private MemberUserService userService;
    @Resource
    private MemberLevelService levelService;

    @GetMapping("/get")
    @Operation(summary = "获得基本信息")
    @PreAuthenticated
    public CommonResult<AppMemberUserInfoRespVO> getUserInfo() {
        MemberUserDO user = userService.getUser(getLoginUserId());
        MemberLevelDO level = levelService.getLevel(user.getLevelId());
        return success(MemberUserConvert.INSTANCE.convert(user, level));
    }

    @PutMapping("/update")
    @Operation(summary = "修改基本信息")
    @PreAuthenticated
    public CommonResult<Boolean> updateUser(@RequestBody @Valid AppMemberUserUpdateReqVO reqVO) {
        userService.updateUser(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-mobile")
    @Operation(summary = "修改用户手机")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserMobile(@RequestBody @Valid AppMemberUserUpdateMobileReqVO reqVO) {
        userService.updateUserMobile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-mobile-by-weixin")
    @Operation(summary = "基于微信小程序的授权码，修改用户手机")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserMobileByWeixin(@RequestBody @Valid AppMemberUserUpdateMobileByWeixinReqVO reqVO) {
        userService.updateUserMobileByWeixin(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户密码", description = "用户修改密码时使用")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserPassword(@RequestBody @Valid AppMemberUserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/reset-password")
    @Operation(summary = "重置密码", description = "用户忘记密码时使用")
    public CommonResult<Boolean> resetUserPassword(@RequestBody @Valid AppMemberUserResetPasswordReqVO reqVO) {
        userService.resetUserPassword(reqVO);
        return success(true);
    }

}

