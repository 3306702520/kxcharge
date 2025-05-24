package com.hanxing.kxcharge.module.operations.dal.mysql.equipmentbrand;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备品牌管理 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentBrandMapper extends BaseMapperX<EquipmentBrandDO> {

    default PageResult<EquipmentBrandDO> selectPage(EquipmentBrandPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EquipmentBrandDO>()
                .likeIfPresent(EquipmentBrandDO::getBrandName, reqVO.getBrandName())
                .betweenIfPresent(EquipmentBrandDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EquipmentBrandDO::getId));
    }

    default List<EquipmentBrandDO> selectList(EquipmentBrandExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipmentBrandDO>()
                .likeIfPresent(EquipmentBrandDO::getBrandName, reqVO.getBrandName())
                .betweenIfPresent(EquipmentBrandDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EquipmentBrandDO::getId));
    }

}
