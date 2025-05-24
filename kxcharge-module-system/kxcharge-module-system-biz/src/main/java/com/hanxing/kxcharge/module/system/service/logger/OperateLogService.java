package com.hanxing.kxcharge.module.system.service.logger;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.system.api.logger.dto.OperateLogCreateReqDTO;
import com.hanxing.kxcharge.module.system.api.logger.dto.OperateLogPageReqDTO;
import com.hanxing.kxcharge.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import com.hanxing.kxcharge.module.system.dal.dataobject.logger.OperateLogDO;

/**
 * 操作日志 Service 接口
 *
 * @author 航兴新能源
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 创建请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqDTO pageReqVO);

}
