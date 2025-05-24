package com.hanxing.kxcharge.module.member.convert.plate;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateCreateReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateExcelVO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateRespVO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateUpdateReqVO;
import com.hanxing.kxcharge.module.member.dal.dataobject.plate.PlateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户车牌信息 Convert
 *
 * @author 航兴新能源
 */
@Mapper
public interface PlateConvert {

    PlateConvert INSTANCE = Mappers.getMapper(PlateConvert.class);

    PlateDO convert(PlateCreateReqVO bean);

    PlateDO convert(PlateUpdateReqVO bean);

    PlateRespVO convert(PlateDO bean);

    List<PlateRespVO> convertList(List<PlateDO> list);

    PageResult<PlateRespVO> convertPage(PageResult<PlateDO> page);

    List<PlateExcelVO> convertList02(List<PlateDO> list);

}
