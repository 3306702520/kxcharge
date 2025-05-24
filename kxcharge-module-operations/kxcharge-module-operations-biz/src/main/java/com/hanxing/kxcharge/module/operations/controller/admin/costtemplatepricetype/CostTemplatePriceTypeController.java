package com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.*;
import com.hanxing.kxcharge.module.operations.convert.costtemplatepricetype.CostTemplatePriceTypeConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.service.costtemplatepricetype.CostTemplatePriceTypeService;
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

@Tag(name = "管理后台 - 计费模板价格类别")
@RestController
@RequestMapping("/operations/cost-template-price-type")
@Validated
public class CostTemplatePriceTypeController {

    @Resource
    private CostTemplatePriceTypeService costTemplatePriceTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建计费模板价格类别")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price-type:create')")
    public CommonResult<Long> createCostTemplatePriceType(@Valid @RequestBody CostTemplatePriceTypeCreateReqVO createReqVO) {
        return success(costTemplatePriceTypeService.createCostTemplatePriceType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新计费模板价格类别")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price-type:update')")
    public CommonResult<Boolean> updateCostTemplatePriceType(@Valid @RequestBody CostTemplatePriceTypeUpdateReqVO updateReqVO) {
        costTemplatePriceTypeService.updateCostTemplatePriceType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计费模板价格类别")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price-type:delete')")
    public CommonResult<Boolean> deleteCostTemplatePriceType(@RequestParam("id") Long id) {
        costTemplatePriceTypeService.deleteCostTemplatePriceType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计费模板价格类别")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price-type:query')")
    public CommonResult<CostTemplatePriceTypeRespVO> getCostTemplatePriceType(@RequestParam("id") Long id) {
        CostTemplatePriceTypeDO costTemplatePriceType = costTemplatePriceTypeService.getCostTemplatePriceType(id);
        return success(CostTemplatePriceTypeConvert.INSTANCE.convert(costTemplatePriceType));
    }

    @GetMapping("/list")
    @Operation(summary = "获得计费模板价格类别列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price-type:query')")
    public CommonResult<List<CostTemplatePriceTypeRespVO>> getCostTemplatePriceTypeList(@RequestParam("ids") Collection<Long> ids) {
        List<CostTemplatePriceTypeDO> list = costTemplatePriceTypeService.getCostTemplatePriceTypeList(ids);
        return success(CostTemplatePriceTypeConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得计费模板价格类别分页")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price-type:query')")
    public CommonResult<PageResult<CostTemplatePriceTypeRespVO>> getCostTemplatePriceTypePage(@Valid CostTemplatePriceTypePageReqVO pageVO) {
        PageResult<CostTemplatePriceTypeDO> pageResult = costTemplatePriceTypeService.getCostTemplatePriceTypePage(pageVO);
        return success(CostTemplatePriceTypeConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计费模板价格类别 Excel")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price-type:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCostTemplatePriceTypeExcel(@Valid CostTemplatePriceTypeExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CostTemplatePriceTypeDO> list = costTemplatePriceTypeService.getCostTemplatePriceTypeList(exportReqVO);
        // 导出 Excel
        List<CostTemplatePriceTypeExcelVO> datas = CostTemplatePriceTypeConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "计费模板价格类别.xls", "数据", CostTemplatePriceTypeExcelVO.class, datas);
    }

}
