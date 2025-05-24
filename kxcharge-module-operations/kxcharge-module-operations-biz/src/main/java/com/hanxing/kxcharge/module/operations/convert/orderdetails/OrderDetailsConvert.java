package com.hanxing.kxcharge.module.operations.convert.orderdetails;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.orderdetails.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetailsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 订单详情管理 Convert
 *
 * @author kaixin
 */
@Mapper
public interface OrderDetailsConvert {

    OrderDetailsConvert INSTANCE = Mappers.getMapper(OrderDetailsConvert.class);

    OrderDetailsDO convert(OrderDetailsCreateReqVO bean);

    OrderDetailsDO convert(OrderDetailsUpdateReqVO bean);

    OrderDetailsRespVO convert(OrderDetailsDO bean);

    List<OrderDetailsRespVO> convertList(List<OrderDetailsDO> list);

    PageResult<OrderDetailsRespVO> convertPage(PageResult<OrderDetailsDO> page);

    List<OrderDetailsExcelVO> convertList02(List<OrderDetailsDO> list);

}
