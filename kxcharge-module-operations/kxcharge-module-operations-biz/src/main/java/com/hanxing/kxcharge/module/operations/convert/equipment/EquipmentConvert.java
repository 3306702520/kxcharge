package com.hanxing.kxcharge.module.operations.convert.equipment;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.api.station.dto.EquipmentReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 电桩管理 Convert
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentConvert {

    EquipmentConvert INSTANCE = Mappers.getMapper(EquipmentConvert.class);

    EquipmentDO convert(EquipmentCreateReqVO bean);

    EquipmentDO convert(EquipmentUpdateReqVO bean);

    EquipmentRespVO convert(EquipmentDO bean);

    List<EquipmentRespVO> convertList(List<EquipmentDO> list);

    PageResult<EquipmentRespVO> convertPage(PageResult<EquipmentDO> page);

    List<EquipmentExcelVO> convertList02(List<EquipmentDO> list);


    List<EquipmentReqDto> convertList01(List<EquipmentDO> equipmentList);
}
