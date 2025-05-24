package com.hanxing.kxcharge.module.operations.service.equipmentbrand;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 设备品牌管理 Service 接口
 *
 * @author kaixin
 */
public interface EquipmentBrandService {

    /**
     * 创建设备品牌管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEquipmentBrand(@Valid EquipmentBrandCreateReqVO createReqVO);

    /**
     * 更新设备品牌管理
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipmentBrand(@Valid EquipmentBrandUpdateReqVO updateReqVO);

    /**
     * 删除设备品牌管理
     *
     * @param id 编号
     */
    void deleteEquipmentBrand(Long id);

    /**
     * 获得设备品牌管理
     *
     * @param id 编号
     * @return 设备品牌管理
     */
    EquipmentBrandDO getEquipmentBrand(Long id);

    /**
     * 获得设备品牌管理列表
     *
     * @param ids 编号
     * @return 设备品牌管理列表
     */
    List<EquipmentBrandDO> getEquipmentBrandList(Collection<Long> ids);

    /**
     * 获得设备品牌管理分页
     *
     * @param pageReqVO 分页查询
     * @return 设备品牌管理分页
     */
    PageResult<EquipmentBrandDO> getEquipmentBrandPage(EquipmentBrandPageReqVO pageReqVO);

    /**
     * 获得设备品牌管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备品牌管理列表
     */
    List<EquipmentBrandDO> getEquipmentBrandList(EquipmentBrandExportReqVO exportReqVO);

    /**
     * 树形结构
     *
     * @return
     */
    List<EquipmentTreeRespVO> getEquipmentTreeTwoStage();

    Map<Long, EquipmentBrandDO> getBrandMap(Collection<Long> brandIds);

    List<EquipmentBrandDO> getEquipmentBrandListByOrgId(Long orgId);
}
