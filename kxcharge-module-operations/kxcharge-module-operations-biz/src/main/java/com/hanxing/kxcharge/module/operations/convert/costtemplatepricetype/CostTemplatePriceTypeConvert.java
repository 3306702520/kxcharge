package com.hanxing.kxcharge.module.operations.convert.costtemplatepricetype;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 计费模板价格类别 Convert
 *
 * @author kaixin
 */
@Mapper
public interface CostTemplatePriceTypeConvert {

    CostTemplatePriceTypeConvert INSTANCE = Mappers.getMapper(CostTemplatePriceTypeConvert.class);

    CostTemplatePriceTypeDO convert(CostTemplatePriceTypeCreateReqVO bean);

    CostTemplatePriceTypeDO convert(CostTemplatePriceTypeUpdateReqVO bean);

    CostTemplatePriceTypeRespVO convert(CostTemplatePriceTypeDO bean);

    List<CostTemplatePriceTypeRespVO> convertList(List<CostTemplatePriceTypeDO> list);

    PageResult<CostTemplatePriceTypeRespVO> convertPage(PageResult<CostTemplatePriceTypeDO> page);

    List<CostTemplatePriceTypeExcelVO> convertList02(List<CostTemplatePriceTypeDO> list);


    List<CostTemplatePriceTypeDO> convertList2(List<CostTemplatePriceTypeCreateReqVO> list);

}
