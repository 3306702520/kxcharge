package com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.*;
import com.hanxing.kxcharge.module.operations.convert.costtemplateprice.CostTemplatePriceConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.service.costtemplateprice.CostTemplatePriceService;
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

@Tag(name = "管理后台 - 计费模板价格时段")
@RestController
@RequestMapping("/operations/cost-template-price")
@Validated
public class CostTemplatePriceController {

    @Resource
    private CostTemplatePriceService costTemplatePriceService;

    @PostMapping("/create")
    @Operation(summary = "创建计费模板价格时段")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price:create')")
    public CommonResult<Long> createCostTemplatePrice(@Valid @RequestBody CostTemplatePriceCreateReqVO createReqVO) {
        return success(costTemplatePriceService.createCostTemplatePrice(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新计费模板价格时段")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price:update')")
    public CommonResult<Boolean> updateCostTemplatePrice(@Valid @RequestBody CostTemplatePriceUpdateReqVO updateReqVO) {
        costTemplatePriceService.updateCostTemplatePrice(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计费模板价格时段")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price:delete')")
    public CommonResult<Boolean> deleteCostTemplatePrice(@RequestParam("id") Long id) {
        costTemplatePriceService.deleteCostTemplatePrice(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计费模板价格时段")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price:query')")
    public CommonResult<CostTemplatePriceRespVO> getCostTemplatePrice(@RequestParam("id") Long id) {
        CostTemplatePriceDO costTemplatePrice = costTemplatePriceService.getCostTemplatePrice(id);
        return success(CostTemplatePriceConvert.INSTANCE.convert(costTemplatePrice));
    }

    @GetMapping("/list")
    @Operation(summary = "获得计费模板价格时段列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price:query')")
    public CommonResult<List<CostTemplatePriceRespVO>> getCostTemplatePriceList(@RequestParam("ids") Collection<Long> ids) {
        List<CostTemplatePriceDO> list = costTemplatePriceService.getCostTemplatePriceList(ids);
        return success(CostTemplatePriceConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得计费模板价格时段分页")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price:query')")
    public CommonResult<PageResult<CostTemplatePriceRespVO>> getCostTemplatePricePage(@Valid CostTemplatePricePageReqVO pageVO) {
        PageResult<CostTemplatePriceDO> pageResult = costTemplatePriceService.getCostTemplatePricePage(pageVO);
        return success(CostTemplatePriceConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计费模板价格时段 Excel")
    @PreAuthorize("@ss.hasPermission('operations:cost-template-price:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCostTemplatePriceExcel(@Valid CostTemplatePriceExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<CostTemplatePriceDO> list = costTemplatePriceService.getCostTemplatePriceList(exportReqVO);
        // 导出 Excel
        List<CostTemplatePriceExcelVO> datas = CostTemplatePriceConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "计费模板价格时段.xls", "数据", CostTemplatePriceExcelVO.class, datas);
    }

}
