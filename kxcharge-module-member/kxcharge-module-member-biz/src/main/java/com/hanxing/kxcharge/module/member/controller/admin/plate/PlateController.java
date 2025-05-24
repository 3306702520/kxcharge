package com.hanxing.kxcharge.module.member.controller.admin.plate;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.*;
import com.hanxing.kxcharge.module.member.convert.plate.PlateConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.plate.PlateDO;
import com.hanxing.kxcharge.module.member.service.plate.PlateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 用户车牌信息")
@RestController
@RequestMapping("/member/plate")
@Validated
public class PlateController {

    @Resource
    private PlateService plateService;

    @PostMapping("/create")
    @Operation(summary = "创建用户车牌信息")
    public CommonResult<Long> createPlate(@Valid @RequestBody PlateCreateReqVO createReqVO) {
        return success(plateService.createPlate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户车牌信息")
    public CommonResult<Boolean> updatePlate(@Valid @RequestBody PlateUpdateReqVO updateReqVO) {
        plateService.updatePlate(updateReqVO);
        return success(true);
    }


    @PutMapping("/updateDefault")
    @Operation(summary = "设置默认")
    public CommonResult<Boolean> updateDefaultPlate(@Valid @RequestBody PlateUpdateDefaultReqVO updateDefaultReqVO) {
        plateService.updateDefaultPlate(updateDefaultReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户车牌信息")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deletePlate(@RequestParam("id") Long id) {
        plateService.deletePlate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户车牌信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PlateRespVO> getPlate(@RequestParam("id") Long id) {
        PlateDO plate = plateService.getPlate(id);
        return success(PlateConvert.INSTANCE.convert(plate));
    }

    @GetMapping("/list")
    @Operation(summary = "获得用户车牌信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    public CommonResult<List<PlateRespVO>> getPlateList(@RequestParam("ids") Collection<Long> ids) {
        List<PlateDO> list = plateService.getPlateList(ids);
        return success(PlateConvert.INSTANCE.convertList(list));
    }


    @GetMapping("/listAll")
    @Operation(summary = "获得所有用户车牌信息列表")
    public CommonResult<List<PlateRespVO>> getPlateListAll(Long userId) {
        PlateExportReqVO exportReqVO = new PlateExportReqVO();
        exportReqVO.setUserId(userId);
        List<PlateDO> list = plateService.getPlateList(exportReqVO);
        return success(PlateConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户车牌信息分页")
    public CommonResult<PageResult<PlateRespVO>> getPlatePage(@Valid PlatePageReqVO pageVO) {
        PageResult<PlateDO> pageResult = plateService.getPlatePage(pageVO);
        return success(PlateConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户车牌信息 Excel")
    public void exportPlateExcel(@Valid PlateExportReqVO exportReqVO,
                                 HttpServletResponse response) throws IOException {
        List<PlateDO> list = plateService.getPlateList(exportReqVO);
        // 导出 Excel
        List<PlateExcelVO> datas = PlateConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "用户车牌信息.xls", "数据", PlateExcelVO.class, datas);
    }

}
