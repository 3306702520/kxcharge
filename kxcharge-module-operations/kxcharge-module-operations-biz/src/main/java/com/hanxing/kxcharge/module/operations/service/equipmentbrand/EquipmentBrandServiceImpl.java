package com.hanxing.kxcharge.module.operations.service.equipmentbrand;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo.*;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.EquipmentModelExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.EquipmentModelTreeRespVO;
import com.hanxing.kxcharge.module.operations.convert.equipmentbrand.EquipmentBrandConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipment.EquipmentMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipmentbrand.EquipmentBrandMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipmentmodel.EquipmentModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.EQUIPMENT_BRAND_NOT_DELETE;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.EQUIPMENT_BRAND_NOT_EXISTS;

/**
 * 设备品牌管理 Service 实现类
 *
 * @author kaixin
 */
@Service
@Validated
public class EquipmentBrandServiceImpl implements EquipmentBrandService {

    @Resource
    private EquipmentBrandMapper equipmentBrandMapper;


    @Resource
    private EquipmentModelMapper equipmentModelMapper;


    @Resource
    private EquipmentMapper equipmentMapper;

    @Override
    public Long createEquipmentBrand(EquipmentBrandCreateReqVO createReqVO) {
        // 插入
        EquipmentBrandDO equipmentBrand = EquipmentBrandConvert.INSTANCE.convert(createReqVO);
        equipmentBrandMapper.insert(equipmentBrand);
        // 返回
        return equipmentBrand.getId();
    }

    @Override
    public void updateEquipmentBrand(EquipmentBrandUpdateReqVO updateReqVO) {
        // 校验存在
        validateEquipmentBrandExists(updateReqVO.getId());
        // 更新
        EquipmentBrandDO updateObj = EquipmentBrandConvert.INSTANCE.convert(updateReqVO);
        equipmentBrandMapper.updateById(updateObj);
    }

    @Override
    public void deleteEquipmentBrand(Long id) {
        // 校验存在
        validateEquipmentBrandExists(id);
        boolean exists = equipmentMapper.exists(new QueryWrapper<EquipmentDO>().lambda().eq(EquipmentDO::getEquipmentBrandId, id));
        if (exists){
            throw exception(EQUIPMENT_BRAND_NOT_DELETE);
        }
        // 删除
        equipmentBrandMapper.deleteById(id);
    }

    private void validateEquipmentBrandExists(Long id) {
        if (equipmentBrandMapper.selectById(id) == null) {
            throw exception(EQUIPMENT_BRAND_NOT_EXISTS);
        }
    }

    @Override
    public EquipmentBrandDO getEquipmentBrand(Long id) {
        return equipmentBrandMapper.selectById(id);
    }

    @Override
    public List<EquipmentBrandDO> getEquipmentBrandList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return equipmentBrandMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<EquipmentBrandDO> getEquipmentBrandPage(EquipmentBrandPageReqVO pageReqVO) {
        return equipmentBrandMapper.selectPage(pageReqVO);
    }

    @Override
    public List<EquipmentBrandDO> getEquipmentBrandList(EquipmentBrandExportReqVO exportReqVO) {
        return equipmentBrandMapper.selectList(exportReqVO);
    }

    @Override
    public List<EquipmentTreeRespVO> getEquipmentTreeTwoStage() {

        List<EquipmentTreeRespVO> resule = new ArrayList<>();
        EquipmentBrandExportReqVO exportReqVO = new EquipmentBrandExportReqVO();
        List<EquipmentBrandDO> brandDOList = equipmentBrandMapper.selectList(exportReqVO);

        for (EquipmentBrandDO brandDO : brandDOList) {
            EquipmentTreeRespVO add = new EquipmentTreeRespVO();
            add.setId(brandDO.getId());
            add.setName(brandDO.getBrandName());

            EquipmentModelExportReqVO reqVO = new EquipmentModelExportReqVO();
            reqVO.setEquipmentBrandId(brandDO.getId());
            List<EquipmentModelDO> modelDOList = equipmentModelMapper.selectList(reqVO);

            List<EquipmentModelTreeRespVO> addList = new ArrayList<>();
            for (EquipmentModelDO modelDO : modelDOList) {
                EquipmentModelTreeRespVO addModel = new EquipmentModelTreeRespVO();
                addModel.setId(modelDO.getId());
                addModel.setName(modelDO.getEquipmentModel());
                addList.add(addModel);
            }
            add.setChildren(addList);
            resule.add(add);
        }

        return resule;
    }

    @Override
    public Map<Long, EquipmentBrandDO> getBrandMap(Collection<Long> brandIds) {
        if (CollUtil.isEmpty(brandIds)) {
            return Collections.emptyMap();
        }
        List<EquipmentBrandDO> list = getEquipmentBrandList(brandIds);
        return CollectionUtils.convertMap(list, EquipmentBrandDO::getId);
    }

    @Override
    public List<EquipmentBrandDO> getEquipmentBrandListByOrgId(Long orgId) {
        return equipmentBrandMapper.selectList(new QueryWrapper<EquipmentBrandDO>().lambda().eq(orgId != null, EquipmentBrandDO::getOrgId, orgId));
    }

}
