package com.hanxing.kxcharge.gateway.util;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author 航兴新能源
 */
@Component
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("\n----------------------------------------------------------\n\t" +
                            "项目启动成功！\n\t" +
                            "接口文档: \t{} \n\t" +
                            "开发文档: \t{} \n\t" +
                            "----------------------------------------------------------",
                    "https://cdzdoc.kxmall.vip/api-doc/",
                    "https://cdzdoc.kxmall.vip");

            // 支付平台
            System.out.println("[支付系统 kxcharge-module-pay - 教程][参考 https://cdzdoc.kxmall.vip/pay/build/ 开启]");
        });
    }

}
