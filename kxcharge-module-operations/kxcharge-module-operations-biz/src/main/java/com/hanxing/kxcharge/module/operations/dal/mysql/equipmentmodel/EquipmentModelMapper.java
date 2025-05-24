package com.hanxing.kxcharge.module.operations.dal.mysql.equipmentmodel;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 设备型号管理 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentModelMapper extends BaseMapperX<EquipmentModelDO> {

    default PageResult<EquipmentModelDO> selectPage(EquipmentModelPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EquipmentModelDO>()
                .eqIfPresent(EquipmentModelDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(EquipmentModelDO::getEquipmentBrandId, reqVO.getEquipmentBrandId())
                .eqIfPresent(EquipmentModelDO::getEquipmentModel, reqVO.getEquipmentModel())
                .eqIfPresent(EquipmentModelDO::getNumber, reqVO.getNumber())
                .eqIfPresent(EquipmentModelDO::getType, reqVO.getType())
                .eqIfPresent(EquipmentModelDO::getImg, reqVO.getImg())
                .eqIfPresent(EquipmentModelDO::getRemark, reqVO.getRemark())
                .eqIfPresent(EquipmentModelDO::getPower, reqVO.getPower())
                .eqIfPresent(EquipmentModelDO::getSupPower, reqVO.getSupPower())
                .eqIfPresent(EquipmentModelDO::getGunTemperatureWarn, reqVO.getGunTemperatureWarn())
                .eqIfPresent(EquipmentModelDO::getGunTemperatureStop, reqVO.getGunTemperatureStop())
                .eqIfPresent(EquipmentModelDO::getInputv, reqVO.getInputv())
                .eqIfPresent(EquipmentModelDO::getMaxv, reqVO.getMaxv())
                .eqIfPresent(EquipmentModelDO::getMinv, reqVO.getMinv())
                .eqIfPresent(EquipmentModelDO::getMaxa, reqVO.getMaxa())
                .eqIfPresent(EquipmentModelDO::getMina, reqVO.getMina())
                .eqIfPresent(EquipmentModelDO::getMaxp, reqVO.getMaxp())
                .eqIfPresent(EquipmentModelDO::getMinp, reqVO.getMinp())
                .betweenIfPresent(EquipmentModelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EquipmentModelDO::getId));
    }

    default List<EquipmentModelDO> selectList(EquipmentModelExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipmentModelDO>()
                .eqIfPresent(EquipmentModelDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(EquipmentModelDO::getEquipmentBrandId, reqVO.getEquipmentBrandId())
                .eqIfPresent(EquipmentModelDO::getEquipmentModel, reqVO.getEquipmentModel())
                .eqIfPresent(EquipmentModelDO::getNumber, reqVO.getNumber())
                .eqIfPresent(EquipmentModelDO::getType, reqVO.getType())
                .eqIfPresent(EquipmentModelDO::getImg, reqVO.getImg())
                .eqIfPresent(EquipmentModelDO::getRemark, reqVO.getRemark())
                .eqIfPresent(EquipmentModelDO::getPower, reqVO.getPower())
                .eqIfPresent(EquipmentModelDO::getSupPower, reqVO.getSupPower())
                .eqIfPresent(EquipmentModelDO::getGunTemperatureWarn, reqVO.getGunTemperatureWarn())
                .eqIfPresent(EquipmentModelDO::getGunTemperatureStop, reqVO.getGunTemperatureStop())
                .eqIfPresent(EquipmentModelDO::getInputv, reqVO.getInputv())
                .eqIfPresent(EquipmentModelDO::getMaxv, reqVO.getMaxv())
                .eqIfPresent(EquipmentModelDO::getMinv, reqVO.getMinv())
                .eqIfPresent(EquipmentModelDO::getMaxa, reqVO.getMaxa())
                .eqIfPresent(EquipmentModelDO::getMina, reqVO.getMina())
                .eqIfPresent(EquipmentModelDO::getMaxp, reqVO.getMaxp())
                .eqIfPresent(EquipmentModelDO::getMinp, reqVO.getMinp())
                .betweenIfPresent(EquipmentModelDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(EquipmentModelDO::getId));
    }

}
