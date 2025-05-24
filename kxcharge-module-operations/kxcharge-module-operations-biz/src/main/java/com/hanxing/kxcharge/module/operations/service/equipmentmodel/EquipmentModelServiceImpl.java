package com.hanxing.kxcharge.module.operations.service.equipmentmodel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.*;
import com.hanxing.kxcharge.module.operations.convert.equipmentmodel.EquipmentModelConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipmentmodel.EquipmentModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.*;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.EQUIPMENT_MODEL_NOT_EXISTS;

/**
 * 设备型号管理 Service 实现类
 *
 * @author kaixin
 */
@Service
@Validated
public class EquipmentModelServiceImpl implements EquipmentModelService {

    @Resource
    private EquipmentModelMapper equipmentModelMapper;

    @Override
    public Long createEquipmentModel(EquipmentModelCreateReqVO createReqVO) {
        // 插入
        EquipmentModelDO equipmentModel = EquipmentModelConvert.INSTANCE.convert(createReqVO);
        equipmentModelMapper.insert(equipmentModel);
        // 返回
        return equipmentModel.getId();
    }

    @Override
    public void updateEquipmentModel(EquipmentModelUpdateReqVO updateReqVO) {
        // 校验存在
        validateEquipmentModelExists(updateReqVO.getId());
        // 更新
        EquipmentModelDO updateObj = EquipmentModelConvert.INSTANCE.convert(updateReqVO);
        equipmentModelMapper.updateById(updateObj);
    }

    @Override
    public void deleteEquipmentModel(Long id) {
        // 校验存在
        validateEquipmentModelExists(id);
        // 删除
        equipmentModelMapper.deleteById(id);
    }

    private void validateEquipmentModelExists(Long id) {
        if (equipmentModelMapper.selectById(id) == null) {
            throw exception(EQUIPMENT_MODEL_NOT_EXISTS);
        }
    }

    @Override
    public EquipmentModelDO getEquipmentModel(Long id) {
        return equipmentModelMapper.selectById(id);
    }

    @Override
    public List<EquipmentModelDO> getEquipmentModelList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return equipmentModelMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<EquipmentModelDO> getEquipmentModelPage(EquipmentModelPageReqVO pageReqVO) {
        return equipmentModelMapper.selectPage(pageReqVO);
    }

    @Override
    public List<EquipmentModelDO> getEquipmentModelList(EquipmentModelExportReqVO exportReqVO) {
        return equipmentModelMapper.selectList(exportReqVO);
    }

    @Override
    public Map<Long, EquipmentModelDO> getModelMap(Collection<Long> modelIds) {
        if (CollUtil.isEmpty(modelIds)) {
            return Collections.emptyMap();
        }
        List<EquipmentModelDO> list = getEquipmentModelList(modelIds);
        return CollectionUtils.convertMap(list, EquipmentModelDO::getId);
    }

}
