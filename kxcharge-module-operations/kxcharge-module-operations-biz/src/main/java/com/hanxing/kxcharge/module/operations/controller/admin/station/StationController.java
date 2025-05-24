package com.hanxing.kxcharge.module.operations.controller.admin.station;

import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.*;
import com.hanxing.kxcharge.module.operations.convert.station.StationConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import com.hanxing.kxcharge.module.operations.service.station.StationService;
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

@Tag(name = "管理后台 - 站点信息")
@RestController
@RequestMapping("/operations/station")
@Validated
public class StationController {

    @Resource
    private StationService stationService;

    @PostMapping("/create")
    @Operation(summary = "创建站点信息")
    @PreAuthorize("@ss.hasPermission('operations:station:create')")
    public CommonResult<Long> createStation(@Valid @RequestBody StationCreateReqVO createReqVO) {
        return success(stationService.createStation(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新站点信息")
    @PreAuthorize("@ss.hasPermission('operations:station:update')")
    public CommonResult<Boolean> updateStation(@Valid @RequestBody StationUpdateReqVO updateReqVO) {
        stationService.updateStation(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除站点信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:station:delete')")
    public CommonResult<Boolean> deleteStation(@RequestParam("id") Long id) {
        stationService.deleteStation(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得站点信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:station:query')")
    public CommonResult<StationRespVO> getStation(@RequestParam("id") Long id) {
        StationDO station = stationService.getStation(id);
        return success(StationConvert.INSTANCE.convert(station));
    }

    @GetMapping("/list")
    @Operation(summary = "获得站点信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:station:query')")
    public CommonResult<List<StationRespVO>> getStationList(@RequestParam("ids") Collection<Long> ids) {
        List<StationDO> list = stationService.getStationList(ids);
        return success(StationConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/listAll")
    @Operation(summary = "获得所有站点信息列表")
    public CommonResult<List<StationRespVO>> getStationListAll() {
        StationExportReqVO exportReqVO = new StationExportReqVO();
        List<StationDO> list = stationService.getStationList(exportReqVO);
        return success(StationConvert.INSTANCE.convertList(list));
    }



    @GetMapping("/listAllByOrgId")
    @Operation(summary = "获得所有站点信息列表运营商底下")
    public CommonResult<List<StationRespVO>> getStationListAllByOrgId(@RequestParam("orgId") Long orgId) {
        StationExportReqVO exportReqVO = new StationExportReqVO();
        exportReqVO.setOrgId(orgId);
        List<StationDO> list = stationService.getStationList(exportReqVO);
        return success(StationConvert.INSTANCE.convertList(list));
    }


    @GetMapping("/page")
    @Operation(summary = "获得站点信息分页")
    @PreAuthorize("@ss.hasPermission('operations:station:query')")
    public CommonResult<PageResult<StationRespVO>> getStationPage(@Valid StationPageReqVO pageVO) {
        PageResult<StationDO> pageResult = stationService.getStationPage(pageVO);
        return success(StationConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出站点信息 Excel")
    @PreAuthorize("@ss.hasPermission('operations:station:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportStationExcel(@Valid StationExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<StationDO> list = stationService.getStationList(exportReqVO);
        // 导出 Excel
        List<StationExcelVO> datas = StationConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "站点信息.xls", "数据", StationExcelVO.class, datas);
    }

}
