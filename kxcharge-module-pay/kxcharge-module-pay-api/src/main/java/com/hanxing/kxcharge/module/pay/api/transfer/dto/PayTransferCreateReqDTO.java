package com.hanxing.kxcharge.module.pay.api.transfer.dto;

import com.hanxing.kxcharge.framework.common.validation.InEnum;
import com.hanxing.kxcharge.module.pay.enums.transfer.PayTransferTypeEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 转账单创建 Request DTO
 *
 * @author jason
 */
@Data
public class PayTransferCreateReqDTO {

    /**
     * 应用标识
     */
    @NotNull(message = "应用标识不能为空")
    private String appKey;

    @NotEmpty(message = "转账渠道不能为空")
    private String channelCode;

    /**
     * 转账渠道的额外参数
     */
    private Map<String, String> channelExtras;

    @NotEmpty(message = "用户 IP 不能为空")
    private String userIp;

    /**
     * 类型
     */
    @NotNull(message = "转账类型不能为空")
    @InEnum(PayTransferTypeEnum.class)
    private Integer type;


    /**
     * 商户转账单编号
     */
    @NotEmpty(message = "商户转账单编号能为空")
    private String merchantTransferId;

    /**
     * 转账金额，单位：分
     */
    @Min(value = 1, message = "转账金额必须大于零")
    @NotNull(message = "转账金额不能为空")
    private Integer price;

    /**
     * 转账标题
     */
    @NotEmpty(message = "转账标题不能为空")
    private String subject;

    /**
     * 收款人姓名
     */
    @NotBlank(message = "收款人姓名不能为空", groups = {PayTransferTypeEnum.Alipay.class})
    private String userName;

    @NotBlank(message = "支付宝登录号不能为空", groups = {PayTransferTypeEnum.Alipay.class})
    private String alipayLogonId;

    // ========== 微信转账相关字段 ==========
    @NotBlank(message = "微信 openId 不能为空", groups = {PayTransferTypeEnum.WxPay.class})
    private String openid;
}
