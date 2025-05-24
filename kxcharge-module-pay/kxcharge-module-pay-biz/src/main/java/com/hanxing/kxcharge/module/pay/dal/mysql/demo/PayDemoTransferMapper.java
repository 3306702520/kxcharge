package com.hanxing.kxcharge.module.pay.dal.mysql.demo;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.pay.dal.dataobject.demo.PayDemoTransferDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayDemoTransferMapper extends BaseMapperX<PayDemoTransferDO> {

    default  PageResult<PayDemoTransferDO> selectPage(PageParam pageParam){
        return selectPage(pageParam, new LambdaQueryWrapperX<PayDemoTransferDO>()
                .orderByDesc(PayDemoTransferDO::getId));
    }
}