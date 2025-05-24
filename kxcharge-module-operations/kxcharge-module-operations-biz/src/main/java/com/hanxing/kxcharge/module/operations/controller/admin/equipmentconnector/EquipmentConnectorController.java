package com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector;

import cn.hutool.core.io.resource.ClassPathResource;
import com.hanxing.kxcharge.framework.apilog.core.annotation.ApiAccessLog;
import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.qrcode.QrcodeUtils;
import com.hanxing.kxcharge.framework.excel.core.util.ExcelUtils;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentRespVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.*;
import com.hanxing.kxcharge.module.operations.convert.equipment.EquipmentConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentbrand.EquipmentBrandConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentconnector.EquipmentConnectorConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentmodel.EquipmentModelConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.framework.kxcharge.config.KxchargeProperties;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import com.hanxing.kxcharge.module.operations.service.equipmentbrand.EquipmentBrandService;
import com.hanxing.kxcharge.module.operations.service.equipmentconnector.EquipmentConnectorService;
import com.hanxing.kxcharge.module.operations.service.equipmentmodel.EquipmentModelService;
import com.hanxing.kxcharge.module.system.api.tenant.TenantApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@Slf4j
@Tag(name = "管理后台 - 电枪管理")
@RestController
@RequestMapping("/operations/equipment-connector")
@Validated
public class EquipmentConnectorController {

    @Resource
    private EquipmentConnectorService equipmentConnectorService;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private EquipmentBrandService equipmentBrandService;
    @Resource
    private EquipmentModelService equipmentModelService;

    @Resource
    private KxchargeProperties kxchargeProperties;

    @Resource
    private TenantApi tenantApi;

    @PostMapping("/create")
    @Operation(summary = "创建电枪管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment:create')")
    public CommonResult<Long> createEquipmentConnector(@Valid @RequestBody EquipmentConnectorCreateReqVO createReqVO) {
        return success(equipmentConnectorService.createEquipmentConnector(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新电枪管理")
    @PreAuthorize("@ss.hasPermission('operations:equipment:update')")
    public CommonResult<Boolean> updateEquipmentConnector(@Valid @RequestBody EquipmentConnectorUpdateReqVO updateReqVO) {
        equipmentConnectorService.updateEquipmentConnector(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除电枪管理")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('operations:equipment:delete')")
    public CommonResult<Boolean> deleteEquipmentConnector(@RequestParam("id") Long id) {
        equipmentConnectorService.deleteEquipmentConnector(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得电枪管理")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('operations:equipment:query')")
    public CommonResult<EquipmentConnectorRespVO> getEquipmentConnector(@RequestParam("id") Long id) {

        EquipmentConnectorDO equipmentConnector = equipmentConnectorService.getEquipmentConnector(id);
        EquipmentConnectorRespVO equipmentConnectorRespVO = EquipmentConnectorConvert.INSTANCE.convert(equipmentConnector);

        EquipmentDO equipment = equipmentService.getEquipment(equipmentConnector.getEquipmentId());
        EquipmentRespVO respVO = EquipmentConvert.INSTANCE.convert(equipment);

        respVO.setEquipmentBrandRespVO(EquipmentBrandConvert.INSTANCE.convert(equipmentBrandService.getEquipmentBrand(respVO.getEquipmentBrandId())));
        respVO.setEquipmentModelRespVO(EquipmentModelConvert.INSTANCE.convert(equipmentModelService.getEquipmentModel(respVO.getEquipmentModelId())));

        equipmentConnectorRespVO.setEquipmentRespVO(respVO);

        try {
            String bgPath = "classpath:/static/bg.png";

            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(new ClassPathResource(bgPath).getStream());
            String codeImage = QrcodeUtils.getQRCodeImage("https://" + tenantApi.getWebsite().getData() + "/stser/?connector_id=" + equipmentConnectorRespVO.getConnectorCode(), 150, 150);
            equipmentConnectorRespVO.setCodeImage(QrcodeUtils.mergeImageToBase64(equipmentConnectorRespVO.getConnectorCode(),originalImage,codeImage,55,60));
        } catch (Exception e) {
            log.error("[expireCode][id({}) 获取二维码失败]", equipmentConnector.getId(), e);
        }

        return success(equipmentConnectorRespVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得电枪管理列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('operations:equipment:query')")
    public CommonResult<List<EquipmentConnectorRespVO>> getEquipmentConnectorList(@RequestParam("ids") Collection<Long> ids) {
        List<EquipmentConnectorDO> list = equipmentConnectorService.getEquipmentConnectorList(ids);
        return success(EquipmentConnectorConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得电枪管理分页")
    @PreAuthorize("@ss.hasPermission('operations:equipment:query')")
    public CommonResult<PageResult<EquipmentConnectorRespVO>> getEquipmentConnectorPage(@Valid EquipmentConnectorPageReqVO pageVO) {
        PageResult<EquipmentConnectorDO> pageResult = equipmentConnectorService.getEquipmentConnectorPage(pageVO);
        return success(EquipmentConnectorConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出电枪管理 Excel")
    @PreAuthorize("@ss.hasPermission('operations:equipment:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportEquipmentConnectorExcel(@Valid EquipmentConnectorExportReqVO exportReqVO,
                                              HttpServletResponse response) throws IOException {
        List<EquipmentConnectorDO> list = equipmentConnectorService.getEquipmentConnectorList(exportReqVO);
        // 导出 Excel
        List<EquipmentConnectorExcelVO> datas = EquipmentConnectorConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "电枪管理.xls", "数据", EquipmentConnectorExcelVO.class, datas);
    }






}
