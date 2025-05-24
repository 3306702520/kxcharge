package com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.*;
import com.hanxing.kxcharge.module.operations.convert.equipmentmodel.EquipmentModelConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
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
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 设备型号管理")
@RestController
@RequestMapping("/operations/equipment-model")
@Validated
public class EquipmentModelController {

    @Resource
    private EquipmentModelService equipmentModelService;

    @PostMapping("/create")
    @Operation(summary = "创建设备型号管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment-model:create')")
    public CommonResult<Long> createEquipmentModel(@Valid @RequestBody EquipmentModelCreateReqVO createReqVO) {
        return success(equipmentModelService.createEquipmentModel(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备型号管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment-model:update')")
    public CommonResult<Boolean> updateEquipmentModel(@Valid @RequestBody EquipmentModelUpdateReqVO updateReqVO) {
        equipmentModelService.updateEquipmentModel(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备型号管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:equipment-model:delete')")
    public CommonResult<Boolean> deleteEquipmentModel(@RequestParam("id") Long id) {
        equipmentModelService.deleteEquipmentModel(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备型号管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:equipment-model:query')")
    public CommonResult<EquipmentModelRespVO> getEquipmentModel(@RequestParam("id") Long id) {
        EquipmentModelDO equipmentModel = equipmentModelService.getEquipmentModel(id);
        return success(EquipmentModelConvert.INSTANCE.convert(equipmentModel));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备型号管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:equipment-model:query')")
    public CommonResult<List<EquipmentModelRespVO>> getEquipmentModelList(@RequestParam("ids") Collection<Long> ids) {
        List<EquipmentModelDO> list = equipmentModelService.getEquipmentModelList(ids);
        return success(EquipmentModelConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备型号管理分页")
    @PreAuthorize("@ss.hasPermission('operations:equipment-model:query')")
    public CommonResult<PageResult<EquipmentModelRespVO>> getEquipmentModelPage(@Valid EquipmentModelPageReqVO pageVO) {
        PageResult<EquipmentModelDO> pageResult = equipmentModelService.getEquipmentModelPage(pageVO);
        return success(EquipmentModelConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备型号管理 Excel")
    @PreAuthorize("@ss.hasPermission('operations:equipment-model:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipmentModelExcel(@Valid EquipmentModelExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<EquipmentModelDO> list = equipmentModelService.getEquipmentModelList(exportReqVO);
        // 导出 Excel
        List<EquipmentModelExcelVO> datas = EquipmentModelConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备型号管理.xls", "数据", EquipmentModelExcelVO.class, datas);
    }

}
