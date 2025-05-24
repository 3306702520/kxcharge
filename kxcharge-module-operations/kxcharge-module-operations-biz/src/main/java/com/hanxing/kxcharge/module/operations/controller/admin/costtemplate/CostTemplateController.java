package com.hanxing.kxcharge.module.operations.controller.admin.costtemplate;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.*;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.CostTemplatePriceExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.CostTemplatePriceTypeExportReqVO;
import com.hanxing.kxcharge.module.operations.convert.costtemplate.CostTemplateConvert;
import com.hanxing.kxcharge.module.operations.convert.costtemplateprice.CostTemplatePriceConvert;
import com.hanxing.kxcharge.module.operations.convert.costtemplatepricetype.CostTemplatePriceTypeConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.service.costtemplate.CostTemplateService;
import com.hanxing.kxcharge.module.operations.service.costtemplateprice.CostTemplatePriceService;
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

@Tag(name = "管理后台 - 计费模板")
@RestController
@RequestMapping("/operations/cost-template")
@Validated
public class CostTemplateController {

    @Resource
    private CostTemplateService costTemplateService;

    @Resource
    private CostTemplatePriceService costTemplatePriceService;

    @Resource
    private CostTemplatePriceTypeService costTemplatePriceTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建计费模板")
    @PreAuthorize("@ss.hasPermission('operations:cost-template:create')")
    public CommonResult<Long> createCostTemplate(@Valid @RequestBody CostTemplateCreateReqVO createReqVO) {
        return success(costTemplateService.createCostTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新计费模板")
    @PreAuthorize("@ss.hasPermission('operations:cost-template:update')")
    public CommonResult<Boolean> updateCostTemplate(@Valid @RequestBody CostTemplateUpdateReqVO updateReqVO) {
        costTemplateService.updateCostTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计费模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:cost-template:delete')")
    public CommonResult<Boolean> deleteCostTemplate(@RequestParam("id") Long id) {
        Boolean result = costTemplateService.deleteCostTemplate(id);
        return success(result);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计费模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:cost-template:query')")
    public CommonResult<CostTemplateRespVO> getCostTemplate(@RequestParam("id") Long id) {

        CostTemplateDO costTemplate = costTemplateService.getCostTemplate(id);
        CostTemplateRespVO convert = CostTemplateConvert.INSTANCE.convert(costTemplate);

        CostTemplatePriceExportReqVO exportReqVO = new CostTemplatePriceExportReqVO();
        exportReqVO.setCostTemplateId(id);
        List<CostTemplatePriceDO> costTemplatePriceList = costTemplatePriceService.getCostTemplatePriceList(exportReqVO);
        convert.setCostTemplatePriceRespVOS(CostTemplatePriceConvert.INSTANCE.convertList(costTemplatePriceList));


        CostTemplatePriceTypeExportReqVO exportTypeReqVO = new CostTemplatePriceTypeExportReqVO();
        exportTypeReqVO.setCostTemplateId(id);
        List<CostTemplatePriceTypeDO> costTemplatePriceTypeList = costTemplatePriceTypeService.getCostTemplatePriceTypeList(exportTypeReqVO);
        convert.setCostTemplatePriceTypeRespVOS(CostTemplatePriceTypeConvert.INSTANCE.convertList(costTemplatePriceTypeList));

        return success(convert);
    }

    @GetMapping("/list")
    @Operation(summary = "获得计费模板列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:cost-template:query')")
    public CommonResult<List<CostTemplateRespVO>> getCostTemplateList(@RequestParam("ids") Collection<Long> ids) {
        List<CostTemplateDO> list = costTemplateService.getCostTemplateList(ids);
        return success(CostTemplateConvert.INSTANCE.convertList(list));
    }


    @GetMapping("/listAll")
    public CommonResult<List<CostTemplateRespVO>> getCostTemplateListAll() {
        CostTemplateExportReqVO exportReqVO = new CostTemplateExportReqVO();
        List<CostTemplateDO> list = costTemplateService.getCostTemplateList(exportReqVO);
        return success(CostTemplateConvert.INSTANCE.convertList(list));
    }


    @GetMapping("/page")
    @Operation(summary = "获得计费模板分页")
    @PreAuthorize("@ss.hasPermission('operations:cost-template:query')")
    public CommonResult<PageResult<CostTemplateRespVO>> getCostTemplatePage(@Valid CostTemplatePageReqVO pageVO) {
        PageResult<CostTemplateDO> pageResult = costTemplateService.getCostTemplatePage(pageVO);
        return success(CostTemplateConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计费模板 Excel")
    @PreAuthorize("@ss.hasPermission('operations:cost-template:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCostTemplateExcel(@Valid CostTemplateExportReqVO exportReqVO,
                                        HttpServletResponse response) throws IOException {
        List<CostTemplateDO> list = costTemplateService.getCostTemplateList(exportReqVO);
        // 导出 Excel
        List<CostTemplateExcelVO> datas = CostTemplateConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "计费模板.xls", "数据", CostTemplateExcelVO.class, datas);
    }

}
