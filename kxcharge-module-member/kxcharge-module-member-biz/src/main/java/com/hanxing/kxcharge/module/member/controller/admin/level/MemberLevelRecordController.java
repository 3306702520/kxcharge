package com.hanxing.kxcharge.module.member.controller.admin.level;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.member.controller.admin.level.vo.record.MemberLevelRecordPageReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.level.vo.record.MemberLevelRecordRespVO;
import com.hanxing.kxcharge.module.member.convert.level.MemberLevelRecordConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.level.MemberLevelRecordDO;
import com.hanxing.kxcharge.module.member.service.level.MemberLevelRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 会员等级记录")
@RestController
@RequestMapping("/member/level-record")
@Validated
public class MemberLevelRecordController {

    @Resource
    private MemberLevelRecordService levelLogService;

    @GetMapping("/get")
    @Operation(summary = "获得会员等级记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:level-record:query')")
    public CommonResult<MemberLevelRecordRespVO> getLevelRecord(@RequestParam("id") Long id) {
        MemberLevelRecordDO levelLog = levelLogService.getLevelRecord(id);
        return success(MemberLevelRecordConvert.INSTANCE.convert(levelLog));
    }

    @GetMapping("/page")
    @Operation(summary = "获得会员等级记录分页")
    @PreAuthorize("@ss.hasPermission('member:level-record:query')")
    public CommonResult<PageResult<MemberLevelRecordRespVO>> getLevelRecordPage(
            @Valid MemberLevelRecordPageReqVO pageVO) {
        PageResult<MemberLevelRecordDO> pageResult = levelLogService.getLevelRecordPage(pageVO);
        return success(MemberLevelRecordConvert.INSTANCE.convertPage(pageResult));
    }

}
