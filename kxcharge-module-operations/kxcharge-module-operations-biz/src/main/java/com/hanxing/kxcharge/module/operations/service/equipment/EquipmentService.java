package com.hanxing.kxcharge.module.operations.service.equipment;

import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.BillingModelRespDto;
import com.hanxing.kxcharge.module.operations.api.billingmodel.dto.PileLoginReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.*;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 电桩管理 Service 接口
 *
 * @author kaixin
 */
public interface EquipmentService {

    /**
     * 创建电桩管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createEquipment(@Valid EquipmentCreateReqVO createReqVO);

    /**
     * 更新电桩管理
     *
     * @param updateReqVO 更新信息
     */
    void updateEquipment(@Valid EquipmentUpdateReqVO updateReqVO);

    /**
     * 删除电桩管理
     *
     * @param id 编号
     */
    void deleteEquipment(Long id);

    /**
     * 获得电桩管理
     *
     * @param id 编号
     * @return 电桩管理
     */
    EquipmentDO getEquipment(Long id);

    /**
     * 获得电桩管理列表
     *
     * @param ids 编号
     * @return 电桩管理列表
     */
    List<EquipmentDO> getEquipmentList(Collection<Long> ids);

    /**
     * 获得电桩管理分页
     *
     * @param pageReqVO 分页查询
     * @return 电桩管理分页
     */
    PageResult<EquipmentDO> getEquipmentPage(EquipmentPageReqVO pageReqVO);

    /**
     * 获得电桩管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 电桩管理列表
     */
    List<EquipmentDO> getEquipmentList(EquipmentExportReqVO exportReqVO);

    /**
     * 充电桩桩体登陆，返回是否登录成功
     * @param pileLoginReqDto
     * @return
     */
    Boolean pileLogin(PileLoginReqDto pileLoginReqDto);


    /**
     * 根据桩编号,去获取设备
     * @param pileCode 桩编号
     * @return 电桩
     */
    EquipmentDO getEquipmentByPileCode(String pileCode);

    /**
     * 根据站点id更新模板id
     * @param equipmentUpdateReqVO
     */
    void batchUpdateCostTemplateId(EquipmentUpdateReqVO equipmentUpdateReqVO);

    /**
     * 根据桩编码获取发送给电桩的计费模型数据
     * @param pileCode
     * @return 发送给电桩的计费模型数据
     */
    BillingModelRespDto getCostTemplatePrice(String pileCode);

    /**
     * 根据桩id获取发送给电桩的计费模型数据
     * @param id
     * @return
     */
    BillingModelRespDto getCostTemplatePriceById(Long id);

    /**
     * 停用/启用，如果已经停用，那么就会被启用，如果已经启用，那么就会被停用
     * @param id 电桩电枪id
     * @return 是否成功
     */
    Boolean enable(Long id);

    /**
     * 远程对时
     * @param id 桩id
     * @return 是否成功
     */
    Boolean proofTime(Long id);

    /**
     * 远程对价
     * @param id
     * @return 是否成功
     */
    Boolean proofPrice(Long id);



    List<CostTemplatePriceDO> getCostTemplatePriceByTemplateId(Long costTemplateId);




    List<CostTemplatePriceTypeDO> getCostTemplatePriceTypeByTemplateId(Long costTemplateId);
}
