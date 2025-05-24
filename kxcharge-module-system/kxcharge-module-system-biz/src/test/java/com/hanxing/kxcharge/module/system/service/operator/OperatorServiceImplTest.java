package com.hanxing.kxcharge.module.system.service.operator;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import com.hanxing.kxcharge.framework.test.core.ut.BaseDbUnitTest;

import com.hanxing.kxcharge.module.system.controller.admin.operator.vo.*;
import com.hanxing.kxcharge.module.system.dal.dataobject.operator.OperatorDO;
import com.hanxing.kxcharge.module.system.dal.mysql.operator.OperatorMapper;
import com.hanxing.kxcharge.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.hanxing.kxcharge.module.system.enums.ErrorCodeConstants.*;
import static com.hanxing.kxcharge.framework.test.core.util.AssertUtils.*;
import static com.hanxing.kxcharge.framework.test.core.util.RandomUtils.*;
import static com.hanxing.kxcharge.framework.common.util.date.LocalDateTimeUtils.*;
import static com.hanxing.kxcharge.framework.common.util.object.ObjectUtils.*;
import static com.hanxing.kxcharge.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link OperatorServiceImpl} 的单元测试类
 *
 * @author 航兴新能源
 */
@Import(OperatorServiceImpl.class)
public class OperatorServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OperatorServiceImpl operatorService;

    @Resource
    private OperatorMapper operatorMapper;

    @Test
    public void testCreateOperator_success() {
        // 准备参数
        OperatorSaveReqVO createReqVO = randomPojo(OperatorSaveReqVO.class).setId(null);

        // 调用
        Long operatorId = operatorService.createOperator(createReqVO);
        // 断言
        assertNotNull(operatorId);
        // 校验记录的属性是否正确
        OperatorDO operator = operatorMapper.selectById(operatorId);
        assertPojoEquals(createReqVO, operator, "id");
    }

    @Test
    public void testUpdateOperator_success() {
        // mock 数据
        OperatorDO dbOperator = randomPojo(OperatorDO.class);
        operatorMapper.insert(dbOperator);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OperatorSaveReqVO updateReqVO = randomPojo(OperatorSaveReqVO.class, o -> {
            o.setId(dbOperator.getId()); // 设置更新的 ID
        });

        // 调用
        operatorService.updateOperator(updateReqVO);
        // 校验是否更新正确
        OperatorDO operator = operatorMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, operator);
    }

    @Test
    public void testUpdateOperator_notExists() {
        // 准备参数
        OperatorSaveReqVO updateReqVO = randomPojo(OperatorSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> operatorService.updateOperator(updateReqVO), OPERATOR_NOT_EXISTS);
    }

    @Test
    public void testDeleteOperator_success() {
        // mock 数据
        OperatorDO dbOperator = randomPojo(OperatorDO.class);
        operatorMapper.insert(dbOperator);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOperator.getId();

        // 调用
        operatorService.deleteOperator(id);
       // 校验数据不存在了
       assertNull(operatorMapper.selectById(id));
    }

    @Test
    public void testDeleteOperator_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> operatorService.deleteOperator(id), OPERATOR_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOperatorPage() {
       // mock 数据
       OperatorDO dbOperator = randomPojo(OperatorDO.class, o -> { // 等会查询到
           o.setOrgName(null);
           o.setOperatorName(null);
           o.setAddress(null);
           o.setCode(null);
           o.setCityCode(null);
           o.setProvinceCode(null);
           o.setCityName(null);
           o.setSignStartTime(null);
           o.setSignEndTime(null);
           o.setBusinessLogo(null);
           o.setRemark(null);
           o.setCodeAbbr(null);
           o.setContactUser(null);
           o.setContactNum(null);
           o.setContactAddress(null);
           o.setEmail(null);
           o.setBusinessLicense(null);
           o.setIsFrozen(null);
           o.setCreateTime(null);
       });
       operatorMapper.insert(dbOperator);
       // 测试 orgName 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setOrgName(null)));
       // 测试 operatorName 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setOperatorName(null)));
       // 测试 address 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setAddress(null)));
       // 测试 code 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setCode(null)));
       // 测试 cityCode 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setCityCode(null)));
       // 测试 provinceCode 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setProvinceCode(null)));
       // 测试 cityName 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setCityName(null)));
       // 测试 signStartTime 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setSignStartTime(null)));
       // 测试 signEndTime 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setSignEndTime(null)));
       // 测试 businessLogo 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setBusinessLogo(null)));
       // 测试 remark 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setRemark(null)));
       // 测试 codeAbbr 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setCodeAbbr(null)));
       // 测试 contactUser 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setContactUser(null)));
       // 测试 contactNum 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setContactNum(null)));
       // 测试 contactAddress 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setContactAddress(null)));
       // 测试 email 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setEmail(null)));
       // 测试 businessLicense 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setBusinessLicense(null)));
       // 测试 isFrozen 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setIsFrozen(null)));
       // 测试 createTime 不匹配
       operatorMapper.insert(cloneIgnoreId(dbOperator, o -> o.setCreateTime(null)));
       // 准备参数
       OperatorPageReqVO reqVO = new OperatorPageReqVO();
       reqVO.setOrgName(null);
       reqVO.setOperatorName(null);
       reqVO.setAddress(null);
       reqVO.setCode(null);
       reqVO.setCityCode(null);
       reqVO.setProvinceCode(null);
       reqVO.setCityName(null);
       reqVO.setSignStartTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setSignEndTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setBusinessLogo(null);
       reqVO.setRemark(null);
       reqVO.setCodeAbbr(null);
       reqVO.setContactUser(null);
       reqVO.setContactNum(null);
       reqVO.setContactAddress(null);
       reqVO.setEmail(null);
       reqVO.setBusinessLicense(null);
       reqVO.setIsFrozen(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<OperatorDO> pageResult = operatorService.getOperatorPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOperator, pageResult.getList().get(0));
    }

}