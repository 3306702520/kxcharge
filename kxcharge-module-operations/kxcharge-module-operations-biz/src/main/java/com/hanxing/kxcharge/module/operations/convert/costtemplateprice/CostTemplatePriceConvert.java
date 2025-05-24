package com.hanxing.kxcharge.module.operations.convert.costtemplateprice;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 计费模板价格时段 Convert
 *
 * @author 航兴新能源
 */
@Mapper
public interface CostTemplatePriceConvert {

    CostTemplatePriceConvert INSTANCE = Mappers.getMapper(CostTemplatePriceConvert.class);

    CostTemplatePriceDO convert(CostTemplatePriceCreateReqVO bean);

    CostTemplatePriceDO convert(CostTemplatePriceUpdateReqVO bean);

    CostTemplatePriceRespVO convert(CostTemplatePriceDO bean);

    List<CostTemplatePriceRespVO> convertList(List<CostTemplatePriceDO> list);

    PageResult<CostTemplatePriceRespVO> convertPage(PageResult<CostTemplatePriceDO> page);

    List<CostTemplatePriceExcelVO> convertList02(List<CostTemplatePriceDO> list);


    List<CostTemplatePriceDO> convertList2(List<CostTemplatePriceCreateReqVO> list);

}
