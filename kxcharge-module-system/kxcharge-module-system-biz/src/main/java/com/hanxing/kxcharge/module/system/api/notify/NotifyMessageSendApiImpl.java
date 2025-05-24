package com.hanxing.kxcharge.module.system.api.notify;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import com.hanxing.kxcharge.module.system.service.notify.NotifySendService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private NotifySendService notifySendService;

    @Override
    public CommonResult<Long> sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return success(notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

    @Override
    public CommonResult<Long> sendSingleMessageToMember(NotifySendSingleToUserReqDTO reqDTO) {
        return success(notifySendService.sendSingleNotifyToMember(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams()));
    }

}
