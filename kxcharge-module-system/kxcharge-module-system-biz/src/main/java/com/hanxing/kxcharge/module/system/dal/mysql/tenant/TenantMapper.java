package com.hanxing.kxcharge.module.system.dal.mysql.tenant;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.system.controller.admin.tenant.vo.tenant.TenantPageReqVO;
import com.hanxing.kxcharge.module.system.dal.dataobject.tenant.TenantDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 租户 Mapper
 *
 * @author 航兴新能源
 */
@Mapper
public interface TenantMapper extends BaseMapperX<TenantDO> {

    default PageResult<TenantDO> selectPage(TenantPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TenantDO>()
                .likeIfPresent(TenantDO::getName, reqVO.getName())
                .likeIfPresent(TenantDO::getContactName, reqVO.getContactName())
                .likeIfPresent(TenantDO::getContactMobile, reqVO.getContactMobile())
                .eqIfPresent(TenantDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(TenantDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TenantDO::getId));
    }

    default TenantDO selectByName(String name) {
        return selectOne(TenantDO::getName, name);
    }

    default TenantDO selectByWebsite(String website) {
        return selectOne(TenantDO::getWebsite, website);
    }

    default Long selectCountByPackageId(Long packageId) {
        return selectCount(TenantDO::getPackageId, packageId);
    }

    default List<TenantDO> selectListByPackageId(Long packageId) {
        return selectList(TenantDO::getPackageId, packageId);
    }

}
