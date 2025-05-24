package com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentbrand.vo.*;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.EquipmentModelExportReqVO;
import com.hanxing.kxcharge.module.operations.convert.equipmentbrand.EquipmentBrandConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentmodel.EquipmentModelConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.service.equipmentbrand.EquipmentBrandService;
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

@Tag(name = "管理后台 - 设备品牌管理")
@RestController
@RequestMapping("/operations/equipment-brand")
@Validated
public class EquipmentBrandController {

    @Resource
    private EquipmentBrandService equipmentBrandService;

    @Resource
    private EquipmentModelService equipmentModelService;

    @PostMapping("/create")
    @Operation(summary = "创建设备品牌管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment-brand:create')")
    public CommonResult<Long> createEquipmentBrand(@Valid @RequestBody EquipmentBrandCreateReqVO createReqVO) {
        return success(equipmentBrandService.createEquipmentBrand(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备品牌管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment-brand:update')")
    public CommonResult<Boolean> updateEquipmentBrand(@Valid @RequestBody EquipmentBrandUpdateReqVO updateReqVO) {
        equipmentBrandService.updateEquipmentBrand(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备品牌管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:equipment-brand:delete')")
    public CommonResult<Boolean> deleteEquipmentBrand(@RequestParam("id") Long id) {
        equipmentBrandService.deleteEquipmentBrand(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备品牌管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:equipment-brand:query')")
    public CommonResult<EquipmentBrandRespVO> getEquipmentBrand(@RequestParam("id") Long id) {
        EquipmentBrandDO equipmentBrand = equipmentBrandService.getEquipmentBrand(id);
        return success(EquipmentBrandConvert.INSTANCE.convert(equipmentBrand));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备品牌管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:equipment-brand:query')")
    public CommonResult<List<EquipmentBrandRespVO>> getEquipmentBrandList(@RequestParam("ids") Collection<Long> ids) {
        List<EquipmentBrandDO> list = equipmentBrandService.getEquipmentBrandList(ids);
        return success(EquipmentBrandConvert.INSTANCE.convertList(list));
    }



    @GetMapping("/listAll")
    @Operation(summary = "获得所有设备品牌管理列表")
    public CommonResult<List<EquipmentBrandRespVO>> getEquipmentBrandListAll(@RequestParam(value = "orgId", required = false) Long orgId) {
        List<EquipmentBrandDO> list =equipmentBrandService.getEquipmentBrandListByOrgId(orgId);
        List<EquipmentBrandRespVO> brandRespVOS = EquipmentBrandConvert.INSTANCE.convertList(list);

        for (EquipmentBrandRespVO respVO : brandRespVOS) {
            EquipmentModelExportReqVO modelExportReqVO = new EquipmentModelExportReqVO();
            modelExportReqVO.setEquipmentBrandId(respVO.getId());
            List<EquipmentModelDO> equipmentModelList = equipmentModelService.getEquipmentModelList(modelExportReqVO);
            respVO.setEquipmentModelRespVOS(EquipmentModelConvert.INSTANCE.convertList(equipmentModelList));
        }

        return success(brandRespVOS);
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备品牌管理分页")
    @PreAuthorize("@ss.hasPermission('operations:equipment-brand:query')")
    public CommonResult<PageResult<EquipmentBrandRespVO>> getEquipmentBrandPage(@Valid EquipmentBrandPageReqVO pageVO) {
        PageResult<EquipmentBrandDO> pageResult = equipmentBrandService.getEquipmentBrandPage(pageVO);
        return success(EquipmentBrandConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备品牌管理 Excel")
    @PreAuthorize("@ss.hasPermission('operations:equipment-brand:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipmentBrandExcel(@Valid EquipmentBrandExportReqVO exportReqVO,
                                          HttpServletResponse response) throws IOException {
        List<EquipmentBrandDO> list = equipmentBrandService.getEquipmentBrandList(exportReqVO);
        // 导出 Excel
        List<EquipmentBrandExcelVO> datas = EquipmentBrandConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备品牌管理.xls", "数据", EquipmentBrandExcelVO.class, datas);
    }

    @GetMapping("/equipmentTwoStage")
    @Operation(summary = "获得品牌型号两级")
    public CommonResult<List<EquipmentTreeRespVO>> getEquipmentTreeTwoStage() {
        return success(equipmentBrandService.getEquipmentTreeTwoStage());
    }

}
