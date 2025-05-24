package com.hanxing.kxcharge.module.operations.convert.equipmentmodel;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.api.station.dto.EquipmentModelReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备型号管理 Convert
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentModelConvert {

    EquipmentModelConvert INSTANCE = Mappers.getMapper(EquipmentModelConvert.class);

    EquipmentModelDO convert(EquipmentModelCreateReqVO bean);

    EquipmentModelDO convert(EquipmentModelUpdateReqVO bean);

    EquipmentModelRespVO convert(EquipmentModelDO bean);

    List<EquipmentModelRespVO> convertList(List<EquipmentModelDO> list);

    PageResult<EquipmentModelRespVO> convertPage(PageResult<EquipmentModelDO> page);

    List<EquipmentModelExcelVO> convertList02(List<EquipmentModelDO> list);

    EquipmentModelReqDto convert01(EquipmentModelDO equipmentModel);

}
