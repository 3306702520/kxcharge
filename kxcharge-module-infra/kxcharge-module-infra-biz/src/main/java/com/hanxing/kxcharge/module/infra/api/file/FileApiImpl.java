package com.hanxing.kxcharge.module.infra.api.file;

import com.hanxing.kxcharge.framework.common.pojo.CommonResult;
import com.hanxing.kxcharge.module.infra.api.file.dto.FileCreateReqDTO;
import com.hanxing.kxcharge.module.infra.service.file.FileService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.hanxing.kxcharge.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class FileApiImpl implements FileApi {

    @Resource
    private FileService fileService;

    @Override
    public CommonResult<String> createFile(FileCreateReqDTO createReqDTO) {
        return success(fileService.createFile(createReqDTO.getName(), createReqDTO.getPath(),
                createReqDTO.getContent()));
    }

}
