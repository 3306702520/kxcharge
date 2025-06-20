package com.hanxing.kxcharge.module.system.api.dept;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.framework.common.util.object.BeanUtils;
import com.hanxing.kxcharge.module.system.api.dept.dto.PostRespDTO;
import com.hanxing.kxcharge.module.system.dal.dataobject.dept.PostDO;
import com.hanxing.kxcharge.module.system.service.dept.PostService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class PostApiImpl implements PostApi {

    @Resource
    private PostService postService;

    @Override
    public CommonResult<Boolean> validPostList(Collection<Long> ids) {
        postService.validatePostList(ids);
        return success(true);
    }

    @Override
    public CommonResult<List<PostRespDTO>> getPostList(Collection<Long> ids) {
        List<PostDO> list = postService.getPostList(ids);
        return success(BeanUtils.toBean(list, PostRespDTO.class));
    }

}
