package com.hanxing.kxcharge.module.system.service.sms;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.module.system.controller.admin.sms.vo.channel.SmsChannelPageReqVO;
import com.hanxing.kxcharge.module.system.controller.admin.sms.vo.channel.SmsChannelSaveReqVO;
import com.hanxing.kxcharge.module.system.dal.dataobject.sms.SmsChannelDO;
import com.hanxing.kxcharge.module.system.dal.mysql.sms.SmsChannelMapper;
import com.hanxing.kxcharge.module.system.framework.sms.core.client.SmsClient;
import com.hanxing.kxcharge.module.system.framework.sms.core.client.SmsClientFactory;
import com.hanxing.kxcharge.module.system.framework.sms.core.property.SmsChannelProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.system.enums.ErrorCodeConstants.SMS_CHANNEL_HAS_CHILDREN;
import static com.hanxing.kxcharge.module.system.enums.ErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS;

/**
 * 短信渠道 Service 实现类
 *
 * @author zzf
 */
@Service
@Slf4j
public class SmsChannelServiceImpl implements SmsChannelService {

    @Resource
    private SmsClientFactory smsClientFactory;

    @Resource
    private SmsChannelMapper smsChannelMapper;

    @Resource
    private SmsTemplateService smsTemplateService;

    @Override
    public Long createSmsChannel(SmsChannelSaveReqVO createReqVO) {
        SmsChannelDO channel = BeanUtils.toBean(createReqVO, SmsChannelDO.class);
        smsChannelMapper.insert(channel);
        return channel.getId();
    }

    @Override
    public void updateSmsChannel(SmsChannelSaveReqVO updateReqVO) {
        // 校验存在
        validateSmsChannelExists(updateReqVO.getId());
        // 更新
        SmsChannelDO updateObj = BeanUtils.toBean(updateReqVO, SmsChannelDO.class);
        smsChannelMapper.updateById(updateObj);
    }

    @Override
    public void deleteSmsChannel(Long id) {
        // 校验存在
        validateSmsChannelExists(id);
        // 校验是否有在使用该账号的模版
        if (smsTemplateService.getSmsTemplateCountByChannelId(id) > 0) {
            throw exception(SMS_CHANNEL_HAS_CHILDREN);
        }
        // 删除
        smsChannelMapper.deleteById(id);
    }

    private SmsChannelDO validateSmsChannelExists(Long id) {
        SmsChannelDO channel = smsChannelMapper.selectById(id);
        if (channel == null) {
            throw exception(SMS_CHANNEL_NOT_EXISTS);
        }
        return channel;
    }

    @Override
    public SmsChannelDO getSmsChannel(Long id) {
        return smsChannelMapper.selectById(id);
    }

    @Override
    public List<SmsChannelDO> getSmsChannelList() {
        return smsChannelMapper.selectList();
    }

    @Override
    public PageResult<SmsChannelDO> getSmsChannelPage(SmsChannelPageReqVO pageReqVO) {
        return smsChannelMapper.selectPage(pageReqVO);
    }

    @Override
    public SmsClient getSmsClient(Long id) {
        SmsChannelDO channel = smsChannelMapper.selectById(id);
        SmsChannelProperties properties = BeanUtils.toBean(channel, SmsChannelProperties.class);
        return smsClientFactory.createOrUpdateSmsClient(properties);
    }

    @Override
    public SmsClient getSmsClient(String code) {
        return smsClientFactory.getSmsClient(code);
    }

}
