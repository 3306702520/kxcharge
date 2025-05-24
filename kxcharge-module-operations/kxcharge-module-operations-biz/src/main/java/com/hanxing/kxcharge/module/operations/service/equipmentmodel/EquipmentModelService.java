package com.hanxing.kxcharge.module.operations.service.equipmentmodel;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 设备型号管理 Service 接口
 *
 * @author kaixin
 */
public interface EquipmentModelService {

    /**
     * 创建设备型号管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEquipmentModel(@Valid EquipmentModelCreateReqVO createReqVO);

    /**
     * 更新设备型号管理
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipmentModel(@Valid EquipmentModelUpdateReqVO updateReqVO);

    /**
     * 删除设备型号管理
     *
     * @param id 编号
     */
    void deleteEquipmentModel(Long id);

    /**
     * 获得设备型号管理
     *
     * @param id 编号
     * @return 设备型号管理
     */
    EquipmentModelDO getEquipmentModel(Long id);

    /**
     * 获得设备型号管理列表
     *
     * @param ids 编号
     * @return 设备型号管理列表
     */
    List<EquipmentModelDO> getEquipmentModelList(Collection<Long> ids);

    /**
     * 获得设备型号管理分页
     *
     * @param pageReqVO 分页查询
     * @return 设备型号管理分页
     */
    PageResult<EquipmentModelDO> getEquipmentModelPage(EquipmentModelPageReqVO pageReqVO);

    /**
     * 获得设备型号管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备型号管理列表
     */
    List<EquipmentModelDO> getEquipmentModelList(EquipmentModelExportReqVO exportReqVO);

    Map<Long, EquipmentModelDO> getModelMap(Collection<Long> modelIds);

}
