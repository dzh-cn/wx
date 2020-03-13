package com.jd.jr.wx.utils;

import com.jd.jr.wx.beans.WxGetCardEventBean;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

class XmlParseSaxUtilTest {

    @Test
    void parse() throws IOException {
        File file = new ClassPathResource("xml/WxMsgEventHandle.xml").getFile();
        Object bean = XmlParseSaxUtil.parseToMap(file);
        String s = JsonUtils.toJson(bean);
        System.out.println(JsonUtils.toJson(s));
        WxGetCardEventBean wgc = JsonUtils.readValue(s, WxGetCardEventBean.class);
        System.out.println(JsonUtils.toJson(wgc));
    }
}