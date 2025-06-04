package com.hanxing.kxcharge.module.system.dal.mysql.operator;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.OperatorPageReqVO;
import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.OperatorRespVO;
import com.hanxing.kxcharge.module.system.dal.dataobject.operator.OperatorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 运营商 Mapper
 *
 * @author 航兴新能源
 */
@Mapper
public interface OperatorMapper extends BaseMapperX<OperatorDO> {

    default PageResult<OperatorDO> selectPage(OperatorPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OperatorDO>()
                .likeIfPresent(OperatorDO::getOrgName, reqVO.getOrgName())
                .likeIfPresent(OperatorDO::getOperatorName, reqVO.getOperatorName())
                .eqIfPresent(OperatorDO::getAddress, reqVO.getAddress())
                .eqIfPresent(OperatorDO::getCode, reqVO.getCode())
                .eqIfPresent(OperatorDO::getCityCode, reqVO.getCityCode())
                .eqIfPresent(OperatorDO::getProvinceCode, reqVO.getProvinceCode())
                .likeIfPresent(OperatorDO::getCityName, reqVO.getCityName())
                .betweenIfPresent(OperatorDO::getSignStartTime, reqVO.getSignStartTime())
                .betweenIfPresent(OperatorDO::getSignEndTime, reqVO.getSignEndTime())
                .eqIfPresent(OperatorDO::getBusinessLogo, reqVO.getBusinessLogo())
                .eqIfPresent(OperatorDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OperatorDO::getCodeAbbr, reqVO.getCodeAbbr())
                .eqIfPresent(OperatorDO::getContactUser, reqVO.getContactUser())
                .eqIfPresent(OperatorDO::getContactNum, reqVO.getContactNum())
                .eqIfPresent(OperatorDO::getContactAddress, reqVO.getContactAddress())
                .eqIfPresent(OperatorDO::getEmail, reqVO.getEmail())
                .eqIfPresent(OperatorDO::getBusinessLicense, reqVO.getBusinessLicense())
                .eqIfPresent(OperatorDO::getIsFrozen, reqVO.getIsFrozen())
                .betweenIfPresent(OperatorDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperatorDO::getId));
    }

    default List<OperatorDO> selectList(OperatorRespVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OperatorDO>()
                .likeIfPresent(OperatorDO::getOrgName, reqVO.getOrgName())
                .likeIfPresent(OperatorDO::getCode, reqVO.getCode())
                .likeIfPresent(OperatorDO::getContactUser, reqVO.getContactUser())
                .eqIfPresent(OperatorDO::getCityCode, reqVO.getCityCode())
                .eqIfPresent(OperatorDO::getProvinceCode, reqVO.getProvinceCode())
                .likeIfPresent(OperatorDO::getCityName, reqVO.getCityName())
                .betweenIfPresent(OperatorDO::getSignStartTime, reqVO.getSignStartTime())
                .betweenIfPresent(OperatorDO::getSignEndTime, reqVO.getSignEndTime())
                .eqIfPresent(OperatorDO::getBusinessLogo, reqVO.getBusinessLogo())
                .eqIfPresent(OperatorDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OperatorDO::getBusinessLicense, reqVO.getBusinessLicense())
                .eqIfPresent(OperatorDO::getIsFrozen, reqVO.getIsFrozen())
                .betweenIfPresent(OperatorDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OperatorDO::getId));
    }

}
