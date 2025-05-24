package com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 计费模板价格时段 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CostTemplatePriceBaseVO {

    @Schema(description = "模板id", example = "32699")
    private Long costTemplateId;

    @Schema(description = "更新版本")
    private String protocolVersion;

    @Schema(description = "运营商id", example = "16991")
    private Long orgId;

    @Schema(description = "48段开始", example = "1")
    private Integer startTime;

    @Schema(description = "48段结束", example = "2")
    private Integer endTime;

    @Schema(description = "开始时间")
    private String startTimeStr;

    @Schema(description = "结束时间")
    private String endTimeStr;

    @Schema(description = "价格类型id", example = "10204")
    private String priceTypeId;

}
