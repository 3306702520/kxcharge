package com.hanxing.kxcharge.module.member.controller.app.plate;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.security.core.annotations.PreAuthenticated;
import com.hanxing.kxcharge.framework.security.core.util.SecurityFrameworkUtils;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateCreateReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateExportReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.PlateRespVO;
import com.hanxing.kxcharge.module.member.controller.app.plate.vo.AppPlateUpdateDefaultReqVO;
import com.hanxing.kxcharge.module.member.convert.plate.PlateConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.plate.PlateDO;
import com.hanxing.kxcharge.module.member.service.plate.PlateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 用户车牌信息")
@RestController
@RequestMapping("/member/plate")
@Validated
public class AppPlateController {

    @Resource
    private PlateService plateService;


    @GetMapping("/listAll")
    @Operation(summary = "获得所有用户车牌信息列表")
    public CommonResult<List<PlateRespVO>> getPlateListAll() {
        PlateExportReqVO exportReqVO = new PlateExportReqVO();
        exportReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        List<PlateDO> list = plateService.getPlateList(exportReqVO);
        return success(PlateConvert.INSTANCE.convertList(list));
    }


    /**
     * 新增
     *
     * @return
     */
    @PostMapping("/create")
    public CommonResult<Long> createPlate(@Valid @RequestBody PlateCreateReqVO createReqVO) {
        createReqVO.setUserId(SecurityFrameworkUtils.getLoginUserId());
        return success(plateService.createPlate(createReqVO));
    }


    @PutMapping("/updateDefault")
    @Operation(summary = "设置默认")
    public CommonResult<Boolean> updateDefaultPlate(@Valid @RequestBody AppPlateUpdateDefaultReqVO updateDefaultReqVO) {
        plateService.updateDefaultPlate(updateDefaultReqVO);
        return success(true);
    }

    /**
     * 新增
     *
     * @return
     */
    @DeleteMapping("/delete")
    @Operation(summary = "删除用户收件地址")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthenticated
    public CommonResult<Boolean> deletePlate(@RequestParam("id") Long id) {
        plateService.deletePlate(id);
        return success(true);
    }


}
