package com.hanxing.kxcharge.module.member.controller.app.point;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.framework.security.core.annotations.PreAuthenticated;
import com.hanxing.kxcharge.module.member.controller.app.point.vo.AppMemberPointRecordPageReqVO;
import com.hanxing.kxcharge.module.member.controller.app.point.vo.AppMemberPointRecordRespVO;
import com.hanxing.kxcharge.module.member.convert.point.MemberPointRecordConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.point.MemberPointRecordDO;
import com.hanxing.kxcharge.module.member.service.point.MemberPointRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 签到记录")
@RestController
@RequestMapping("/member/point/record")
@Validated
public class AppMemberPointRecordController {

    @Resource
    private MemberPointRecordService pointRecordService;

    @GetMapping("/page")
    @Operation(summary = "获得用户积分记录分页")
    @PreAuthenticated
    public CommonResult<PageResult<AppMemberPointRecordRespVO>> getPointRecordPage(
            @Valid AppMemberPointRecordPageReqVO pageReqVO) {
        PageResult<MemberPointRecordDO> pageResult = pointRecordService.getPointRecordPage(getLoginUserId(), pageReqVO);
        return success(BeanUtils.toBean(pageResult, AppMemberPointRecordRespVO.class));
    }

}
