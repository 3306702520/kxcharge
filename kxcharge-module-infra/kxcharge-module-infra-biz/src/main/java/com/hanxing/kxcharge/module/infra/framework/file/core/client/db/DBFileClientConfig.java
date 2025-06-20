package com.hanxing.kxcharge.module.infra.framework.file.core.client.db;

import com.hanxing.kxcharge.module.infra.framework.file.core.client.FileClientConfig;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;

/**
 * 基于 DB 存储的文件客户端的配置类
 *
 * @author 航兴新能源
 */
@Data
public class DBFileClientConfig implements FileClientConfig {

    /**
     * 自定义域名
     */
    @NotEmpty(message = "domain 不能为空")
    @URL(message = "domain 必须是 URL 格式")
    private String domain;

}
