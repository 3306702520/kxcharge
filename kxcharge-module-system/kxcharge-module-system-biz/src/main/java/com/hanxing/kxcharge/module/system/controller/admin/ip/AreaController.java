package com.hanxing.kxcharge.module.system.controller.admin.ip;

import cn.hutool.core.lang.Assert;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.framework.ip.core.Area;
import com.hanxing.kxcharge.framework.ip.core.utils.AreaTownsUtils;
import com.hanxing.kxcharge.framework.ip.core.utils.AreaTwoStageUtils;
import com.hanxing.kxcharge.framework.ip.core.utils.AreaUtils;
import com.hanxing.kxcharge.framework.ip.core.utils.IPUtils;
import com.hanxing.kxcharge.module.system.controller.admin.ip.vo.AreaNodeRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 地区")
@RestController
@RequestMapping("/system/area")
@Validated
public class AreaController {

    @GetMapping("/tree")
    @Operation(summary = "获得地区树")
    public CommonResult<List<AreaNodeRespVO>> getAreaTree() {
        Area area = AreaUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "获取不到中国");
        return success(BeanUtils.toBean(area.getChildren(), AreaNodeRespVO.class));
    }

    @GetMapping("/treeTwoStage")
    @Operation(summary = "获得地区树两级")
    public CommonResult<List<AreaNodeRespVO>> getAreaTreeTwoStage() {
        Area area = AreaTwoStageUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "获取不到中国");
        return success(BeanUtils.toBean(area.getChildren(),AreaNodeRespVO.class));
    }


    @GetMapping("/treeTowns")
    @Operation(summary = "获得地区树四级")
    public CommonResult<List<AreaNodeRespVO>> getAreaTreeTowns() {
        Area area = AreaTownsUtils.getArea(Area.ID_CHINA);
        Assert.notNull(area, "获取不到中国");
        return success(BeanUtils.toBean(area.getChildren(),AreaNodeRespVO.class));
    }

    @GetMapping("/get-by-ip")
    @Operation(summary = "获得 IP 对应的地区名")
    @Parameter(name = "ip", description = "IP", required = true)
    public CommonResult<String> getAreaByIp(@RequestParam("ip") String ip) {
        // 获得城市
        Area area = IPUtils.getArea(ip);
        if (area == null) {
            return success("未知");
        }
        // 格式化返回
        return success(AreaUtils.format(area.getId()));
    }

}
