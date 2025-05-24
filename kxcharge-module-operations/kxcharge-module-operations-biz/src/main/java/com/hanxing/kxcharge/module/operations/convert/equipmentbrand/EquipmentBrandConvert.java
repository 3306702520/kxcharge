package com.hanxing.kxcharge.module.operations.convert.equipmentbrand;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.api.station.dto.EquipmentBrandReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 设备品牌管理 Convert
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentBrandConvert {

    EquipmentBrandConvert INSTANCE = Mappers.getMapper(EquipmentBrandConvert.class);

    EquipmentBrandDO convert(EquipmentBrandCreateReqVO bean);

    EquipmentBrandDO convert(EquipmentBrandUpdateReqVO bean);

    EquipmentBrandRespVO convert(EquipmentBrandDO bean);

    List<EquipmentBrandRespVO> convertList(List<EquipmentBrandDO> list);

    PageResult<EquipmentBrandRespVO> convertPage(PageResult<EquipmentBrandDO> page);

    List<EquipmentBrandExcelVO> convertList02(List<EquipmentBrandDO> list);

    EquipmentBrandReqDto convert01(EquipmentBrandDO equipmentBrand);
}
