package com.hanxing.kxcharge.module.operations.framework.datapermission.config;

import com.hanxing.kxcharge.framework.datapermission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplate.CostTemplateDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplateprice.CostTemplatePriceDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.costtemplatepricetype.CostTemplatePriceTypeDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipment.EquipmentDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentbrand.EquipmentBrandDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentconnector.EquipmentConnectorDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.equipmentmodel.EquipmentModelDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.order.OrderDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.orderdetails.OrderDetailsDO;
import com.hanxing.kxcharge.module.operations.dal.dataobject.station.StationDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * business 模块的数据权限 Configuration
 *
 * @author kaixin
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysOrgDataPermissionRuleCustomizer() {
        return rule -> {

            //org
            rule.addOrgColumn(CostTemplateDO.class);
            rule.addOrgColumn(CostTemplatePriceDO.class);
            rule.addOrgColumn(CostTemplatePriceTypeDO.class);


            rule.addOrgColumn(EquipmentDO.class);
            rule.addOrgColumn(EquipmentBrandDO.class);
            rule.addOrgColumn(EquipmentConnectorDO.class);
            rule.addOrgColumn(EquipmentModelDO.class);

            rule.addOrgColumn(OrderDO.class);
            rule.addOrgColumn(OrderDetailsDO.class);

            rule.addOrgColumn(StationDO.class);
        };
    }

}
