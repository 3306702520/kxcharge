package com.hanxing.kxcharge.module.member.service.plate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hanxing.kxcharge.framework.common.enums.PlateIsDefaultEnum;
import com.hanxing.kxcharge.framework.common.enums.PlateTypeEnum;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.framework.tenant.core.aop.TenantIgnore;
import com.hanxing.kxcharge.module.member.api.plate.dto.MemberPlateReqDTO;
import com.hanxing.kxcharge.module.member.controller.admin.plate.vo.*;
import com.hanxing.kxcharge.module.member.controller.app.plate.vo.AppPlateUpdateDefaultReqVO;
import com.hanxing.kxcharge.module.member.convert.plate.PlateConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.plate.PlateDO;
import com.hanxing.kxcharge.module.member.dal.mysql.plate.PlateMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.member.enums.ErrorCodeConstants.PLATE_NOT_EXISTS;

/**
 * 用户车牌信息 Service 实现类
 *
 * @author 小黑
 */
@Service
@Validated
public class PlateServiceImpl implements PlateService {

    @Resource
    private PlateMapper plateMapper;

    @Transactional
    @Override
    public Long createPlate(PlateCreateReqVO createReqVO) {
        // 插入
        if (PlateIsDefaultEnum.IS_DEFAULT.getStatus().equals(createReqVO.getIsDefault())) {
            plateMapper.updateDefaultByUserId(createReqVO.getUserId());
        }
        PlateDO plate = PlateConvert.INSTANCE.convert(createReqVO);
        plateMapper.insert(plate);
        // 返回
        return plate.getId();
    }

    @Override
    public void updatePlate(PlateUpdateReqVO updateReqVO) {
        // 校验存在
        validatePlateExists(updateReqVO.getId());
        // 更新
        PlateDO updateObj = PlateConvert.INSTANCE.convert(updateReqVO);
        plateMapper.updateById(updateObj);
    }

    @Override
    public void deletePlate(Long id) {
        // 校验存在
        validatePlateExists(id);
        // 删除
        plateMapper.deleteById(id);
    }

    private void validatePlateExists(Long id) {
        if (plateMapper.selectById(id) == null) {
            throw exception(PLATE_NOT_EXISTS);
        }
    }

    @Override
    public PlateDO getPlate(Long id) {
        return plateMapper.selectById(id);
    }

    @Override
    public List<PlateDO> getPlateList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return plateMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<PlateDO> getPlatePage(PlatePageReqVO pageReqVO) {
        return plateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<PlateDO> getPlateList(PlateExportReqVO exportReqVO) {
        return plateMapper.selectList(exportReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDefaultPlate(PlateUpdateDefaultReqVO updateReqVO) {

        validatePlateExists(updateReqVO.getId());
        plateMapper.updateDefaultByUserId(updateReqVO.getUserId());
        plateMapper.updateDefaultById(updateReqVO.getId());

    }

    @Override
    public void updateDefaultPlate(AppPlateUpdateDefaultReqVO updateReqVO) {
        validatePlateExists(updateReqVO.getId());
        plateMapper.updateDefaultByUserId(updateReqVO.getUserId());
        plateMapper.updateDefaultById(updateReqVO.getId());
    }

    @Override
    public void addPlateNumber(Long userId, String plateNumber) {
        PlateDO plateDO = new PlateDO();
        plateDO.setPlateType(PlateTypeEnum.NEWENERGY.getValue());
        plateDO.setPlateNumber(plateNumber);
        plateDO.setIsDefault(0);
        plateDO.setUserId(userId);
        PlateDO carByCarNumber = plateMapper.selectOne(new LambdaQueryWrapperX<PlateDO>()
                .eq(PlateDO::getPlateNumber, plateNumber)
                .eq(PlateDO::getUserId, userId));
        if (ObjectUtils.isEmpty(carByCarNumber)) {
            plateMapper.insert(plateDO);
        }
    }

    @Override
    @TenantIgnore
    public void chargeOrder(MemberPlateReqDTO memberPlateReqDTO) {
        List<PlateDO> plateDOS = plateMapper.selectList(new QueryWrapper<PlateDO>().lambda()
                .eq(PlateDO::getUserId, memberPlateReqDTO.getUserId())
                .eq(PlateDO::getPlateNumber, memberPlateReqDTO.getPlateNumber())
        );
        if (CollUtil.isEmpty(plateDOS)) {
            return;
        }
        if (plateDOS.size() > 1) {
            // 同一个用户找到两台一样车牌号的车,不处理
            return;
        }
        // todo zxy 需要注意更新丢失的问题，理论上存在，一般不存在。暂时先不处理
        PlateDO plateDO = plateDOS.get(0);
        plateDO.setLatelyPlace(memberPlateReqDTO.getChargePlace());
        plateDO.setLatelyTime(memberPlateReqDTO.getChargeTime());
        plateDO.setChargeQuantity(plateDO.getChargeQuantity() + 1);
        plateMapper.updateById(plateDO);
    }

}
