package com.hanxing.kxcharge.module.member.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "RPC 服务 - 用户信息 Response DTO")
@Data
public class MemberUserRespDTO {

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "昵称", example = "小王同学")
    private String nickname;

    @Schema(description = "帐号状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status; // 参见 CommonStatusEnum 枚举

    @Schema(description = "用户头像", example = "https://www.xxx.cn/xxx.jpg")
    private String avatar;

    @Schema(description = "手机号", example = "15601691300")
    private String mobile;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== 其它信息 ==========

    @Schema(description = "会员级别编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long levelId;

    @Schema(description = "积分", requiredMode = Schema.RequiredMode.REQUIRED, example = "886")
    private Integer point;

    /**
     * 企业id
     */
    @Schema(description = "企业id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long companyId;

    /**
     * 企业用户标识
     */
    @Schema(description = "企业用户标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer unit;
    /**
     * 运营商id
     */
    @Schema(description = "运营商id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long orgId;


    @Schema(description = "账户", requiredMode = Schema.RequiredMode.REQUIRED)
    private String account;

}
