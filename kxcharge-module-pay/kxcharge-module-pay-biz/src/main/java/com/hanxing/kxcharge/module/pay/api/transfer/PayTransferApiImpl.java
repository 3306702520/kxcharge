package com.hanxing.kxcharge.module.pay.api.transfer;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.pay.api.transfer.dto.PayTransferCreateReqDTO;
import com.hanxing.kxcharge.module.pay.service.transfer.PayTransferService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

/**
 * 转账单 API 实现类
 *
 * @author jason
 */
@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class PayTransferApiImpl implements PayTransferApi {

    @Resource
    private PayTransferService payTransferService;

    @Override
    public CommonResult<Long> createTransfer(PayTransferCreateReqDTO reqDTO) {
        return success(payTransferService.createTransfer(reqDTO));
    }

}
