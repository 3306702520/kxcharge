package com.hanxing.kxcharge.module.member.controller.admin.signin;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.member.controller.admin.signin.vo.record.MemberSignInRecordPageReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.signin.vo.record.MemberSignInRecordRespVO;
import com.hanxing.kxcharge.module.member.convert.signin.MemberSignInRecordConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.signin.MemberSignInRecordDO;
import com.hanxing.kxcharge.module.member.dal.dataobject.user.MemberUserDO;
import com.hanxing.kxcharge.module.member.service.signin.MemberSignInRecordService;
import com.hanxing.kxcharge.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 签到记录")
@RestController
@RequestMapping("/member/sign-in/record")
@Validated
public class MemberSignInRecordController {

    @Resource
    private MemberSignInRecordService signInRecordService;

    @Resource
    private MemberUserService memberUserService;

    @GetMapping("/page")
    @Operation(summary = "获得签到记录分页")
    @PreAuthorize("@ss.hasPermission('point:sign-in-record:query')")
    public CommonResult<PageResult<MemberSignInRecordRespVO>> getSignInRecordPage(@Valid MemberSignInRecordPageReqVO pageVO) {
        // 执行分页查询
        PageResult<MemberSignInRecordDO> pageResult = signInRecordService.getSignInRecordPage(pageVO);
        if (CollectionUtils.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // 拼接结果返回
        List<MemberUserDO> users = memberUserService.getUserList(
                convertSet(pageResult.getList(), MemberSignInRecordDO::getUserId));
        return success(MemberSignInRecordConvert.INSTANCE.convertPage(pageResult, users));
    }
}
