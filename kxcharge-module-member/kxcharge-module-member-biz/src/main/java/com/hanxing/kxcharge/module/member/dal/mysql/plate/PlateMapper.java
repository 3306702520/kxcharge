package com.hanxing.kxcharge.module.member.dal.mysql.plate;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateExportReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlatePageReqVO;
import com.hanxing.kxcharge.module.member.dal.dataobject.plate.PlateDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户车牌信息 Mapper
 *
 * @author 航兴新能源
 */
@Mapper
public interface PlateMapper extends BaseMapperX<PlateDO> {

    default PageResult<PlateDO> selectPage(PlatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<PlateDO>()
                .eqIfPresent(PlateDO::getPlateType, reqVO.getPlateType())
                .eqIfPresent(PlateDO::getPlateNumber, reqVO.getPlateNumber())
                .eqIfPresent(PlateDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PlateDO::getLatelyPlace, reqVO.getLatelyPlace())
                .betweenIfPresent(PlateDO::getLatelyTime, reqVO.getLatelyTime())
                .eqIfPresent(PlateDO::getChargeQuantity, reqVO.getChargeQuantity())
                .eqIfPresent(PlateDO::getIsDefault, reqVO.getIsDefault())
                .betweenIfPresent(PlateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlateDO::getId));
    }

    default List<PlateDO> selectList(PlateExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<PlateDO>()
                .eqIfPresent(PlateDO::getPlateType, reqVO.getPlateType())
                .eqIfPresent(PlateDO::getPlateNumber, reqVO.getPlateNumber())
                .eqIfPresent(PlateDO::getUserId, reqVO.getUserId())
                .eqIfPresent(PlateDO::getLatelyPlace, reqVO.getLatelyPlace())
                .betweenIfPresent(PlateDO::getLatelyTime, reqVO.getLatelyTime())
                .eqIfPresent(PlateDO::getChargeQuantity, reqVO.getChargeQuantity())
                .eqIfPresent(PlateDO::getIsDefault, reqVO.getIsDefault())
                .betweenIfPresent(PlateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(PlateDO::getId));
    }

    @Update("update member_plate set is_default = 0 where user_id = #{userId}")
    void updateDefaultByUserId(@Param("userId") Long userId);

    @Update("update member_plate set is_default = 1 where id = #{id}")
    void updateDefaultById(@Param("id") Long id);
}
