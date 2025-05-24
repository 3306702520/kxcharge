package com.hanxing.kxcharge.module.operations.service.costtemplate;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 计费模板 Service 接口
 *
 * @author kaixin
 */
public interface CostTemplateService {

    /**
     * 创建计费模板
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCostTemplate(@Valid CostTemplateCreateReqVO createReqVO);

    /**
     * 更新计费模板
     *
     * @param updateReqVO 更新信息
     */
    void updateCostTemplate(@Valid CostTemplateUpdateReqVO updateReqVO);

    /**
     * 删除计费模板
     *
     * @param id 编号
     */
    Boolean deleteCostTemplate(Long id);

    /**
     * 获得计费模板
     *
     * @param id 编号
     * @return 计费模板
     */
    CostTemplateDO getCostTemplate(Long id);

    /**
     * 获得计费模板列表
     *
     * @param ids 编号
     * @return 计费模板列表
     */
    List<CostTemplateDO> getCostTemplateList(Collection<Long> ids);

    /**
     * 获得计费模板分页
     *
     * @param pageReqVO 分页查询
     * @return 计费模板分页
     */
    PageResult<CostTemplateDO> getCostTemplatePage(CostTemplatePageReqVO pageReqVO);

    /**
     * 获得计费模板列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 计费模板列表
     */
    List<CostTemplateDO> getCostTemplateList(CostTemplateExportReqVO exportReqVO);


    /**
     * 获取当前时间的电费单价
     * @param id costTemplate Id
     * @return 电费单价
     */
    BigDecimal getNowPrice(Long id);
}
