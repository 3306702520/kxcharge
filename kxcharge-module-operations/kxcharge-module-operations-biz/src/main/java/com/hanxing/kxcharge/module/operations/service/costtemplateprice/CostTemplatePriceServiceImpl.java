package com.hanxing.kxcharge.module.operations.service.costtemplateprice;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.*;
import com.hanxing.kxcharge.module.operations.convert.costtemplateprice.CostTemplatePriceConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplateprice.CostTemplatePriceMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.*;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.COST_TEMPLATE_PRICE_NOT_EXISTS;

/**
 * 计费模板价格时段 Service 实现类
 *
 * @author 航兴新能源
 */
@Service
@Validated
public class CostTemplatePriceServiceImpl implements CostTemplatePriceService {

    @Resource
    private CostTemplatePriceMapper costTemplatePriceMapper;

    @Override
    public Long createCostTemplatePrice(CostTemplatePriceCreateReqVO createReqVO) {
        // 插入
        CostTemplatePriceDO costTemplatePrice = CostTemplatePriceConvert.INSTANCE.convert(createReqVO);
        costTemplatePriceMapper.insert(costTemplatePrice);
        // 返回
        return costTemplatePrice.getId();
    }

    @Override
    public void updateCostTemplatePrice(CostTemplatePriceUpdateReqVO updateReqVO) {
        // 校验存在
        validateCostTemplatePriceExists(updateReqVO.getId());
        // 更新
        CostTemplatePriceDO updateObj = CostTemplatePriceConvert.INSTANCE.convert(updateReqVO);
        costTemplatePriceMapper.updateById(updateObj);
    }

    @Override
    public void deleteCostTemplatePrice(Long id) {
        // 校验存在
        validateCostTemplatePriceExists(id);
        // 删除
        costTemplatePriceMapper.deleteById(id);
    }

    private void validateCostTemplatePriceExists(Long id) {
        if (costTemplatePriceMapper.selectById(id) == null) {
            throw exception(COST_TEMPLATE_PRICE_NOT_EXISTS);
        }
    }

    @Override
    public CostTemplatePriceDO getCostTemplatePrice(Long id) {
        return costTemplatePriceMapper.selectById(id);
    }

    @Override
    public List<CostTemplatePriceDO> getCostTemplatePriceList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return costTemplatePriceMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CostTemplatePriceDO> getCostTemplatePricePage(CostTemplatePricePageReqVO pageReqVO) {
        return costTemplatePriceMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CostTemplatePriceDO> getCostTemplatePriceList(CostTemplatePriceExportReqVO exportReqVO) {
        return costTemplatePriceMapper.selectList(exportReqVO);
    }

    @Override
    public List<CostTemplatePriceDO> getCostTemplatePriceByTemplateId(Long operatorId) {
        return costTemplatePriceMapper.selectList(new QueryWrapper<CostTemplatePriceDO>().lambda()
                .eq(CostTemplatePriceDO::getCostTemplateId, operatorId)
                .orderByAsc(CostTemplatePriceDO::getStartTime)
        );
    }

}
