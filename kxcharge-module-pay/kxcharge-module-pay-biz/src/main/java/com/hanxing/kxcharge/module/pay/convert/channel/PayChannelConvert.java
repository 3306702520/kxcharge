package com.hanxing.kxcharge.module.pay.convert.channel;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.pay.controller.admin.channel.vo.PayChannelCreateReqVO;
import com.hanxing.kxcharge.module.pay.controller.admin.channel.vo.PayChannelRespVO;
import com.hanxing.kxcharge.module.pay.controller.admin.channel.vo.PayChannelUpdateReqVO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.channel.PayChannelDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayChannelConvert {

    PayChannelConvert INSTANCE = Mappers.getMapper(PayChannelConvert.class);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelCreateReqVO bean);

    @Mapping(target = "config",ignore = true)
    PayChannelDO convert(PayChannelUpdateReqVO bean);

    @Mapping(target = "config",expression = "java(com.hanxing.kxcharge.framework.common.util.json.JsonUtils.toJsonString(bean.getConfig()))")
    PayChannelRespVO convert(PayChannelDO bean);

    PageResult<PayChannelRespVO> convertPage(PageResult<PayChannelDO> page);

}
