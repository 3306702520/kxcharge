package com.hanxing.kxcharge.module.operations.service.equipmentconnector;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentconnector.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 电枪管理 Service 接口
 *
 * @author kaixin
 */
public interface EquipmentConnectorService {

    /**
     * 创建电枪管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEquipmentConnector(@Valid EquipmentConnectorCreateReqVO createReqVO);

    /**
     * 更新电枪管理
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipmentConnector(@Valid EquipmentConnectorUpdateReqVO updateReqVO);

    /**
     * 删除电枪管理
     *
     * @param id 编号
     */
    void deleteEquipmentConnector(Long id);

    /**
     * 获得电枪管理
     *
     * @param id 编号
     * @return 电枪管理
     */
    EquipmentConnectorDO getEquipmentConnector(Long id);


    /**
     * 获得电枪管理通过
     *
     * @param connectorCode 编号
     * @return 电枪管理
     */
    EquipmentConnectorDO getEquipmentConnector(String connectorCode);


    /**
     * 获得电枪管理通过（无需租户）
     *
     * @param connectorCode 编号
     * @return 电枪管理
     */
    EquipmentConnectorDO getEquipmentConnectorTenantless(String connectorCode);

    /**
     * 获得电枪管理列表
     *
     * @param ids 编号
     * @return 电枪管理列表
     */
    List<EquipmentConnectorDO> getEquipmentConnectorList(Collection<Long> ids);

    /**
     * 获得电枪管理分页
     *
     * @param pageReqVO 分页查询
     * @return 电枪管理分页
     */
    PageResult<EquipmentConnectorDO> getEquipmentConnectorPage(EquipmentConnectorPageReqVO pageReqVO);

    /**
     * 获得电枪管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 电枪管理列表
     */
    List<EquipmentConnectorDO> getEquipmentConnectorList(EquipmentConnectorExportReqVO exportReqVO);

    /**
     * @param pileCode 电桩编号 14位
     * @param gunCode 枪编号 2 位
     * @param orderNumber 交易流水号 32位
     * @param logicNumber 逻辑卡号 16位
     * @param physicalNumber 物理卡号 16位
     * @param money 余额 8位
     */
    void startCharge(String pileCode, String gunCode,String orderNumber, String logicNumber, String physicalNumber, BigDecimal money);


    /**
     * 结束充电
     * @param pileCode 电桩编号 14位
     * @param gunId 枪号 2位
     */
    void endCharge(String pileCode, String gunId);

}
