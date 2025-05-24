package com.hanxing.kxcharge.module.member.controller.app.user.vo;

import com.hanxing.kxcharge.framework.common.validation.InEnum;
import com.hanxing.kxcharge.module.system.enums.common.SexEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Schema(description = "用户 App - 会员用户更新 Request VO")
@Data
public class AppMemberUserUpdateReqVO {

    @Schema(description = "用户昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String nickname;

    @Schema(description = "头像", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn/x.png")
    @URL(message = "头像必须是 URL 格式")
    private String avatar;

    @Schema(description = "性别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sex;

}
