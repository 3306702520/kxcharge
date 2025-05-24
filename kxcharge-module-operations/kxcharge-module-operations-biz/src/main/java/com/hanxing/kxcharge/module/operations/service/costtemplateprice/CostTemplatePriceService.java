package com.hanxing.kxcharge.module.operations.service.costtemplateprice;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 计费模板价格时段 Service 接口
 *
 * @author 航兴新能源
 */
public interface CostTemplatePriceService {

    /**
     * 创建计费模板价格时段
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCostTemplatePrice(@Valid CostTemplatePriceCreateReqVO createReqVO);

    /**
     * 更新计费模板价格时段
     *
     * @param updateReqVO 更新信息
     */
    void updateCostTemplatePrice(@Valid CostTemplatePriceUpdateReqVO updateReqVO);

    /**
     * 删除计费模板价格时段
     *
     * @param id 编号
     */
    void deleteCostTemplatePrice(Long id);

    /**
     * 获得计费模板价格时段
     *
     * @param id 编号
     * @return 计费模板价格时段
     */
    CostTemplatePriceDO getCostTemplatePrice(Long id);

    /**
     * 获得计费模板价格时段列表
     *
     * @param ids 编号
     * @return 计费模板价格时段列表
     */
    List<CostTemplatePriceDO> getCostTemplatePriceList(Collection<Long> ids);

    /**
     * 获得计费模板价格时段分页
     *
     * @param pageReqVO 分页查询
     * @return 计费模板价格时段分页
     */
    PageResult<CostTemplatePriceDO> getCostTemplatePricePage(CostTemplatePricePageReqVO pageReqVO);

    /**
     * 获得计费模板价格时段列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 计费模板价格时段列表
     */
    List<CostTemplatePriceDO> getCostTemplatePriceList(CostTemplatePriceExportReqVO exportReqVO);

    /**
     * 根据计费模型id获取计费模型价格时段
     * @param operatorId
     * @return
     */
    List<CostTemplatePriceDO> getCostTemplatePriceByTemplateId(Long operatorId);
}
