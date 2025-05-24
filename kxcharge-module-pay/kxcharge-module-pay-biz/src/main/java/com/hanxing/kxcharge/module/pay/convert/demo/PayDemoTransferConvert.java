package com.hanxing.kxcharge.module.pay.convert.demo;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import com.hanxing.kxcharge.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferRespVO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.demo.PayDemoTransferDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author jason
 */
@Mapper
public interface PayDemoTransferConvert {

    PayDemoTransferConvert INSTANCE = Mappers.getMapper(PayDemoTransferConvert.class);

    PayDemoTransferDO convert(PayDemoTransferCreateReqVO bean);

    PageResult<PayDemoTransferRespVO> convertPage(PageResult<PayDemoTransferDO> pageResult);
}
