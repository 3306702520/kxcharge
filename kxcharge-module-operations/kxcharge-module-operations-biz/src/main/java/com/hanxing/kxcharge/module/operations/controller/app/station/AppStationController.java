package com.hanxing.kxcharge.module.operations.controller.app.station;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.EquipmentConnectorInfoVO;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.StationInfoVO;
import com.hanxing.kxcharge.module.operations.service.station.StationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@Tag(name = "app - 站点信息")
@RestController
@RequestMapping("/operations/app/station")
@Validated
public class AppStationController {


    @Resource
    StationService stationService;


    @GetMapping("/info")
    public CommonResult<List<StationInfoVO>> stationInfo(@RequestParam(value = "stationName", required = false) String stationName, @RequestParam(value = "longitude") BigDecimal longitude, @RequestParam(value = "latitude") BigDecimal latitude, @RequestParam(value = "rank", required = false, defaultValue = "distance") String rank) {
        return CommonResult.success(stationService.selectListByItude(stationName, longitude, latitude, rank));
    }


    @GetMapping("/{id}")
    public CommonResult<StationInfoVO> stationInfo(@PathVariable Long id, @RequestParam(value = "longitude") BigDecimal longitude, @RequestParam(value = "latitude") BigDecimal latitude) {
        return CommonResult.success(stationService.selectInfoById(id, longitude, latitude));
    }

    /**
     * 枪详情
     */
    @GetMapping("/connectorInfo/{id}")
    public CommonResult<EquipmentConnectorInfoVO> connectorInfo(@NotNull(message = "主键不能为空") @PathVariable String id) {
        return CommonResult.success(stationService.selectConnectorInfoById(id));
    }


    @GetMapping("/gunList")
    public CommonResult<List<EquipmentConnectorInfoVO>> gunList(@RequestParam(value = "id") Long id) {
        return CommonResult.success(stationService.gunList(id));
    }

}
