package com.hanxing.kxcharge.module.member.dal.mysql.tag;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.mybatis.core.mapper.BaseMapperX;
import com.hanxing.kxcharge.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanxing.kxcharge.module.member.controller.admin.tag.vo.MemberTagPageReqVO;
import com.hanxing.kxcharge.module.member.dal.dataobject.tag.MemberTagDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员标签 Mapper
 *
 * @author 航兴新能源
 */
@Mapper
public interface MemberTagMapper extends BaseMapperX<MemberTagDO> {

    default PageResult<MemberTagDO> selectPage(MemberTagPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MemberTagDO>()
                .likeIfPresent(MemberTagDO::getName, reqVO.getName())
                .betweenIfPresent(MemberTagDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MemberTagDO::getId));
    }

    default MemberTagDO selelctByName(String name) {
        return selectOne(MemberTagDO::getName, name);
    }
}
