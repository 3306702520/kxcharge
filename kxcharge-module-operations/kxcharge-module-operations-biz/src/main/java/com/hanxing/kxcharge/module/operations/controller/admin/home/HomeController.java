package com.hanxing.kxcharge.module.operations.controller.admin.home;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.*;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.ChargeDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.GunDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TerminalOperationDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TimeDistributionDataRespVo;
import com.hanxing.kxcharge.module.operations.service.home.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 首页")
@RestController
@RequestMapping("/operations/home")
@Validated
public class HomeController {

    @Resource
    private HomeService homeService;



    @GetMapping("/chargeData")
    @Operation(summary = "首页报表数据，昨日今日数据")
    public CommonResult<ChargeDataRespVo> chargeData() {
       return success(homeService.chargeData());
    }


    @GetMapping("/gunData")
    @Operation(summary = "首页报表数据，实时枪数据")
    public CommonResult<GunDataRespVo> gunData() {
       return success(homeService.gunData());
    }


    @GetMapping("/terminalOperationData")
    @Operation(summary = "首页报表数据，场站经营数据，1今天 2昨天 3近30天，4近12个月")
    public CommonResult<List<TerminalOperationDataRespVo>> terminalOperationData(Integer type) {
       return success(homeService.terminalOperationData(type));
    }


    @GetMapping("/timeDistribution")
    @Operation(summary = "首页报表数据，时段分布，1今天 2昨天 3近30天，4近12个月")
    public CommonResult<List<TimeDistributionDataRespVo>> timeDistribution(Integer type) {
       return success(homeService.timeDistribution(type));
    }




}
