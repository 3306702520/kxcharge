package com.hanxing.kxcharge.module.operations.service.station;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.*;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.EquipmentConnectorInfoVO;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.StationInfoVO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * 站点信息 Service 接口
 *
 * @author kaixin
 */
public interface StationService {

    /**
     * 创建站点信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createStation(@Valid StationCreateReqVO createReqVO);

    /**
     * 更新站点信息
     *
     * @param updateReqVO 更新信息
     */
    void updateStation(@Valid StationUpdateReqVO updateReqVO);

    /**
     * 删除站点信息
     *
     * @param id 编号
     */
    void deleteStation(Long id);

    /**
     * 获得站点信息
     *
     * @param id 编号
     * @return 站点信息
     */
    StationDO getStation(Long id);

    /**
     * 获得站点信息列表
     *
     * @param ids 编号
     * @return 站点信息列表
     */
    List<StationDO> getStationList(Collection<Long> ids);

    /**
     * 获得站点信息分页
     *
     * @param pageReqVO 分页查询
     * @return 站点信息分页
     */
    PageResult<StationDO> getStationPage(StationPageReqVO pageReqVO);

    /**
     * 获得站点信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 站点信息列表
     */
    List<StationDO> getStationList(StationExportReqVO exportReqVO);

    /**
     *
     * @param stationName 站点名称
     * @param longitude 经度
     * @param latitude 维度
     * @param rank 排序方式
     * @return 站点信息列表
     */
    List<StationInfoVO> selectListByItude(String stationName, BigDecimal longitude, BigDecimal latitude, String rank);

    /**
     * 获取站点信息
     *
     * @param id        站点id
     * @param longitude
     * @param latitude
     * @return 站点信息(含计费信息 + 电桩信息)
     */
    StationInfoVO selectInfoById(Long id, BigDecimal longitude, BigDecimal latitude);

    /**
     * 获取枪信息
     * @param id
     * @return
     */
    EquipmentConnectorInfoVO selectConnectorInfoById(String id);

    /**
     * 获取所有枪信息
     * @param id
     * @return
     */
    List<EquipmentConnectorInfoVO> gunList(Long id);
}
