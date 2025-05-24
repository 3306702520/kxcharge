package com.hanxing.kxcharge.module.operations.controller.admin.equipment;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.*;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.EquipmentConnectorExportReqVO;
import com.hanxing.kxcharge.module.operations.convert.equipment.EquipmentConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentbrand.EquipmentBrandConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentconnector.EquipmentConnectorConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentmodel.EquipmentModelConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.mq.producer.proofTime.ProofTimeProducer;
import com.hanxing.kxcharge.module.operations.mq.producer.proofprice.ProofPriceProducer;
import com.hanxing.kxcharge.module.operations.mq.producer.remoterestart.RemoteRestartProducer;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import com.hanxing.kxcharge.module.operations.service.equipmentbrand.EquipmentBrandService;
import com.hanxing.kxcharge.module.operations.service.equipmentconnector.EquipmentConnectorService;
import com.hanxing.kxcharge.module.operations.service.equipmentmodel.EquipmentModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;
import static com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "管理后台 - 电桩管理")
@RestController
@RequestMapping("/operations/equipment")
@Validated
public class EquipmentController {

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private EquipmentBrandService equipmentBrandService;
    @Resource
    private EquipmentModelService equipmentModelService;

    @Resource
    private EquipmentConnectorService equipmentConnectorService;

    @Resource
    private RemoteRestartProducer remoteRestartProducer;

    @Resource
    private ProofTimeProducer proofTimeProducer;

    @Resource
    private ProofPriceProducer proofPriceProducer;

    @PostMapping("/create")
    @Operation(summary = "创建电桩管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment:create')")
    public CommonResult<Long> createEquipment(@Valid @RequestBody EquipmentCreateReqVO createReqVO) {
        return success(equipmentService.createEquipment(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电桩管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment:update')")
    public CommonResult<Boolean> updateEquipment(@Valid @RequestBody EquipmentUpdateReqVO updateReqVO) {
        equipmentService.updateEquipment(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电桩管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:equipment:delete')")
    public CommonResult<Boolean> deleteEquipment(@RequestParam("id") Long id) {
        equipmentService.deleteEquipment(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电桩管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:equipment:query')")
    public CommonResult<EquipmentRespVO> getEquipment(@RequestParam("id") Long id) {
        EquipmentDO equipment = equipmentService.getEquipment(id);
        return success(EquipmentConvert.INSTANCE.convert(equipment));
    }

    @GetMapping("/list")
    @Operation(summary = "获得电桩管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:equipment:query')")
    public CommonResult<List<EquipmentRespVO>> getEquipmentList(@RequestParam("ids") Collection<Long> ids) {
        List<EquipmentDO> list = equipmentService.getEquipmentList(ids);
        return success(EquipmentConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电桩管理分页")
    @PreAuthorize("@ss.hasPermission('operations:equipment:query')")
    public CommonResult<PageResult<EquipmentRespVO>> getEquipmentPage(@Valid EquipmentPageReqVO pageVO) {
        PageResult<EquipmentDO> pageResult = equipmentService.getEquipmentPage(pageVO);

        // 获得拼接需要的数据品牌
        Collection<Long> brandIds = convertList(pageResult.getList(), EquipmentDO::getEquipmentBrandId);
        Map<Long, EquipmentBrandDO> brandMap = equipmentBrandService.getBrandMap(brandIds);

        // 类型
        Collection<Long> modelIds = convertList(pageResult.getList(), EquipmentDO::getEquipmentModelId);
        Map<Long, EquipmentModelDO> modelMap = equipmentModelService.getModelMap(modelIds);


        // 拼接结果返回
        List<EquipmentRespVO> equipmentRespVOS = new ArrayList<>(pageResult.getList().size());

        pageResult.getList().forEach(equipmentDO -> {
            EquipmentRespVO respVO = EquipmentConvert.INSTANCE.convert(equipmentDO);
            respVO.setEquipmentBrandRespVO(EquipmentBrandConvert.INSTANCE.convert(brandMap.get(equipmentDO.getEquipmentBrandId())));
            respVO.setEquipmentModelRespVO(EquipmentModelConvert.INSTANCE.convert(modelMap.get(equipmentDO.getEquipmentModelId())));

            EquipmentConnectorExportReqVO exportReqVO = new EquipmentConnectorExportReqVO();
            exportReqVO.setEquipmentId(equipmentDO.getId());
            respVO.setEquipmentConnectorRespVOS(EquipmentConnectorConvert.INSTANCE.convertList(equipmentConnectorService.getEquipmentConnectorList(exportReqVO)));
            equipmentRespVOS.add(respVO);
        });

        return success(new PageResult<>(equipmentRespVOS, pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出电桩管理 Excel")
    @PreAuthorize("@ss.hasPermission('operations:equipment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipmentExcel(@Valid EquipmentExportReqVO exportReqVO,
                                     HttpServletResponse response) throws IOException {
        List<EquipmentDO> list = equipmentService.getEquipmentList(exportReqVO);
        // 导出 Excel
        List<EquipmentExcelVO> datas = EquipmentConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "电桩管理.xls", "数据", EquipmentExcelVO.class, datas);
    }

    @GetMapping("/remote-restart")
    @Operation(summary = "远程重启电桩")
    @PreAuthorize("@ss.hasPermission('operations:equipment:restart')")
    public CommonResult<Boolean> remoteRestart(@RequestParam("id") Long id) {
        EquipmentDO equipment = equipmentService.getEquipment(id);
        remoteRestartProducer.sendRemoteRestart(equipment.getEquipmentCode());

        return CommonResult.success(Boolean.TRUE);
    }

    @PreAuthorize("@ss.hasPermission('operations:equipment:upgradation')")
    @Operation(summary = "远程升级电桩")
    @GetMapping("/remote-upgrade")
    public CommonResult<Boolean> remoteUpgrade(@RequestParam("id") Long id) {
        //todo zxy

        return CommonResult.success(Boolean.TRUE);
    }

    @PreAuthorize("@ss.hasPermission('operations:equipment:proofTime')")
    @Operation(summary = "校时")
    @GetMapping("/proof-time")
    public CommonResult<Boolean> proofTime(@RequestParam("id") Long id) {
        return CommonResult.success(equipmentService.proofTime(id));
    }

    @PreAuthorize("@ss.hasPermission('operations:equipment:proofPrice')")
    @Operation(summary = "校价")
    @GetMapping("/proof-price")
    public CommonResult<Boolean> proofPrice(@RequestParam("id") Long id) {
        return CommonResult.success(equipmentService.proofPrice(id));
    }

    /**
     * 不操作真机，只是在逻辑上对枪进行了控制。
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermission('operations:equipment:isStop')")
    @Operation(summary = "是否停用,传入枪id,停用的会被启用,启用的会被停用")
    @GetMapping("/is-stop")
    public CommonResult<Boolean> enable(@RequestParam("id") Long id) {
        equipmentService.enable(id);
        return CommonResult.success(Boolean.TRUE);
    }


}
