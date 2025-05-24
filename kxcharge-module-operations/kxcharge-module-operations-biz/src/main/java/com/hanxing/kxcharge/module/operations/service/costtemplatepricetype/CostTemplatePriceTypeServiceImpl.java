package com.hanxing.kxcharge.module.operations.service.costtemplatepricetype;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.*;
import com.hanxing.kxcharge.module.operations.convert.costtemplatepricetype.CostTemplatePriceTypeConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplatepricetype.CostTemplatePriceTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.*;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.COST_TEMPLATE_PRICE_TYPE_NOT_EXISTS;

/**
 * 计费模板价格类别 Service 实现类
 *
 * @author kaixin
 */
@Service
@Validated
public class CostTemplatePriceTypeServiceImpl implements CostTemplatePriceTypeService {

    @Resource
    private CostTemplatePriceTypeMapper costTemplatePriceTypeMapper;

    @Override
    public Long createCostTemplatePriceType(CostTemplatePriceTypeCreateReqVO createReqVO) {
        // 插入
        CostTemplatePriceTypeDO costTemplatePriceType = CostTemplatePriceTypeConvert.INSTANCE.convert(createReqVO);
        costTemplatePriceTypeMapper.insert(costTemplatePriceType);
        // 返回
        return costTemplatePriceType.getId();
    }

    @Override
    public void updateCostTemplatePriceType(CostTemplatePriceTypeUpdateReqVO updateReqVO) {
        // 校验存在
        validateCostTemplatePriceTypeExists(updateReqVO.getId());
        // 更新
        CostTemplatePriceTypeDO updateObj = CostTemplatePriceTypeConvert.INSTANCE.convert(updateReqVO);
        costTemplatePriceTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteCostTemplatePriceType(Long id) {
        // 校验存在
        validateCostTemplatePriceTypeExists(id);
        // 删除
        costTemplatePriceTypeMapper.deleteById(id);
    }

    private void validateCostTemplatePriceTypeExists(Long id) {
        if (costTemplatePriceTypeMapper.selectById(id) == null) {
            throw exception(COST_TEMPLATE_PRICE_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public CostTemplatePriceTypeDO getCostTemplatePriceType(Long id) {
        return costTemplatePriceTypeMapper.selectById(id);
    }

    @Override
    public List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return costTemplatePriceTypeMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CostTemplatePriceTypeDO> getCostTemplatePriceTypePage(CostTemplatePriceTypePageReqVO pageReqVO) {
        return costTemplatePriceTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeList(CostTemplatePriceTypeExportReqVO exportReqVO) {
        return costTemplatePriceTypeMapper.selectList(exportReqVO);
    }

    @Override
    public List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeByTemplateId(Long costTemplateId) {
        return costTemplatePriceTypeMapper.selectList(new QueryWrapper<CostTemplatePriceTypeDO>().lambda().eq(CostTemplatePriceTypeDO::getCostTemplateId, costTemplateId));
    }

}
