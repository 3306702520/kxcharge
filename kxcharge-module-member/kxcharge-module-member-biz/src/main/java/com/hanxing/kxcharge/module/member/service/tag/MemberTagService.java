package com.hanxing.kxcharge.module.member.service.tag;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.module.member.controller.admin.tag.vo.MemberTagCreateReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.tag.vo.MemberTagPageReqVO;
import com.hanxing.kxcharge.module.member.controller.admin.tag.vo.MemberTagUpdateReqVO;
import com.hanxing.kxcharge.module.member.dal.dataobject.tag.MemberTagDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 会员标签 Service 接口
 *
 * @author 航兴新能源
 */
public interface MemberTagService {

    /**
     * 创建会员标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTag(@Valid MemberTagCreateReqVO createReqVO);

    /**
     * 更新会员标签
     *
     * @param updateReqVO 更新信息
     */
    void updateTag(@Valid MemberTagUpdateReqVO updateReqVO);

    /**
     * 删除会员标签
     *
     * @param id 编号
     */
    void deleteTag(Long id);

    /**
     * 获得会员标签
     *
     * @param id 编号
     * @return 会员标签
     */
    MemberTagDO getTag(Long id);

    /**
     * 获得会员标签列表
     *
     * @param ids 编号
     * @return 会员标签列表
     */
    List<MemberTagDO> getTagList(Collection<Long> ids);

    /**
     * 获得会员标签分页
     *
     * @param pageReqVO 分页查询
     * @return 会员标签分页
     */
    PageResult<MemberTagDO> getTagPage(MemberTagPageReqVO pageReqVO);

    /**
     * 获取标签列表
     *
     * @return 标签列表
     */
    List<MemberTagDO> getTagList();

}
