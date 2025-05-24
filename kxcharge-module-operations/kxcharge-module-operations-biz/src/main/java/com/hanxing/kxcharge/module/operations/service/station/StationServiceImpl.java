package com.hanxing.kxcharge.module.operations.service.station;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationStationChangeReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.CostTemplateRespVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplateprice.vo.CostTemplatePriceExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplatepricetype.vo.CostTemplatePriceTypeExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipment.vo.EquipmentUpdateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.equipmentmodel.vo.EquipmentModelExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.StationCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.StationExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.StationPageReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.station.vo.StationUpdateReqVO;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.EquipmentConnectorInfoVO;
import com.hanxing.kxcharge.module.operations.controller.app.station.vo.StationInfoVO;
import com.hanxing.kxcharge.module.operations.convert.costtemplate.CostTemplateConvert;
import com.hanxing.kxcharge.module.operations.convert.costtemplateprice.CostTemplatePriceConvert;
import com.hanxing.kxcharge.module.operations.convert.costtemplatepricetype.CostTemplatePriceTypeConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentbrand.EquipmentBrandConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentconnector.EquipmentConnectorConvert;
import com.hanxing.kxcharge.module.operations.convert.equipmentmodel.EquipmentModelConvert;
import com.hanxing.kxcharge.module.operations.convert.station.StationConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipmentconnector.EquipmentConnectorMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.station.StationMapper;
import com.hanxing.kxcharge.module.operations.mq.producer.interflow.InteconnectedProducer;
import com.hanxing.kxcharge.module.operations.service.costtemplate.CostTemplateService;
import com.hanxing.kxcharge.module.operations.service.costtemplateprice.CostTemplatePriceService;
import com.hanxing.kxcharge.module.operations.service.costtemplatepricetype.CostTemplatePriceTypeService;
import com.hanxing.kxcharge.module.operations.service.equipment.EquipmentService;
import com.hanxing.kxcharge.module.operations.service.equipmentbrand.EquipmentBrandService;
import com.hanxing.kxcharge.module.operations.service.equipmentmodel.EquipmentModelService;
import com.hanxing.kxcharge.module.operations.service.onlineequipmentconnector.OnlineEquipmentConnectorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils.convertList;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.STATION_NOT_EXISTS;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.STATION_UNABLE_DELETE;

/**
 * 站点信息 Service 实现类
 *
 * @author kaixin
 */
@Service
@Validated
public class StationServiceImpl implements StationService {

    @Resource
    private EquipmentBrandService equipmentBrandService;

    @Resource
    private EquipmentModelService equipmentModelService;

    @Resource
    private EquipmentService equipmentService;

    @Resource
    private StationMapper stationMapper;

    @Resource
    private CostTemplateService costTemplateService;

    @Resource
    private EquipmentConnectorMapper equipmentConnectorMapper;

    @Resource
    private OnlineEquipmentConnectorService onlineEquipmentConnectorService;

    @Resource
    private CostTemplatePriceService costTemplatePriceService;

    @Resource
    private CostTemplatePriceTypeService costTemplatePriceTypeService;

    @Resource
    InteconnectedProducer inteconnectedProducer;


    @Override
    public Long createStation(StationCreateReqVO createReqVO) {
        // 插入
        StationDO station = StationConvert.INSTANCE.convert(createReqVO);
        stationMapper.insert(station);
        // 返回
        return station.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStation(StationUpdateReqVO updateReqVO) {
        // 校验存在
        StationDO stationDO = stationMapper.selectById(updateReqVO.getId());
        if (stationDO == null) {
            throw exception(STATION_NOT_EXISTS);
        }
        // 判断是否变更了计费规则
        if (!stationDO.getCostTemplateId().equals(updateReqVO.getCostTemplateId())) {
            NotificationStationChangeReqDto stationChangeReqDto = new NotificationStationChangeReqDto();
            stationChangeReqDto.setStationId(stationDO.getId());
            stationChangeReqDto.setType(3);
            inteconnectedProducer.sendNotificationStationChange(stationChangeReqDto);
        }
        // 更新
        StationDO updateObj = StationConvert.INSTANCE.convert(updateReqVO);
        stationMapper.updateById(updateObj);
        //判断该站点底下是否有枪号，对计费模板进行更新
        EquipmentUpdateReqVO equipmentUpdateReqVO = new EquipmentUpdateReqVO();
        equipmentUpdateReqVO.setCostTemplateId(updateObj.getCostTemplateId());
        equipmentUpdateReqVO.setStationId(updateObj.getId());
        equipmentService.batchUpdateCostTemplateId(equipmentUpdateReqVO);
    }

    @Override
    public void deleteStation(Long id) {
        // 校验存在
        validateStationExists(id);
        // 校验是否存在未删除的桩信息
        validateEquipmentExists(id);
        // 删除
        stationMapper.deleteById(id);
    }

    private void validateEquipmentExists(Long id) {
        EquipmentExportReqVO exportReqVO = new EquipmentExportReqVO();
        exportReqVO.setStationId(id);
        List<EquipmentDO> equipmentList = equipmentService.getEquipmentList(exportReqVO);
        if (!CollectionUtils.isAnyEmpty(equipmentList)) {
            throw exception(STATION_UNABLE_DELETE);
        }
    }

    private void validateStationExists(Long id) {
        if (stationMapper.selectById(id) == null) {
            throw exception(STATION_NOT_EXISTS);
        }
    }

    @Override
    public StationDO getStation(Long id) {
        return stationMapper.selectById(id);
    }

    @Override
    public List<StationDO> getStationList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return stationMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<StationDO> getStationPage(StationPageReqVO pageReqVO) {
        return stationMapper.selectPage(pageReqVO);
    }

    @Override
    public List<StationDO> getStationList(StationExportReqVO exportReqVO) {
        return stationMapper.selectList(exportReqVO);
    }

    /**
     * @param stationName 站点名称
     * @param longitude   经度
     * @param latitude    维度
     * @param rank        排序方式
     * @return 站点信息列表
     */
    @Override
    public List<StationInfoVO> selectListByItude(String stationName, BigDecimal longitude, BigDecimal latitude, String rank) {
        List<StationDO> stationDOS = stationMapper.selectList(new QueryWrapper<StationDO>().lambda()
                .like(StringUtils.isNotEmpty(stationName), StationDO::getStationName, stationName)
                .eq(StationDO::getStationStatus, 1));

        //查询一次所有的平台
        List<EquipmentModelDO> equipmentModelList = equipmentModelService.getEquipmentModelList(new EquipmentModelExportReqVO());

        List<StationInfoVO> collect = stationDOS.stream()
                .map(item -> {
                    //获取距离
                    StationInfoVO stationInfoVO = StationConvert.INSTANCE.convertInfo(item);
                    double v = calculateDistance(latitude, longitude, stationInfoVO.getStationLat(), stationInfoVO.getStationLng());
                    stationInfoVO.setDistance(v);
                    //获取计费规则
                    BigDecimal nowPrice = costTemplateService.getNowPrice(stationInfoVO.getCostTemplateId());
                    stationInfoVO.setCurrentPrice(nowPrice);

                    //获取桩数+空闲桩数
                    List<EquipmentConnectorDO> equipmentConnectorDOS = equipmentConnectorMapper.selectList(new QueryWrapper<EquipmentConnectorDO>()
                            .lambda()
                            .eq(EquipmentConnectorDO::getStationId, stationInfoVO.getId()));

                    int slowCountNum = 0;
                    int countNum = 0;
                    int slowLeisureConnectorNumber = 0;
                    int leisureConnectorNumber = 0;
                    for (EquipmentConnectorDO equipmentConnectorDO : equipmentConnectorDOS) {
                        for (EquipmentModelDO equipmentModelDO : equipmentModelList) {
                            if (equipmentModelDO.getId().equals(equipmentConnectorDO.getEquipmentModelId())) {
                                if (2 == equipmentModelDO.getType()) {
                                    if (onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorDO.getConnectorCode()) == 2) {
                                        slowLeisureConnectorNumber = slowLeisureConnectorNumber + 1;
                                    }
                                    slowCountNum = slowCountNum + 1;
                                } else {
                                    if (onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorDO.getConnectorCode()) == 2) {
                                        leisureConnectorNumber = leisureConnectorNumber + 1;
                                    }
                                    countNum = countNum + 1;
                                }
                            }
                        }
                    }

                    stationInfoVO.setConnectorNumber(countNum);
                    stationInfoVO.setSlowConnectorNumber(slowCountNum);

                    stationInfoVO.setLeisureConnectorNumber(leisureConnectorNumber);
                    stationInfoVO.setSlowLeisureConnectorNumber(slowLeisureConnectorNumber);
                    return stationInfoVO;
                })
                .collect(Collectors.toList());
        if ("distance".equals(rank)) {
            return collect.stream().sorted(Comparator.comparing(StationInfoVO::getDistance)).collect(Collectors.toList());
        } else {
            return collect.stream().sorted(Comparator.comparing(StationInfoVO::getLeisureConnectorNumber).reversed()).collect(Collectors.toList());
        }
    }

    @Override
    public StationInfoVO selectInfoById(Long id, BigDecimal longitude, BigDecimal latitude) {
        //查询站点
        StationDO stationDO = stationMapper.selectById(id);

        //获取计费规则
        StationInfoVO stationInfoVO = fetchBillingRule(stationDO);


        double v = calculateDistance(latitude, longitude, stationInfoVO.getStationLat(), stationInfoVO.getStationLng());
        stationInfoVO.setDistance(v);

        //设置电枪列表
        List<EquipmentConnectorDO> equipmentConnectorDOS = equipmentConnectorMapper.selectList(new QueryWrapper<EquipmentConnectorDO>().lambda()
                .eq(EquipmentConnectorDO::getStationId, id));

        // 获得拼接需要的数据品牌
        Collection<Long> brandIds = convertList(equipmentConnectorDOS, EquipmentConnectorDO::getEquipmentBrandId);
        Map<Long, EquipmentBrandDO> brandMap = equipmentBrandService.getBrandMap(brandIds);

        // 类型
        Collection<Long> modelIds = convertList(equipmentConnectorDOS, EquipmentConnectorDO::getEquipmentModelId);
        Map<Long, EquipmentModelDO> modelMap = equipmentModelService.getModelMap(modelIds);

        List<EquipmentConnectorInfoVO> collect = equipmentConnectorDOS.stream().map(item -> {
            EquipmentConnectorInfoVO equipmentConnectorInfoVO = EquipmentConnectorConvert.INSTANCE.convertInfoVO(item);
            Integer gunWorkState = onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorInfoVO.getConnectorCode());
            equipmentConnectorInfoVO.setWorkState(gunWorkState);
            equipmentConnectorInfoVO.setEquipmentBrandRespVO(EquipmentBrandConvert.INSTANCE.convert(brandMap.get(item.getEquipmentBrandId())));
            equipmentConnectorInfoVO.setEquipmentModelRespVO(EquipmentModelConvert.INSTANCE.convert(modelMap.get(item.getEquipmentModelId())));

            return equipmentConnectorInfoVO;
        }).sorted(Comparator.comparing(EquipmentConnectorInfoVO::getConnectorCode)).collect(Collectors.toList());
        stationInfoVO.setEquipmentConnectorInfoVOS(collect);

        //直流交流区分开
        int slowCountNum = 0;
        int countNum = 0;
        int slowLeisureConnectorNumber = 0;
        int leisureConnectorNumber = 0;
        for (EquipmentConnectorDO equipmentConnectorDO : equipmentConnectorDOS) {
            EquipmentModelDO equipmentModelDO = modelMap.get(equipmentConnectorDO.getEquipmentModelId());
            if (equipmentModelDO != null){
                if (2 == equipmentModelDO.getType()) {
                    if (onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorDO.getConnectorCode()) == 2) {
                        slowLeisureConnectorNumber = slowLeisureConnectorNumber + 1;
                    }
                    slowCountNum = slowCountNum + 1;
                } else {
                    if (onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorDO.getConnectorCode()) == 2) {
                        leisureConnectorNumber = leisureConnectorNumber + 1;
                    }
                    countNum = countNum + 1;
                }
            }
        }

        stationInfoVO.setConnectorNumber(countNum);
        stationInfoVO.setLeisureConnectorNumber(leisureConnectorNumber);
        stationInfoVO.setSlowConnectorNumber(slowCountNum);
        stationInfoVO.setSlowLeisureConnectorNumber(slowLeisureConnectorNumber);
        return stationInfoVO;
    }

    @Override
    public EquipmentConnectorInfoVO selectConnectorInfoById(String id) {

        EquipmentConnectorDO equipmentConnectorDO = equipmentConnectorMapper.selectOne(new LambdaQueryWrapperX<EquipmentConnectorDO>()
                .eq(EquipmentConnectorDO::getConnectorCode, id));
        EquipmentConnectorInfoVO equipmentConnectorInfoVO = EquipmentConnectorConvert.INSTANCE.convertInfoVO(equipmentConnectorDO);

        Integer gunWorkState = onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorInfoVO.getConnectorCode());
        Integer insertArmsState = onlineEquipmentConnectorService.getInsertArmsState(equipmentConnectorInfoVO.getConnectorCode());
        equipmentConnectorInfoVO.setWorkState(gunWorkState);
        equipmentConnectorInfoVO.setInsertArmsState(insertArmsState);

        equipmentConnectorInfoVO.setEquipmentBrandRespVO(EquipmentBrandConvert.INSTANCE.convert(equipmentBrandService.getEquipmentBrand(equipmentConnectorDO.getEquipmentBrandId())));
        equipmentConnectorInfoVO.setEquipmentModelRespVO(EquipmentModelConvert.INSTANCE.convert(equipmentModelService.getEquipmentModel(equipmentConnectorDO.getEquipmentModelId())));


        StationDO stationDO = stationMapper.selectOne(new LambdaQueryWrapperX<StationDO>().eq(StationDO::getId, equipmentConnectorDO.getStationId()));
        StationInfoVO stationInfoVO = fetchBillingRule(stationDO);
        equipmentConnectorInfoVO.setStationVo(stationInfoVO);

        return equipmentConnectorInfoVO;
    }

    @Override
    public List<EquipmentConnectorInfoVO> gunList(Long id) {

        //设置电枪列表
        List<EquipmentConnectorDO> equipmentConnectorDOS = equipmentConnectorMapper.selectList(new QueryWrapper<EquipmentConnectorDO>().lambda()
                .eq(EquipmentConnectorDO::getStationId, id));

        // 获得拼接需要的数据品牌
        Collection<Long> brandIds = convertList(equipmentConnectorDOS, EquipmentConnectorDO::getEquipmentBrandId);
        Map<Long, EquipmentBrandDO> brandMap = equipmentBrandService.getBrandMap(brandIds);

        // 类型
        Collection<Long> modelIds = convertList(equipmentConnectorDOS, EquipmentConnectorDO::getEquipmentModelId);
        Map<Long, EquipmentModelDO> modelMap = equipmentModelService.getModelMap(modelIds);

        List<EquipmentConnectorInfoVO> collect = equipmentConnectorDOS.stream().map(item -> {
            EquipmentConnectorInfoVO equipmentConnectorInfoVO = EquipmentConnectorConvert.INSTANCE.convertInfoVO(item);
            Integer gunWorkState = onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorInfoVO.getConnectorCode());
            equipmentConnectorInfoVO.setWorkState(gunWorkState);
            equipmentConnectorInfoVO.setEquipmentBrandRespVO(EquipmentBrandConvert.INSTANCE.convert(brandMap.get(item.getEquipmentBrandId())));
            equipmentConnectorInfoVO.setEquipmentModelRespVO(EquipmentModelConvert.INSTANCE.convert(modelMap.get(item.getEquipmentModelId())));

            return equipmentConnectorInfoVO;
        }).sorted(Comparator.comparing(EquipmentConnectorInfoVO::getConnectorCode)).collect(Collectors.toList());


        //直流交流区分开
        int slowCountNum = 0;
        int countNum = 0;
        int slowLeisureConnectorNumber = 0;
        int leisureConnectorNumber = 0;
        for (EquipmentConnectorDO equipmentConnectorDO : equipmentConnectorDOS) {
            EquipmentModelDO equipmentModelDO = modelMap.get(equipmentConnectorDO.getEquipmentModelId());
            if (equipmentModelDO != null){
                if (2 == equipmentModelDO.getType()) {
                    if (onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorDO.getConnectorCode()) == 2) {
                        slowLeisureConnectorNumber = slowLeisureConnectorNumber + 1;
                    }
                    slowCountNum = slowCountNum + 1;
                } else {
                    if (onlineEquipmentConnectorService.getGunWorkState(equipmentConnectorDO.getConnectorCode()) == 2) {
                        leisureConnectorNumber = leisureConnectorNumber + 1;
                    }
                    countNum = countNum + 1;
                }
            }
        }

        return collect;
    }

    private StationInfoVO fetchBillingRule(StationDO stationDO) {

        StationInfoVO stationInfoVO = StationConvert.INSTANCE.convertInfo(stationDO);

        //获取当前计费时段
        BigDecimal nowPrice = costTemplateService.getNowPrice(stationInfoVO.getCostTemplateId());
        stationInfoVO.setCurrentPrice(nowPrice);

        //设置计费规则
        CostTemplateDO costTemplate = costTemplateService.getCostTemplate(stationDO.getCostTemplateId());
        CostTemplateRespVO convert = CostTemplateConvert.INSTANCE.convert(costTemplate);

        CostTemplatePriceExportReqVO exportReqVO = new CostTemplatePriceExportReqVO();
        exportReqVO.setCostTemplateId(costTemplate.getId());
        List<CostTemplatePriceDO> costTemplatePriceList = costTemplatePriceService.getCostTemplatePriceList(exportReqVO);
        convert.setCostTemplatePriceRespVOS(CostTemplatePriceConvert.INSTANCE.convertList(costTemplatePriceList));


        CostTemplatePriceTypeExportReqVO exportTypeReqVO = new CostTemplatePriceTypeExportReqVO();
        exportTypeReqVO.setCostTemplateId(costTemplate.getId());
        List<CostTemplatePriceTypeDO> costTemplatePriceTypeList = costTemplatePriceTypeService.getCostTemplatePriceTypeList(exportTypeReqVO);
        convert.setCostTemplatePriceTypeRespVOS(CostTemplatePriceTypeConvert.INSTANCE.convertList(costTemplatePriceTypeList));

        stationInfoVO.setCostTemplateRespVO(convert);


        Map<String, BigDecimal> powerTypeMap = new HashMap<>();
        Map<String, BigDecimal> serviceTypeMap = new HashMap<>();
        for (CostTemplatePriceTypeDO priceType : costTemplatePriceTypeList) {
            powerTypeMap.put(priceType.getTypeName(), priceType.getPricePower());
            serviceTypeMap.put(priceType.getTypeName(), priceType.getPriceService());
        }

        StringBuilder electricityFee = new StringBuilder();
        StringBuilder serviceFee = new StringBuilder();
        //格式化电费设置
        for (CostTemplatePriceDO price : costTemplatePriceList) {
            String priceTypeId = price.getPriceTypeId();
            BigDecimal powerName = powerTypeMap.get(priceTypeId);
            BigDecimal serviceName = serviceTypeMap.get(priceTypeId);
            stationInfoVO.setElectricityFee(electricityFee.append(price.getStartTimeStr()).append("-").append(price.getEndTimeStr()).append(":").append(powerName).append(";").toString());
            stationInfoVO.setServiceFee(serviceFee.append(price.getStartTimeStr()).append("-").append(price.getEndTimeStr()).append(":").append(serviceName).append(";").toString());
        }

        return stationInfoVO;
    }


    private double calculateDistance(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        // 将 BigDecimal 转换为 double
        double lat1Double = lat1.doubleValue();
        double lon1Double = lon1.doubleValue();
        double lat2Double = lat2.doubleValue();
        double lon2Double = lon2.doubleValue();

        // 使用 Haversine Formula 算法计算两个坐标之间的距离
        double earthRadius = 6371; // 地球半径，单位为千米

        double dLat = Math.toRadians(lat2Double - lat1Double);
        double dLon = Math.toRadians(lon2Double - lon1Double);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1Double)) * Math.cos(Math.toRadians(lat2Double)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

}
