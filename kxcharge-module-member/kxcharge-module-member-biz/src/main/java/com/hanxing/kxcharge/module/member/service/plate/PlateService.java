package com.hanxing.kxcharge.module.member.service.plate;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.member.api.plate.dto.MemberPlateReqDTO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.*;
import com.hanxing.kxcharge.module.member.controller.app.plate.vo.AppPlateUpdateDefaultReqVO;
import com.hanxing.kxcharge.module.member.dal.dataobject.plate.PlateDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 用户车牌信息 Service 接口
 *
 * @author 小黑
 */
public interface PlateService {

    /**
     * 创建用户车牌信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPlate(@Valid PlateCreateReqVO createReqVO);

    /**
     * 更新用户车牌信息
     *
     * @param updateReqVO 更新信息
     */
    void updatePlate(@Valid PlateUpdateReqVO updateReqVO);

    /**
     * 删除用户车牌信息
     *
     * @param id 编号
     */
    void deletePlate(Long id);

    /**
     * 获得用户车牌信息
     *
     * @param id 编号
     * @return 用户车牌信息
     */
    PlateDO getPlate(Long id);

    /**
     * 获得用户车牌信息列表
     *
     * @param ids 编号
     * @return 用户车牌信息列表
     */
    List<PlateDO> getPlateList(Collection<Long> ids);

    /**
     * 获得用户车牌信息分页
     *
     * @param pageReqVO 分页查询
     * @return 用户车牌信息分页
     */
    PageResult<PlateDO> getPlatePage(PlatePageReqVO pageReqVO);

    /**
     * 获得用户车牌信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 用户车牌信息列表
     */
    List<PlateDO> getPlateList(PlateExportReqVO exportReqVO);

    /**
     * 设置默认车辆
     * @param updateReqVO
     */
    void updateDefaultPlate(PlateUpdateDefaultReqVO updateReqVO);

    /**
     * 设置默认车辆
     * @param updateReqVO
     */
    void updateDefaultPlate(AppPlateUpdateDefaultReqVO updateReqVO);

    /**
     * 新增会员车辆
     * @param userId
     * @param plateNumber
     * @return
     */
    void addPlateNumber(Long userId, String plateNumber);

    /**
     * 处理充电订单
     * @param memberPlateReqDTO
     */
    void chargeOrder(MemberPlateReqDTO memberPlateReqDTO);
}
