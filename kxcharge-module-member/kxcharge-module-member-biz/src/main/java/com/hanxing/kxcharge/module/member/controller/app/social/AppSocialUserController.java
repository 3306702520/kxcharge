package com.hanxing.kxcharge.module.member.controller.app.social;

import cn.hutool.core.codec.Base64;
import com.hanxing.kxcharge.framework.common.enums.UserTypeEnum;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.framework.security.core.annotations.PreAuthenticated;
import com.hanxing.kxcharge.module.member.controller.app.social.vo.*;
import com.hanxing.kxcharge.module.system.api.social.SocialClientApi;
import com.hanxing.kxcharge.module.system.api.social.SocialUserApi;
import com.hanxing.kxcharge.module.system.api.social.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 社交用户")
@RestController
@RequestMapping("/member/social-user")
@Validated
public class AppSocialUserController {

    @Resource
    private SocialUserApi socialUserApi;
    @Resource
    private SocialClientApi socialClientApi;

    @PostMapping("/bind")
    @Operation(summary = "社交绑定，使用 code 授权码")
    public CommonResult<String> socialBind(@RequestBody @Valid AppSocialUserBindReqVO reqVO) {
        SocialUserBindReqDTO reqDTO = new SocialUserBindReqDTO(getLoginUserId(), UserTypeEnum.MEMBER.getValue(),
                reqVO.getType(), reqVO.getCode(), reqVO.getState());
        String openid = socialUserApi.bindSocialUser(reqDTO).getCheckedData();
        return success(openid);
    }

    @DeleteMapping("/unbind")
    @Operation(summary = "取消社交绑定")
    @PreAuthenticated
    public CommonResult<Boolean> socialUnbind(@RequestBody AppSocialUserUnbindReqVO reqVO) {
        SocialUserUnbindReqDTO reqDTO = new SocialUserUnbindReqDTO(getLoginUserId(), UserTypeEnum.MEMBER.getValue(),
                reqVO.getType(), reqVO.getOpenid());
        socialUserApi.unbindSocialUser(reqDTO).checkError();
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得社交用户")
    @Parameter(name = "type", description = "社交平台的类型，参见 SocialTypeEnum 枚举值", required = true, example = "10")
    @PreAuthenticated
    public CommonResult<AppSocialUserRespVO> getSocialUser(@RequestParam("type") Integer type) {
        SocialUserRespDTO socialUser = socialUserApi.getSocialUserByUserId(UserTypeEnum.MEMBER.getValue(), getLoginUserId(), type).getCheckedData();
        return success(BeanUtils.toBean(socialUser, AppSocialUserRespVO.class));
    }

    @PostMapping("/wxa-qrcode")
    @Operation(summary = "获得微信小程序码(base64 image)")
    public CommonResult<String> getWxaQrcode(@RequestBody @Valid AppSocialWxaQrcodeReqVO reqVO) {
        byte[] wxQrcode = socialClientApi.getWxaQrcode(BeanUtils.toBean(reqVO, SocialWxQrcodeReqDTO.class)).getCheckedData();
        return success(Base64.encode(wxQrcode));
    }

    @GetMapping("/get-subscribe-template-list")
    @Operation(summary = "获得微信小程订阅模板列表")
    public CommonResult<List<AppSocialWxaSubscribeTemplateRespVO>> getSubscribeTemplateList() {
        List<SocialWxaSubscribeTemplateRespDTO> template = socialClientApi.getWxaSubscribeTemplateList(UserTypeEnum.MEMBER.getValue()).getCheckedData();
        return success(BeanUtils.toBean(template, AppSocialWxaSubscribeTemplateRespVO.class));
    }

}
