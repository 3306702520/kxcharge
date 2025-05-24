package com.hanxing.kxcharge.module.operations.api.station;


import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnore;
import com.hanxing.kxcharge.module.operations.api.station.dto.StationReqDto;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import com.hanxing.kxcharge.module.operations.service.equipmentbrand.EquipmentBrandService;
import com.hanxing.kxcharge.module.operations.service.equipmentconnector.EquipmentConnectorService;
import com.hanxing.kxcharge.module.operations.service.equipmentmodel.EquipmentModelService;
import com.hanxing.kxcharge.module.operations.service.station.StationService;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnore;
import com.hanxing.kxcharge.module.operations.api.station.dto.EquipmentConnectorReqDto;
import com.hanxing.kxcharge.module.operations.api.station.dto.EquipmentReqDto;
import com.hanxing.kxcharge.module.operations.api.station.dto.StationReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.EquipmentConnectorExportReqVO;
import com.hanxing.kxcharge.module.operations.convert.equipment.EquipmentConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentbrand.EquipmentBrandConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentconnector.EquipmentConnectorConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentmodel.EquipmentModelConvert;
import com.hanxing.kxcharge.module.operations.convert.station.StationConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import com.hanxing.kxcharge.module.operations.service.equipmentbrand.EquipmentBrandService;
import com.hanxing.kxcharge.module.operations.service.equipmentconnector.EquipmentConnectorService;
import com.hanxing.kxcharge.module.operations.service.equipmentmodel.EquipmentModelService;
import com.hanxing.kxcharge.module.operations.service.station.StationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Slf4j
public class StationApiImpl implements StationApi {


    @Autowired
    StationService stationService;

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    EquipmentConnectorService equipmentConnectorService;

    @Resource
    private EquipmentBrandService equipmentBrandService;

    @Resource
    private EquipmentModelService equipmentModelService;

    @Override
    @TenantIgnore
    public CommonResult<StationReqDto> stationInfo(Long id) {
        StationDO station = stationService.getStation(id);
        StationReqDto stationReqDto = StationConvert.INSTANCE.convert01(station);

        EquipmentExportReqVO reqVO = new EquipmentExportReqVO();
        reqVO.setStationId(station.getId());
        List<EquipmentDO> equipmentList = equipmentService.getEquipmentList(reqVO);


        List<EquipmentReqDto> equipmentReqDtoList = EquipmentConvert.INSTANCE.convertList01(equipmentList);
        stationReqDto.setEquipmentReqDtoList(equipmentReqDtoList);

        for (EquipmentReqDto reqDto : equipmentReqDtoList) {
            EquipmentConnectorExportReqVO exportReqVO = new EquipmentConnectorExportReqVO();
            exportReqVO.setStationId(station.getId());
            exportReqVO.setEquipmentId(reqDto.getId());
            List<EquipmentConnectorDO> equipmentConnectorList = equipmentConnectorService.getEquipmentConnectorList(exportReqVO);

            List<EquipmentConnectorReqDto> equipmentConnectorReqDtoList = EquipmentConnectorConvert.INSTANCE.convertList01(equipmentConnectorList);

            EquipmentBrandDO equipmentBrand = equipmentBrandService.getEquipmentBrand(reqDto.getEquipmentBrandId());
            EquipmentModelDO equipmentModel = equipmentModelService.getEquipmentModel(reqDto.getEquipmentModelId());
            reqDto.setBrandReqDto(EquipmentBrandConvert.INSTANCE.convert01(equipmentBrand));
            reqDto.setModelReqDto(EquipmentModelConvert.INSTANCE.convert01(equipmentModel));
            reqDto.setConnectorReqDtoList(equipmentConnectorReqDtoList);
        }


        return CommonResult.success(stationReqDto);
    }

}
