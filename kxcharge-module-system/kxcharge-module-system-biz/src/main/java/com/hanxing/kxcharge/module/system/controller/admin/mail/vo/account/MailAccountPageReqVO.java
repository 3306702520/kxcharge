package com.hanxing.kxcharge.module.system.controller.admin.mail.vo.account;

import com.hanxing.kxcharge.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 邮箱账号分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailAccountPageReqVO extends PageParam {

    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "kxchargeyuanma@123.com")
    private String mail;

    @Schema(description = "用户名" , requiredMode = Schema.RequiredMode.REQUIRED , example = "kxcharge")
    private String username;

}
