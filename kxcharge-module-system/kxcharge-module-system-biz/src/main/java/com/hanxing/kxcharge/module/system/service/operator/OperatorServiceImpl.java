package com.hanxing.kxcharge.module.system.service.operator;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.OperatorPageReqVO;
import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.OperatorRespVO;
import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.OperatorSaveReqVO;
import com.hanxing.kxcharge.module.system.dal.dataobject.operator.OperatorDO;
import com.hanxing.kxcharge.module.system.dal.mysql.operator.OperatorMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.system.enums.ErrorCodeConstants.OPERATOR_NOT_EXISTS;

/**
 * 运营商 Service 实现类
 *
 * @author 航兴新能源
 */
@Service
@Validated
public class OperatorServiceImpl implements OperatorService {

    @Resource
    private OperatorMapper operatorMapper;

    @Override
    public Long createOperator(OperatorSaveReqVO createReqVO) {
        // 插入
        OperatorDO operator = BeanUtils.toBean(createReqVO, OperatorDO.class);
        operatorMapper.insert(operator);
        // 返回
        return operator.getId();
    }

    @Override
    public void updateOperator(OperatorSaveReqVO updateReqVO) {
        // 校验存在
        validateOperatorExists(updateReqVO.getId());
        // 更新
        OperatorDO updateObj = BeanUtils.toBean(updateReqVO, OperatorDO.class);
        operatorMapper.updateById(updateObj);
    }

    @Override
    public void deleteOperator(Long id) {
        // 校验存在
        validateOperatorExists(id);
        // 删除
        operatorMapper.deleteById(id);
    }

    private void validateOperatorExists(Long id) {
        if (operatorMapper.selectById(id) == null) {
            throw exception(OPERATOR_NOT_EXISTS);
        }
    }

    @Override
    public OperatorDO getOperator(Long id) {
        return operatorMapper.selectById(id);
    }

    @Override
    public PageResult<OperatorDO> getOperatorPage(OperatorPageReqVO pageReqVO) {
        return operatorMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OperatorDO> getOperatorList(OperatorRespVO operatorRespVO) {
        return operatorMapper.selectList(operatorRespVO);
    }

}
