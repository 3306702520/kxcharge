package com.hanxing.kxcharge.module.system.framework.datapermission.config;

import com.hanxing.kxcharge.module.system.dal.dataobject.dept.DeptDO;
import com.hanxing.kxcharge.module.system.dal.dataobject.user.AdminUserDO;
import com.hanxing.kxcharge.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的数据权限 Configuration
 *
 * @author 航兴新能源
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(AdminUserDO.class);
            rule.addDeptColumn(DeptDO.class, "id");
            // user
            rule.addUserColumn(AdminUserDO.class, "id");
        };
    }

}
