package com.hanxing.kxcharge.module.member.api.level;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.member.api.level.dto.MemberLevelRespDTO;
import com.hanxing.kxcharge.module.member.convert.level.MemberLevelConvert;
import com.hanxing.kxcharge.module.member.enums.MemberExperienceBizTypeEnum;
import com.hanxing.kxcharge.module.member.service.level.MemberLevelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.module.member.enums.ErrorCodeConstants.EXPERIENCE_BIZ_NOT_SUPPORT;

/**
 * 会员等级 API 实现类
 *
 * @author owen
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class MemberLevelApiImpl implements MemberLevelApi {

    @Resource
    private MemberLevelService memberLevelService;

    @Override
    public CommonResult<MemberLevelRespDTO> getMemberLevel(Long id) {
        return success(MemberLevelConvert.INSTANCE.convert02(memberLevelService.getLevel(id)));
    }

    @Override
    public CommonResult<Boolean> addExperience(Long userId, Integer experience, Integer bizType, String bizId) {
        MemberExperienceBizTypeEnum bizTypeEnum = MemberExperienceBizTypeEnum.getByType(bizType);
        if (bizTypeEnum == null) {
            throw exception(EXPERIENCE_BIZ_NOT_SUPPORT);
        }
        memberLevelService.addExperience(userId, experience, bizTypeEnum, bizId);
        return success(true);
    }

    @Override
    public CommonResult<Boolean> reduceExperience(Long userId, Integer experience, Integer bizType, String bizId) {
        return addExperience(userId, -experience, bizType, bizId);
    }

}
