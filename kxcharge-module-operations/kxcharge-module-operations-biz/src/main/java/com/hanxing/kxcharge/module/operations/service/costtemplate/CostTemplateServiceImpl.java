package com.hanxing.kxcharge.module.operations.service.costtemplate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.operations.api.interflow.dto.NotificationStationChangeReqDto;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.CostTemplateCreateReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.CostTemplateExportReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.CostTemplatePageReqVO;
import com.hanxing.kxcharge.module.operations.controller.admin.costtemplate.vo.CostTemplateUpdateReqVO;
import com.hanxing.kxcharge.module.operations.convert.costtemplate.CostTemplateConvert;
import com.hanxing.kxcharge.module.operations.convert.costtemplateprice.CostTemplatePriceConvert;
import com.hanxing.kxcharge.module.operations.convert.costtemplatepricetype.CostTemplatePriceTypeConvert;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplate.CostTemplateMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplateprice.CostTemplatePriceMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.costtemplatepricetype.CostTemplatePriceTypeMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.equipment.EquipmentMapper;
import com.hanxing.kxcharge.module.operations.dal.mysql.station.StationMapper;
import com.hanxing.kxcharge.module.operations.mq.producer.interflow.InteconnectedProducer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.operations.enums.ErrorCodeConstants.*;

/**
 * 计费模板 Service 实现类
 *
 * @author kaixin
 */
@Service
@Validated
public class CostTemplateServiceImpl implements CostTemplateService {

    @Resource
    private CostTemplateMapper costTemplateMapper;
    @Resource
    private CostTemplatePriceMapper costTemplatePriceMapper;
    @Resource
    private CostTemplatePriceTypeMapper costTemplatePriceTypeMapper;
    @Resource
    private StationMapper stationMapper;
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private InteconnectedProducer inteconnectedProducer;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCostTemplate(CostTemplateCreateReqVO createReqVO) {
        // 插入
        CostTemplateDO costTemplate = CostTemplateConvert.INSTANCE.convert(createReqVO);

        //设置模型编码
        this.setProtocolVersion(costTemplate);

        costTemplateMapper.insert(costTemplate);

        // 插入 CostTemplatePriceDO
        List<CostTemplatePriceDO> costTemplatePrice = CostTemplatePriceConvert.INSTANCE.convertList2(createReqVO.getCostTemplatePriceRespVOS());
        for (CostTemplatePriceDO templatePriceDO : costTemplatePrice) {
            templatePriceDO.setCostTemplateId(costTemplate.getId());
            templatePriceDO.setOrgId(costTemplate.getOrgId());
        }
        costTemplatePriceMapper.insertBatch(costTemplatePrice);

        // 插入 CostTemplatePriceTypeDO
        List<CostTemplatePriceTypeDO> costTemplatePriceType = CostTemplatePriceTypeConvert.INSTANCE.convertList2(createReqVO.getCostTemplatePriceTypeRespVOS());
        for (CostTemplatePriceTypeDO costTemplatePriceTypeDO : costTemplatePriceType) {
            costTemplatePriceTypeDO.setCostTemplateId(costTemplate.getId());
            costTemplatePriceTypeDO.setOrgId(costTemplate.getOrgId());
        }
        costTemplatePriceTypeMapper.insertBatch(costTemplatePriceType);

        // 返回
        return costTemplate.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCostTemplate(CostTemplateUpdateReqVO updateReqVO) {
        // 校验存在
        validateCostTemplateExists(updateReqVO.getId());
        // 更新
        CostTemplateDO updateObj = CostTemplateConvert.INSTANCE.convert(updateReqVO);

        //设置模型编码
        this.setProtocolVersion(updateObj);

        costTemplateMapper.updateById(updateObj);

        costTemplatePriceMapper.delete(new QueryWrapper<CostTemplatePriceDO>().lambda().eq(CostTemplatePriceDO::getCostTemplateId, updateReqVO.getId()));
        costTemplatePriceTypeMapper.delete(new QueryWrapper<CostTemplatePriceTypeDO>().lambda().eq(CostTemplatePriceTypeDO::getCostTemplateId, updateReqVO.getId()));

        // 插入 CostTemplatePriceDO
        List<CostTemplatePriceDO> costTemplatePrice = CostTemplatePriceConvert.INSTANCE.convertList2(updateReqVO.getCostTemplatePriceRespVOS());
        for (CostTemplatePriceDO templatePriceDO : costTemplatePrice) {
            templatePriceDO.setCostTemplateId(updateReqVO.getId());
        }
        costTemplatePriceMapper.insertBatch(costTemplatePrice);
        // 插入 CostTemplatePriceTypeDO
        List<CostTemplatePriceTypeDO> costTemplatePriceType = CostTemplatePriceTypeConvert.INSTANCE.convertList2(updateReqVO.getCostTemplatePriceTypeRespVOS());
        for (CostTemplatePriceTypeDO costTemplatePriceTypeDO : costTemplatePriceType) {
            costTemplatePriceTypeDO.setCostTemplateId(updateReqVO.getId());
        }
        costTemplatePriceTypeMapper.insertBatch(costTemplatePriceType);

        //查出所有在这个底下的计费规则
        List<StationDO> stationDOS = stationMapper.selectList(new LambdaQueryWrapperX<StationDO>().eq(StationDO::getCostTemplateId, updateReqVO.getId()));
        for (StationDO stationDO : stationDOS) {
            NotificationStationChangeReqDto stationChangeReqDto = new NotificationStationChangeReqDto();
            stationChangeReqDto.setStationId(stationDO.getId());
            stationChangeReqDto.setType(3);
            inteconnectedProducer.sendNotificationStationChange(stationChangeReqDto);
        }
    }

    private void setProtocolVersion(CostTemplateDO costTemplateId) {
        CostTemplateDO costTemplateDO = costTemplateMapper.selectOne(
                new LambdaQueryWrapper<CostTemplateDO>()
                        .orderByDesc(CostTemplateDO::getProtocolVersion)
                        .last("LIMIT 1")
        );
        if (costTemplateDO == null) {
            costTemplateId.setProtocolVersion(1L);
        } else {
            // todo 需要注意，需要上分布式锁。但也可以不用上，因为这个接口一般不会存在并行调用
            costTemplateId.setProtocolVersion(costTemplateDO.getProtocolVersion() + 1);
        }
    }

    @Override
    public Boolean deleteCostTemplate(Long id) {
        // 校验存在
        validateCostTemplateExists(id);

        List<EquipmentDO> equipmentDOS = equipmentMapper.selectList(new QueryWrapper<EquipmentDO>().lambda().eq(EquipmentDO::getCostTemplateId, id));
        if (!CollUtil.isEmpty(equipmentDOS)) {
            throw exception(COST_TEMPLATE_DELETION_FAILURE);
        }

        List<StationDO> stationDOS = stationMapper.selectList(new QueryWrapper<StationDO>().lambda().eq(StationDO::getCostTemplateId, id));
        if (!CollUtil.isEmpty(stationDOS)) {
            throw exception(COST_TEMPLATE_DELETION_FAILURE_STATION);
        }

        // 删除
        int i = costTemplateMapper.deleteById(id);
        return i == 1;
    }

    private void validateCostTemplateExists(Long id) {
        if (costTemplateMapper.selectById(id) == null) {
            throw exception(COST_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public CostTemplateDO getCostTemplate(Long id) {
        return costTemplateMapper.selectById(id);
    }

    @Override
    public List<CostTemplateDO> getCostTemplateList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return costTemplateMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CostTemplateDO> getCostTemplatePage(CostTemplatePageReqVO pageReqVO) {
        return costTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<CostTemplateDO> getCostTemplateList(CostTemplateExportReqVO exportReqVO) {
        return costTemplateMapper.selectList(exportReqVO);
    }

    @Override
    public BigDecimal getNowPrice(Long id) {
        List<CostTemplatePriceDO> costTemplatePriceDO = costTemplatePriceMapper.selectList(new QueryWrapper<CostTemplatePriceDO>().lambda().eq(CostTemplatePriceDO::getCostTemplateId, id));
        Integer timePeriod = getTimePeriod();
        List<CostTemplatePriceDO> collect = costTemplatePriceDO.stream().filter(item -> {
            return timePeriod >= item.getStartTime() && timePeriod < item.getEndTime();
        }).collect(Collectors.toList());
        if (CollUtil.isEmpty(collect)) {
            //系统错误
            throw new RuntimeException("价格时间段计算有问题,找不到相应时间段");
        }
        String priceTypeId = collect.get(0).getPriceTypeId();
        if (StringUtils.isEmpty(priceTypeId)) {
            throw new RuntimeException("系统出现了问题,找不到对应时间段计费规则");
        }
        CostTemplatePriceTypeDO costTemplatePriceTypeDO = costTemplatePriceTypeMapper.selectOne(new QueryWrapper<CostTemplatePriceTypeDO>()
                .lambda()
                .eq(CostTemplatePriceTypeDO::getCostTemplateId, id)
                .eq(CostTemplatePriceTypeDO::getTypeName, priceTypeId)
        );
        if (costTemplatePriceTypeDO == null) {
            throw new RuntimeException("系统出现了问题,找不到对应时间段计费规则");
        }
        //电费价格+服务费价格
        return costTemplatePriceTypeDO.getPricePower().add(costTemplatePriceTypeDO.getPriceService()).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public static Integer getTimePeriod() {
        // 获取当前时间
        LocalTime currentTime = LocalTime.now();

        // 获取当前时间的小时和分钟
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();

        // 将小时和分钟转换为分钟数
        int totalMinutes = hour * 60 + minute;

        // 计算当前所处的时段
        int timeSlot = totalMinutes / 30;

        return timeSlot;
    }


}
