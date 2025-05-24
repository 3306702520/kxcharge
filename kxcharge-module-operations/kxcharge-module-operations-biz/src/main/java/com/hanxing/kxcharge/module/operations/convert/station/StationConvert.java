package com.hanxing.kxcharge.module.operations.convert.station;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.api.station.dto.StationReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.*;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.StationInfoVO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 站点信息 Convert
 *
 * @author kaixin
 */
@Mapper
public interface StationConvert {

    StationConvert INSTANCE = Mappers.getMapper(StationConvert.class);

    StationDO convert(StationCreateReqVO bean);

    StationDO convert(StationUpdateReqVO bean);

    StationRespVO convert(StationDO bean);

    StationReqDto convert01(StationDO bean);

    List<StationRespVO> convertList(List<StationDO> list);

    PageResult<StationRespVO> convertPage(PageResult<StationDO> page);

    List<StationExcelVO> convertList02(List<StationDO> list);

    StationInfoVO convertInfo(StationDO bean);

}
