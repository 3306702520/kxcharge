package com.hanxing.kxcharge.module.operations.service.costtemplatepricetype;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 计费模板价格类别 Service 接口
 *
 * @author kaixin
 */
public interface CostTemplatePriceTypeService {

    /**
     * 创建计费模板价格类别
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCostTemplatePriceType(@Valid CostTemplatePriceTypeCreateReqVO createReqVO);

    /**
     * 更新计费模板价格类别
     *
     * @param updateReqVO 更新信息
     */
    void updateCostTemplatePriceType(@Valid CostTemplatePriceTypeUpdateReqVO updateReqVO);

    /**
     * 删除计费模板价格类别
     *
     * @param id 编号
     */
    void deleteCostTemplatePriceType(Long id);

    /**
     * 获得计费模板价格类别
     *
     * @param id 编号
     * @return 计费模板价格类别
     */
    CostTemplatePriceTypeDO getCostTemplatePriceType(Long id);

    /**
     * 获得计费模板价格类别列表
     *
     * @param ids 编号
     * @return 计费模板价格类别列表
     */
    List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeList(Collection<Long> ids);

    /**
     * 获得计费模板价格类别分页
     *
     * @param pageReqVO 分页查询
     * @return 计费模板价格类别分页
     */
    PageResult<CostTemplatePriceTypeDO> getCostTemplatePriceTypePage(CostTemplatePriceTypePageReqVO pageReqVO);

    /**
     * 获得计费模板价格类别列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 计费模板价格类别列表
     */
    List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeList(CostTemplatePriceTypeExportReqVO exportReqVO);

    /**
     * 根据模块id获取模块类型列表
     * @param costTemplateId
     * @return
     */
    List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeByTemplateId(Long costTemplateId);
}
