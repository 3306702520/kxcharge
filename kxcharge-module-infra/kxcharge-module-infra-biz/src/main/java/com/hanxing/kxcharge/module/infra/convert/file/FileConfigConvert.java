package com.hanxing.kxcharge.module.infra.convert.file;

import com.hanxing.kxcharge.module.infra.controller.admin.file.vo.config.FileConfigSaveReqVO;
import com.hanxing.kxcharge.module.infra.dal.dataobject.file.FileConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 文件配置 Convert
 *
 * @author 航兴新能源
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigSaveReqVO bean);

}
