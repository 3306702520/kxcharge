package com.hanxing.kxcharge.module.operations.api.billingmodel;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnore;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.BillingModelRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.BillingModelValidationRespDto;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotifacationEquipBusinessPolicyInfoRespDto;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotifacationEquipBusinessPolicyRespDto;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.service.costtemplate.CostTemplateService;
import com.hanxing.kxcharge.module.operations.service.costtemplateprice.CostTemplatePriceService;
import com.hanxing.kxcharge.module.operations.service.costtemplatepricetype.CostTemplatePriceTypeService;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Slf4j
public class BillingModelApiImpl implements BillingModelApi {


    @Resource
    private EquipmentService equipmentService;

    @Resource
    private CostTemplateService costTemplateService;


    //判断收费模型id是否正确,pileCode为设备编号, billingModelNo为收费模型id
    @Override
    public CommonResult<BillingModelValidationRespDto> validation(String pileCode,
                                                                  String billingModelNo) {
        log.info("收到模型验证请求");
        BillingModelValidationRespDto billingModelValidationRespDto = new BillingModelValidationRespDto();
        billingModelValidationRespDto.setBillingModelNo(billingModelNo);
        EquipmentDO equipment = equipmentService.getEquipmentByPileCode(pileCode);
        Long costTemplateId = equipment.getCostTemplateId();
        CostTemplateDO costTemplate = costTemplateService.getCostTemplate(costTemplateId);
        Long protocolVersion = costTemplate.getProtocolVersion();
        billingModelValidationRespDto.setVerificationResults(String.valueOf(protocolVersion).equals(billingModelNo) ? "00" : "01");

        return CommonResult.success(billingModelValidationRespDto);
    }


    //获取收费模型
    @Override
    public CommonResult<BillingModelRespDto> getModel(String pileCode) {
        log.info("收到获取收费模型请求");

        BillingModelRespDto billingModelRespDto = equipmentService.getCostTemplatePrice(pileCode);

        return CommonResult.success(billingModelRespDto);
    }

    @TenantIgnore
    @Override
    public CommonResult<NotifacationEquipBusinessPolicyRespDto> getPolicy(String connectorId) {
        EquipmentDO equipmentByPileCode = equipmentService.getEquipmentByPileCode(connectorId.substring(0, connectorId.length() - 2));
        List<CostTemplatePriceDO> costTemplatePriceByTemplateId = equipmentService.getCostTemplatePriceByTemplateId(equipmentByPileCode.getCostTemplateId());
        List<CostTemplatePriceTypeDO> costTemplatePriceTypeByTemplateId = equipmentService.getCostTemplatePriceTypeByTemplateId(equipmentByPileCode.getCostTemplateId());

        List<NotifacationEquipBusinessPolicyInfoRespDto> collect = costTemplatePriceByTemplateId
                .stream()
                .map(item -> {
                    NotifacationEquipBusinessPolicyInfoRespDto policyInfo = new NotifacationEquipBusinessPolicyInfoRespDto();
                    String startTimeStr = item.getStartTimeStr();
                    String hTimeStr = startTimeStr.substring(0, 2);
                    String mTimeStr = startTimeStr.substring(3, 5);
                    String startTime = hTimeStr + mTimeStr + "00";
                    policyInfo.setStartTime(startTime);
                    costTemplatePriceTypeByTemplateId
                            .stream()
                            .filter(otem -> {
                                return otem.getTypeName().equals(item.getPriceTypeId());
                            }).findAny().ifPresent(otem -> {
                        policyInfo.setServicePrice(otem.getPriceService().floatValue());
                        policyInfo.setElecPrice(otem.getPricePower().floatValue());
                    });
                    return policyInfo;
                }).collect(Collectors.toList());
        return CommonResult.success(new NotifacationEquipBusinessPolicyRespDto().setConnectorId(connectorId)
                .setSumPeriod(costTemplatePriceByTemplateId.size())
                .setPolicyInfos(collect));
    }


//    @Override
//    public CommonResult<TransactionConfirmationRespDto> getTransaction(TransactionRecordsReqDto dto){
//        return null;
//    }
}
