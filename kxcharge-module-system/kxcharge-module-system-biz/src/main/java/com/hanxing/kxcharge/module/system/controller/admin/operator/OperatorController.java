package com.hanxing.kxcharge.module.system.controller.admin.operator;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.*;

import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.*;
import com.hanxing.kxcharge.module.system.dal.dataobject.operator.OperatorDO;
import com.hanxing.kxcharge.module.system.service.operator.OperatorService;

@Tag(name = "管理后台 - 运营商")
@RestController
@RequestMapping("/system/operator")
@Validated
public class OperatorController {

    @Resource
    private OperatorService operatorService;

    @PostMapping("/create")
    @Operation(summary = "创建运营商")
    @PreAuthorize("@ss.hasPermission('system:operator:create')")
    public CommonResult<Long> createOperator(@Valid @RequestBody OperatorSaveReqVO createReqVO) {
        return success(operatorService.createOperator(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新运营商")
    @PreAuthorize("@ss.hasPermission('system:operator:update')")
    public CommonResult<Boolean> updateOperator(@Valid @RequestBody OperatorSaveReqVO updateReqVO) {
        operatorService.updateOperator(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除运营商")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('system:operator:delete')")
    public CommonResult<Boolean> deleteOperator(@RequestParam("id") Long id) {
        operatorService.deleteOperator(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得运营商")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('system:operator:query')")
    public CommonResult<OperatorRespVO> getOperator(@RequestParam("id") Long id) {
        OperatorDO operator = operatorService.getOperator(id);
        return success(BeanUtils.toBean(operator, OperatorRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得运营商分页")
    @PreAuthorize("@ss.hasPermission('system:operator:query')")
    public CommonResult<PageResult<OperatorRespVO>> getOperatorPage(@Valid OperatorPageReqVO pageReqVO) {
        PageResult<OperatorDO> pageResult = operatorService.getOperatorPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperatorRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出运营商 Excel")
    @PreAuthorize("@ss.hasPermission('system:operator:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperatorExcel(@Valid OperatorPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperatorDO> list = operatorService.getOperatorPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "运营商.xls", "数据", OperatorRespVO.class,
                        BeanUtils.toBean(list, OperatorRespVO.class));
    }

}