package com.hanxing.kxcharge.module.operations.convert.equipmentconnector;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.api.station.dto.EquipmentConnectorReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.EquipmentConnectorCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.EquipmentConnectorExcelVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.EquipmentConnectorRespVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.EquipmentConnectorUpdateReqVO;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.EquipmentConnectorInfoVO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 电枪管理 Convert
 *
 * @author kaixin
 */
@Mapper
public interface EquipmentConnectorConvert {

    EquipmentConnectorConvert INSTANCE = Mappers.getMapper(EquipmentConnectorConvert.class);

    EquipmentConnectorDO convert(EquipmentConnectorCreateReqVO bean);

    EquipmentConnectorDO convert(EquipmentConnectorUpdateReqVO bean);

    EquipmentConnectorRespVO convert(EquipmentConnectorDO bean);

    List<EquipmentConnectorRespVO> convertList(List<EquipmentConnectorDO> list);

    PageResult<EquipmentConnectorRespVO> convertPage(PageResult<EquipmentConnectorDO> page);

    List<EquipmentConnectorExcelVO> convertList02(List<EquipmentConnectorDO> list);

    EquipmentConnectorInfoVO convertInfoVO(EquipmentConnectorDO equipmentConnectorDO);

    List<EquipmentConnectorReqDto> convertList01(List<EquipmentConnectorDO> equipmentConnectorList);

}
