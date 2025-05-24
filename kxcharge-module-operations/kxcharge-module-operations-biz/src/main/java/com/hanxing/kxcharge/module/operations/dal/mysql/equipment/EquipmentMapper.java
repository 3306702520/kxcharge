package com.hanxing.kxcharge.module.operations.dal.mysql.equipment;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentPageReqVO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 电桩管理 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentMapper extends BaseMapperX<EquipmentDO> {

    default PageResult<EquipmentDO> selectPage(EquipmentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EquipmentDO>()
                .likeIfPresent(EquipmentDO::getEquipmentName, reqVO.getEquipmentName())
                .likeIfPresent(EquipmentDO::getEquipmentCode, reqVO.getEquipmentCode())
                .eqIfPresent(EquipmentDO::getStationId, reqVO.getStationId())
                .eqIfPresent(EquipmentDO::getOperatorId, reqVO.getOperatorId())
                .orderByDesc(EquipmentDO::getId));
    }

    default List<EquipmentDO> selectList(EquipmentExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipmentDO>()
                .likeIfPresent(EquipmentDO::getEquipmentName, reqVO.getEquipmentName())
                .eqIfPresent(EquipmentDO::getEquipmentCode, reqVO.getEquipmentCode())
                .eqIfPresent(EquipmentDO::getStationId, reqVO.getStationId())
                .eqIfPresent(EquipmentDO::getOperatorId, reqVO.getOperatorId())
                .orderByDesc(EquipmentDO::getId));
    }

}
