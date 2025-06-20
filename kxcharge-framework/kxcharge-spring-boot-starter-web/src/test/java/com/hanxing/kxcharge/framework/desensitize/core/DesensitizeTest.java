package com.hanxing.kxcharge.framework.desensitize.core;

import com.hanxing.kxcharge.framework.common.util.json.JsonUtils;
import com.hanxing.kxcharge.framework.desensitize.core.regex.annotation.EmailDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.regex.annotation.RegexDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.annotation.Address;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.BankCardDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.CarLicenseDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.ChineseNameDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.FixedPhoneDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.IdCardDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.PasswordDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.MobileDesensitize;
import com.hanxing.kxcharge.framework.desensitize.core.slider.annotation.SliderDesensitize;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link DesensitizeTest} 的单元测试
 */
@ExtendWith(MockitoExtension.class)
public class DesensitizeTest {

    @Test
    public void test() {
        // 准备参数
        DesensitizeDemo desensitizeDemo = new DesensitizeDemo();
        desensitizeDemo.setNickname("航兴新能源");
        desensitizeDemo.setBankCard("9988002866797031");
        desensitizeDemo.setCarLicense("粤A66666");
        desensitizeDemo.setFixedPhone("01086551122");
        desensitizeDemo.setIdCard("530321199204074611");
        desensitizeDemo.setPassword("123456");
        desensitizeDemo.setPhoneNumber("13248765917");
        desensitizeDemo.setSlider1("ABCDEFG");
        desensitizeDemo.setSlider2("ABCDEFG");
        desensitizeDemo.setSlider3("ABCDEFG");
        desensitizeDemo.setEmail("1@email.com");
        desensitizeDemo.setRegex("你好，我是航兴新能源");
        desensitizeDemo.setAddress("北京市海淀区上地十街10号");
        desensitizeDemo.setOrigin("航兴新能源");

        // 调用
        DesensitizeDemo d = JsonUtils.parseObject(JsonUtils.toJsonString(desensitizeDemo), DesensitizeDemo.class);
        // 断言
        assertNotNull(d);
        assertEquals("芋***", d.getNickname());
        assertEquals("998800********31", d.getBankCard());
        assertEquals("粤A6***6", d.getCarLicense());
        assertEquals("0108*****22", d.getFixedPhone());
        assertEquals("530321**********11", d.getIdCard());
        assertEquals("******", d.getPassword());
        assertEquals("132****5917", d.getPhoneNumber());
        assertEquals("#######", d.getSlider1());
        assertEquals("ABC*EFG", d.getSlider2());
        assertEquals("*******", d.getSlider3());
        assertEquals("1****@email.com", d.getEmail());
        assertEquals("你好，我是*", d.getRegex());
        assertEquals("北京市海淀区上地十街10号*", d.getAddress());
        assertEquals("航兴新能源", d.getOrigin());
    }

    @Data
    public static class DesensitizeDemo {

        @ChineseNameDesensitize
        private String nickname;
        @BankCardDesensitize
        private String bankCard;
        @CarLicenseDesensitize
        private String carLicense;
        @FixedPhoneDesensitize
        private String fixedPhone;
        @IdCardDesensitize
        private String idCard;
        @PasswordDesensitize
        private String password;
        @MobileDesensitize
        private String phoneNumber;
        @SliderDesensitize(prefixKeep = 6, suffixKeep = 1, replacer = "#")
        private String slider1;
        @SliderDesensitize(prefixKeep = 3, suffixKeep = 3)
        private String slider2;
        @SliderDesensitize(prefixKeep = 10)
        private String slider3;
        @EmailDesensitize
        private String email;
        @RegexDesensitize(regex = "航兴新能源", replacer = "*")
        private String regex;
        @Address
        private String address;
        private String origin;

    }

}
