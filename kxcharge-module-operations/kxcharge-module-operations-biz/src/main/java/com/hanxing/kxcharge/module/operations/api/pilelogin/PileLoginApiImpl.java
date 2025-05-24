package com.hanxing.kxcharge.module.operations.api.pilelogin;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.PileLoginApi;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.PileLoginReqDto;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
@Slf4j
public class PileLoginApiImpl implements PileLoginApi {

    @Autowired
    EquipmentService equipmentService;

    /**
     * 是否登录的逻辑判断
     * @param pileLoginReqDto
     * @return
     */
    @Override
    public CommonResult<Boolean> pileLogin(PileLoginReqDto pileLoginReqDto) {
        log.info("成功收到登录请求");
        return CommonResult.success(equipmentService.pileLogin(pileLoginReqDto));
    }
}
