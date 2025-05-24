package com.hanxing.kxcharge.module.operations.api.interflow.dto;

import lombok.Data;

import java.util.List;

/**
 * 查询业务策略信息返回实体(操作结果通过Comresult的返回true或者flase决定，失败原因中不存在此充电桩业务策略不存在的情况)
 */
@Data
public class NotifacationEquipBusinessPolicyRespDto {

    private String connectorId;

    private Integer sumPeriod;

    private List<NotifacationEquipBusinessPolicyInfoRespDto> policyInfos;






}
