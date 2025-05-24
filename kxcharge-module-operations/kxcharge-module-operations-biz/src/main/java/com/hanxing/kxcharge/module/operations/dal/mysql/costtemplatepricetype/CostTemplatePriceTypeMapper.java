package com.hanxing.kxcharge.module.operations.dal.mysql.costtemplatepricetype;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 计费模板价格类别 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface CostTemplatePriceTypeMapper extends BaseMapperX<CostTemplatePriceTypeDO> {

    default PageResult<CostTemplatePriceTypeDO> selectPage(CostTemplatePriceTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CostTemplatePriceTypeDO>()
                .eqIfPresent(CostTemplatePriceTypeDO::getCostTemplateId, reqVO.getCostTemplateId())
                .eqIfPresent(CostTemplatePriceTypeDO::getProtocolVersion, reqVO.getProtocolVersion())
                .eqIfPresent(CostTemplatePriceTypeDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(CostTemplatePriceTypeDO::getIdentification, reqVO.getIdentification())
                .likeIfPresent(CostTemplatePriceTypeDO::getTypeName, reqVO.getTypeName())
                .eqIfPresent(CostTemplatePriceTypeDO::getPricePower, reqVO.getPricePower())
                .eqIfPresent(CostTemplatePriceTypeDO::getPriceService, reqVO.getPriceService())
                .betweenIfPresent(CostTemplatePriceTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(CostTemplatePriceTypeDO::getId));
    }

    default List<CostTemplatePriceTypeDO> selectList(CostTemplatePriceTypeExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CostTemplatePriceTypeDO>()
                .eqIfPresent(CostTemplatePriceTypeDO::getCostTemplateId, reqVO.getCostTemplateId())
                .eqIfPresent(CostTemplatePriceTypeDO::getProtocolVersion, reqVO.getProtocolVersion())
                .eqIfPresent(CostTemplatePriceTypeDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(CostTemplatePriceTypeDO::getIdentification, reqVO.getIdentification())
                .likeIfPresent(CostTemplatePriceTypeDO::getTypeName, reqVO.getTypeName())
                .eqIfPresent(CostTemplatePriceTypeDO::getPricePower, reqVO.getPricePower())
                .eqIfPresent(CostTemplatePriceTypeDO::getPriceService, reqVO.getPriceService())
                .betweenIfPresent(CostTemplatePriceTypeDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(CostTemplatePriceTypeDO::getId));
    }

}
