package com.hanxing.kxcharge.module.operations.convert.order;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.order.vo.*;
import com.hanxing.kxcharge.module.operations.controller.app.order.vo.AppOrderRespVO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单管理 Convert
 *
 * @author 小黑
 */
@Mapper
public interface OrderConvert {

    OrderConvert INSTANCE = Mappers.getMapper(OrderConvert.class);

    OrderDO convert(OrderCreateReqVO bean);

    OrderDO convert(OrderUpdateReqVO bean);

    OrderRespVO convert(OrderDO bean);

    List<OrderRespVO> convertList(List<OrderDO> list);

    PageResult<OrderRespVO> convertPage(PageResult<OrderDO> page);

    List<OrderExcelVO> convertList02(List<OrderDO> list);

    PageResult<AppOrderRespVO> convertPage01(PageResult<OrderDO> pageResult);
}
