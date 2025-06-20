package com.hanxing.kxcharge.module.member.controller.admin.config;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.member.controller.admin.config.vo.MemberConfigRespVO;
import com.hanxing.kxcharge.module.member.controller.admin.config.vo.MemberConfigSaveReqVO;
import com.hanxing.kxcharge.module.member.convert.config.MemberConfigConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.config.MemberConfigDO;
import com.hanxing.kxcharge.module.member.service.config.MemberConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员设置")
@RestController
@RequestMapping("/member/config")
@Validated
public class MemberConfigController {

    @Resource
    private MemberConfigService memberConfigService;

    @PutMapping("/save")
    @Operation(summary = "保存会员配置")
    @PreAuthorize("@ss.hasPermission('member:config:save')")
    public CommonResult<Boolean> saveConfig(@Valid @RequestBody MemberConfigSaveReqVO saveReqVO) {
        memberConfigService.saveConfig(saveReqVO);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得会员配置")
    @PreAuthorize("@ss.hasPermission('member:config:query')")
    public CommonResult<MemberConfigRespVO> getConfig() {
        MemberConfigDO config = memberConfigService.getConfig();
        return success(MemberConfigConvert.INSTANCE.convert(config));
    }

}
