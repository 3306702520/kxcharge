package com.hanxing.kxcharge.module.pay.convert.demo;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import com.hanxing.kxcharge.module.pay.controller.admin.demo.vo.order.PayDemoOrderRespVO;
import com.hanxing.kxcharge.module.pay.dal.dataobject.demo.PayDemoOrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 示例订单 Convert
 *
 * @author 航兴新能源
 */
@Mapper
public interface PayDemoOrderConvert {

    PayDemoOrderConvert INSTANCE = Mappers.getMapper(PayDemoOrderConvert.class);

    PayDemoOrderDO convert(PayDemoOrderCreateReqVO bean);

    PayDemoOrderRespVO convert(PayDemoOrderDO bean);

    PageResult<PayDemoOrderRespVO> convertPage(PageResult<PayDemoOrderDO> page);

}
