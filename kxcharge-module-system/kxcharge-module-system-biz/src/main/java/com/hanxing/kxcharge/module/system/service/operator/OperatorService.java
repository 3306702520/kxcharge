package com.hanxing.kxcharge.module.system.service.operator;

import java.util.*;
import javax.validation.*;
import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.*;
import com.hanxing.kxcharge.module.system.dal.dataobject.operator.OperatorDO;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.pojo.PageParam;

/**
 * 运营商 Service 接口
 *
 * @author 航兴新能源
 */
public interface OperatorService {

    /**
     * 创建运营商
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOperator(@Valid OperatorSaveReqVO createReqVO);

    /**
     * 更新运营商
     *
     * @param updateReqVO 更新信息
     */
    void updateOperator(@Valid OperatorSaveReqVO updateReqVO);

    /**
     * 删除运营商
     *
     * @param id 编号
     */
    void deleteOperator(Long id);

    /**
     * 获得运营商
     *
     * @param id 编号
     * @return 运营商
     */
    OperatorDO getOperator(Long id);

    /**
     * 获得运营商分页
     *
     * @param pageReqVO 分页查询
     * @return 运营商分页
     */
    PageResult<OperatorDO> getOperatorPage(OperatorPageReqVO pageReqVO);

}