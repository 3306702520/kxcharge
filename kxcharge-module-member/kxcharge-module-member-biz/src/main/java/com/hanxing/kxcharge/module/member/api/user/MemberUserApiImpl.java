package com.hanxing.kxcharge.module.member.api.user;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.member.api.user.dto.MemberUserRespDTO;
import com.hanxing.kxcharge.module.member.convert.user.MemberUserConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.user.MemberUserDO;
import com.hanxing.kxcharge.module.member.service.user.MemberUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.module.member.enums.ErrorCodeConstants.USER_MOBILE_NOT_EXISTS;

/**
 * 会员用户的 API 实现类
 *
 * @author 航兴新能源
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class MemberUserApiImpl implements MemberUserApi {

    @Resource
    private MemberUserService userService;

    @Override
    public CommonResult<MemberUserRespDTO> getUser(Long id) {
        MemberUserDO user = userService.getUser(id);
        return success(MemberUserConvert.INSTANCE.convert2(user));
    }

    @Override
    public CommonResult<List<MemberUserRespDTO>> getUserList(Collection<Long> ids) {
        return success(MemberUserConvert.INSTANCE.convertList2(userService.getUserList(ids)));
    }

    @Override
    public CommonResult<List<MemberUserRespDTO>> getUserListByNickname(String nickname) {
        return success(MemberUserConvert.INSTANCE.convertList2(userService.getUserListByNickname(nickname)));
    }

    @Override
    public CommonResult<MemberUserRespDTO> getUserByMobile(String mobile) {
        return success(MemberUserConvert.INSTANCE.convert2(userService.getUserByMobile(mobile)));
    }

    @Override
    public CommonResult<Boolean> validateUser(Long id) {
        MemberUserDO user = userService.getUser(id);
        if (user == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }
        return success(true);
    }

}
