package com.hanxing.kxcharge.module.pay.dal.mysql.notify;

import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayNotifyLogMapper extends BaseMapperX<PayNotifyLogDO> {

    default List<PayNotifyLogDO> selectListByTaskId(Long taskId) {
        return selectList(PayNotifyLogDO::getTaskId, taskId);
    }

}
