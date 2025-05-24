package com.hanxing.kxcharge.module.operations.dal.mysql.costtemplateprice;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 计费模板价格时段 Mapper
 *
 * @author 航兴新能源
 */
@Mapper
public interface CostTemplatePriceMapper extends BaseMapperX<CostTemplatePriceDO> {

    default PageResult<CostTemplatePriceDO> selectPage(CostTemplatePricePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<CostTemplatePriceDO>()
                .eqIfPresent(CostTemplatePriceDO::getCostTemplateId, reqVO.getCostTemplateId())
                .eqIfPresent(CostTemplatePriceDO::getProtocolVersion, reqVO.getProtocolVersion())
                .eqIfPresent(CostTemplatePriceDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(CostTemplatePriceDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(CostTemplatePriceDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(CostTemplatePriceDO::getStartTimeStr, reqVO.getStartTimeStr())
                .eqIfPresent(CostTemplatePriceDO::getEndTimeStr, reqVO.getEndTimeStr())
                .eqIfPresent(CostTemplatePriceDO::getPriceTypeId, reqVO.getPriceTypeId())
                .betweenIfPresent(CostTemplatePriceDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(CostTemplatePriceDO::getStartTime));
    }

    default List<CostTemplatePriceDO> selectList(CostTemplatePriceExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<CostTemplatePriceDO>()
                .eqIfPresent(CostTemplatePriceDO::getCostTemplateId, reqVO.getCostTemplateId())
                .eqIfPresent(CostTemplatePriceDO::getProtocolVersion, reqVO.getProtocolVersion())
                .eqIfPresent(CostTemplatePriceDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(CostTemplatePriceDO::getStartTime, reqVO.getStartTime())
                .eqIfPresent(CostTemplatePriceDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(CostTemplatePriceDO::getStartTimeStr, reqVO.getStartTimeStr())
                .eqIfPresent(CostTemplatePriceDO::getEndTimeStr, reqVO.getEndTimeStr())
                .eqIfPresent(CostTemplatePriceDO::getPriceTypeId, reqVO.getPriceTypeId())
                .betweenIfPresent(CostTemplatePriceDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(CostTemplatePriceDO::getStartTime));
    }

}
