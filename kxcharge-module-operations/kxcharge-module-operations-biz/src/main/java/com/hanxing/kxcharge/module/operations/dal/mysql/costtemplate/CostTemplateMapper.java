package com.hanxing.kxcharge.module.operations.dal.mysql.costtemplate;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 计费模板 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface CostTemplateMapper extends BaseMapperX<CostTemplateDO> {

    default PageResult<CostTemplateDO> selectPage(CostTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CostTemplateDO>()
                .likeIfPresent(CostTemplateDO::getTemplateName, reqVO.getTemplateName())
                .betweenIfPresent(CostTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CostTemplateDO::getId));
    }

    default List<CostTemplateDO> selectList(CostTemplateExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CostTemplateDO>()
                .likeIfPresent(CostTemplateDO::getTemplateName, reqVO.getTemplateName())
                .betweenIfPresent(CostTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CostTemplateDO::getId));
    }

}
