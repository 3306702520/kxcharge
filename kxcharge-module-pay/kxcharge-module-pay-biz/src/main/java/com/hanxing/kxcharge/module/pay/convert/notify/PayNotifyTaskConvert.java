package com.hanxing.kxcharge.module.pay.convert.notify;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.collection.MapUtils;
import com.hanxing.kxcharge.module.pay.controller.admin.notify.vo.PayNotifyTaskDetailRespVO;
import com.hanxing.kxcharge.module.pay.controller.admin.notify.vo.PayNotifyTaskRespVO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.app.PayAppDO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.notify.PayNotifyTaskDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 支付通知 Convert
 *
 * @author 航兴新能源
 */
@Mapper
public interface PayNotifyTaskConvert {

    PayNotifyTaskConvert INSTANCE = Mappers.getMapper(PayNotifyTaskConvert.class);

    PayNotifyTaskRespVO convert(PayNotifyTaskDO bean);

    default PageResult<PayNotifyTaskRespVO> convertPage(PageResult<PayNotifyTaskDO> page, Map<Long, PayAppDO> appMap){
        PageResult<PayNotifyTaskRespVO> result = convertPage(page);
        result.getList().forEach(order -> MapUtils.findAndThen(appMap, order.getAppId(), app -> order.setAppName(app.getName())));
        return result;
    }
    PageResult<PayNotifyTaskRespVO> convertPage(PageResult<PayNotifyTaskDO> page);

    default PayNotifyTaskDetailRespVO convert(PayNotifyTaskDO task, PayAppDO app, List<PayNotifyLogDO> logs) {
        PayNotifyTaskDetailRespVO respVO = convert(task, logs);
        if (app != null) {
            respVO.setAppName(app.getName());
        }
        return respVO;
    }
    PayNotifyTaskDetailRespVO convert(PayNotifyTaskDO task, List<PayNotifyLogDO> logs);
}
