package com.hanxing.kxcharge.module.operations.dal.mysql.equipmentconnector;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 电枪管理 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentConnectorMapper extends BaseMapperX<EquipmentConnectorDO> {

    default PageResult<EquipmentConnectorDO> selectPage(EquipmentConnectorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<EquipmentConnectorDO>()
                .eqIfPresent(EquipmentConnectorDO::getStationId, reqVO.getStationId())
                .eqIfPresent(EquipmentConnectorDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(EquipmentConnectorDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(EquipmentConnectorDO::getConnectorCode, reqVO.getConnectorCode())
                .eqIfPresent(EquipmentConnectorDO::getShortcutCode, reqVO.getShortcutCode())
                .likeIfPresent(EquipmentConnectorDO::getConnectorName, reqVO.getConnectorName())
                .eqIfPresent(EquipmentConnectorDO::getIsEnableStart, reqVO.getIsEnableStart())
                .eqIfPresent(EquipmentConnectorDO::getWorkStatus, reqVO.getWorkStatus())
                .eqIfPresent(EquipmentConnectorDO::getVersion, reqVO.getVersion())
                .eqIfPresent(EquipmentConnectorDO::getIsAlonePrice, reqVO.getIsAlonePrice())
                .betweenIfPresent(EquipmentConnectorDO::getOperationDate, reqVO.getOperationDate())
                .orderByDesc(EquipmentConnectorDO::getId));
    }

    default List<EquipmentConnectorDO> selectList(EquipmentConnectorExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<EquipmentConnectorDO>()
                .eqIfPresent(EquipmentConnectorDO::getStationId, reqVO.getStationId())
                .eqIfPresent(EquipmentConnectorDO::getEquipmentId, reqVO.getEquipmentId())
                .eqIfPresent(EquipmentConnectorDO::getOrgId, reqVO.getOrgId())
                .eqIfPresent(EquipmentConnectorDO::getOperatorId, reqVO.getOperatorId())
                .eqIfPresent(EquipmentConnectorDO::getConnectorCode, reqVO.getConnectorCode())
                .eqIfPresent(EquipmentConnectorDO::getShortcutCode, reqVO.getShortcutCode())
                .likeIfPresent(EquipmentConnectorDO::getConnectorName, reqVO.getConnectorName())
                .eqIfPresent(EquipmentConnectorDO::getIsEnableStart, reqVO.getIsEnableStart())
                .eqIfPresent(EquipmentConnectorDO::getWorkStatus, reqVO.getWorkStatus())
                .eqIfPresent(EquipmentConnectorDO::getVersion, reqVO.getVersion())
                .eqIfPresent(EquipmentConnectorDO::getIsAlonePrice, reqVO.getIsAlonePrice())
                .betweenIfPresent(EquipmentConnectorDO::getOperationDate, reqVO.getOperationDate())
                .orderByDesc(EquipmentConnectorDO::getId));
    }

}
