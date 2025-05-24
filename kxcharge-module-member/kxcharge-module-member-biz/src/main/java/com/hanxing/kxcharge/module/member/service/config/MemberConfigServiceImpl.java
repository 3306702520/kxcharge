package com.hanxing.kxcharge.module.member.service.config;

import com.hanxing.kxcharge.framework.common.util.collection.CollectionUtils;
import com.hanxing.kxcharge.module.member.controller.admin.config.vo.MemberConfigSaveReqVO;
import com.hanxing.kxcharge.module.member.convert.config.MemberConfigConvert;
import com.hanxing.kxcharge.module.member.dal.dataobject.config.MemberConfigDO;
import com.hanxing.kxcharge.module.member.dal.mysql.config.MemberConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 会员配置 Service 实现类
 *
 * @author QingX
 */
@Service
@Validated
public class MemberConfigServiceImpl implements MemberConfigService {

    @Resource
    private MemberConfigMapper memberConfigMapper;

    @Override
    public void saveConfig(MemberConfigSaveReqVO saveReqVO) {
        // 存在，则进行更新
        MemberConfigDO dbConfig = getConfig();
        if (dbConfig != null) {
            memberConfigMapper.updateById(MemberConfigConvert.INSTANCE.convert(saveReqVO).setId(dbConfig.getId()));
            return;
        }
        // 不存在，则进行插入
        memberConfigMapper.insert(MemberConfigConvert.INSTANCE.convert(saveReqVO));
    }

    @Override
    public MemberConfigDO getConfig() {
        List<MemberConfigDO> list = memberConfigMapper.selectList();
        return CollectionUtils.getFirst(list);
    }

}
