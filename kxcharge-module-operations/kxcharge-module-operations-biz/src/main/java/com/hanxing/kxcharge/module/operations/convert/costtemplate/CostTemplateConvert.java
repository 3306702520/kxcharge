package com.hanxing.kxcharge.module.operations.convert.costtemplate;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 计费模板 Convert
 *
 * @author kaixin
 */
@Mapper
public interface CostTemplateConvert {

    CostTemplateConvert INSTANCE = Mappers.getMapper(CostTemplateConvert.class);

    CostTemplateDO convert(CostTemplateCreateReqVO bean);

    CostTemplateDO convert(CostTemplateUpdateReqVO bean);

    CostTemplateRespVO convert(CostTemplateDO bean);

    List<CostTemplateRespVO> convertList(List<CostTemplateDO> list);

    PageResult<CostTemplateRespVO> convertPage(PageResult<CostTemplateDO> page);

    List<CostTemplateExcelVO> convertList02(List<CostTemplateDO> list);

}
