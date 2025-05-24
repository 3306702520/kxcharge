package com.hanxing.kxcharge.module.system.service.notice;

import com.hanxing.kxcharge.framework.common.pojo.PageResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.module.system.controller.admin.notice.vo.NoticePageReqVO;
import com.hanxing.kxcharge.module.system.controller.admin.notice.vo.NoticeSaveReqVO;
import com.hanxing.kxcharge.module.system.dal.dataobject.notice.NoticeDO;
import com.hanxing.kxcharge.module.system.dal.mysql.notice.NoticeMapper;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.hanxing.kxcharge.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.hanxing.kxcharge.module.system.enums.ErrorCodeConstants.NOTICE_NOT_FOUND;

/**
 * 通知公告 Service 实现类
 *
 * @author 航兴新能源
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Long createNotice(NoticeSaveReqVO createReqVO) {
        NoticeDO notice = BeanUtils.toBean(createReqVO, NoticeDO.class);
        noticeMapper.insert(notice);
        return notice.getId();
    }

    @Override
    public void updateNotice(NoticeSaveReqVO updateReqVO) {
        // 校验是否存在
        validateNoticeExists(updateReqVO.getId());
        // 更新通知公告
        NoticeDO updateObj = BeanUtils.toBean(updateReqVO, NoticeDO.class);
        noticeMapper.updateById(updateObj);
    }

    @Override
    public void deleteNotice(Long id) {
        // 校验是否存在
        validateNoticeExists(id);
        // 删除通知公告
        noticeMapper.deleteById(id);
    }

    @Override
    public PageResult<NoticeDO> getNoticePage(NoticePageReqVO reqVO) {
        return noticeMapper.selectPage(reqVO);
    }

    @Override
    public NoticeDO getNotice(Long id) {
        return noticeMapper.selectById(id);
    }

    @VisibleForTesting
    public void validateNoticeExists(Long id) {
        if (id == null) {
            return;
        }
        NoticeDO notice = noticeMapper.selectById(id);
        if (notice == null) {
            throw exception(NOTICE_NOT_FOUND);
        }
    }

}
