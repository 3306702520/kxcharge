package com.hanxing.kxcharge.framework.dict.config;

import com.hanxing.kxcharge.framework.dict.core.DictFrameworkUtils;
import com.hanxing.kxcharge.module.system.api.dict.DictDataApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class KxchargeDictAutoConfiguration {

    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public DictFrameworkUtils dictUtils(DictDataApi dictDataApi) {
        DictFrameworkUtils.init(dictDataApi);
        return new DictFrameworkUtils();
    }

}
