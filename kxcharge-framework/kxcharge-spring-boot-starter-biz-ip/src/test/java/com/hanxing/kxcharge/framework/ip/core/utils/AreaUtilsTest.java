package com.hanxing.kxcharge.framework.ip.core.utils;


import com.hanxing.kxcharge.framework.ip.core.Area;
import com.hanxing.kxcharge.framework.ip.core.enums.AreaTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link AreaUtils} 的单元测试
 *
 * @author 航兴新能源
 */
public class AreaUtilsTest {

    @Test
    public void testGetArea() {
        // 调用：北京
        Area area = AreaUtils.getArea("110100");
        // 断言
        assertEquals(area.getId(), 110100);
        assertEquals(area.getName(), "北京市");
        assertEquals(area.getType(), AreaTypeEnum.CITY.getType());
        assertEquals(area.getParent().getId(), 110000);
        assertEquals(area.getChildren().size(), 16);
    }

    @Test
    public void testFormat() {
        assertEquals(AreaUtils.format("110105"), "北京市 北京市 朝阳区");
        assertEquals(AreaUtils.format("1"), "中国");
        assertEquals(AreaUtils.format("2"), "蒙古");
    }

}
