package com.hanxing.kxcharge.module.operations.service.home;

import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.ChargeDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.GunDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TerminalOperationDataRespVo;
import com.hanxing.kxcharge.module.operations.controller.admin.home.vo.TimeDistributionDataRespVo;

import java.util.List;

public interface HomeService {


    /**
     * 充电数据
     * @return
     */
    ChargeDataRespVo chargeData();

    /**
     * 枪数据
     * @return
     */
    GunDataRespVo gunData();

    /**
     * 站点经营数据
     * @param type
     * @return
     */
    List<TerminalOperationDataRespVo> terminalOperationData(Integer type);

    /**
     * 站点分布数据
     * @param type
     * @return
     */
    List<TimeDistributionDataRespVo> timeDistribution(Integer type);
}
