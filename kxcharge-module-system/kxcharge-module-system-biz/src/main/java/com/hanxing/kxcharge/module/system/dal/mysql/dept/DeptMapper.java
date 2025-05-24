package com.hanxing.kxcharge.module.system.dal.mysql.dept;

import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import com.hanxing.kxcharge.module.system.dal.dataobject.dept.DeptDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapperX<DeptDO> {

    default List<DeptDO> selectList(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .likeIfPresent(DeptDO::getName, reqVO.getName())
                .eqIfPresent(DeptDO::getStatus, reqVO.getStatus()));
    }

    default DeptDO selectByParentIdAndName(Long parentId, String name) {
        return selectOne(DeptDO::getParentId, parentId, DeptDO::getName, name);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(DeptDO::getParentId, parentId);
    }

    default List<DeptDO> selectListByParentId(Collection<Long> parentIds) {
        return selectList(DeptDO::getParentId, parentIds);
    }

}
