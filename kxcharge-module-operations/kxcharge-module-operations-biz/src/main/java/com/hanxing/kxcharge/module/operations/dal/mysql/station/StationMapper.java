package com.hanxing.kxcharge.module.operations.dal.mysql.station;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 站点信息 Mapper
 *
 * @author kaixin
 */
@Mapper
public interface StationMapper extends BaseMapperX<StationDO> {

    default PageResult<StationDO> selectPage(StationPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StationDO>()
                .eqIfPresent(StationDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(StationDO::getStationName, reqVO.getStationName())
                .eqIfPresent(StationDO::getStationStatus, reqVO.getStationStatus())
                .eqIfPresent(StationDO::getProperty, reqVO.getProperty())
                .orderByDesc(StationDO::getId));
    }

    default List<StationDO> selectList(StationExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<StationDO>()
                .eqIfPresent(StationDO::getOrgId, reqVO.getOrgId())
                .likeIfPresent(StationDO::getStationName, reqVO.getStationName())
                .eqIfPresent(StationDO::getStationStatus, reqVO.getStationStatus())
                .eqIfPresent(StationDO::getProperty, reqVO.getProperty())
                .orderByDesc(StationDO::getId));
    }

}
