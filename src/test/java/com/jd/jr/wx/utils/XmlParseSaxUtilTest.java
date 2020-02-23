package com.jd.jr.wx.utils;

import com.jd.jr.wx.beans.WxGetCardEventBean;
import org.junit.jupiter.api.Test;

import java.io.File;

class XmlParseSaxUtilTest {

    @Test
    void parse() {
        File file = new File("/Users/dongzhihua/Documents/project/wx/src/test/resources/xml/WxGetCardEventParam.xml");
        Object bean = XmlParseSaxUtil.parseToMap(file);
        String s = JsonUtils.toJson(bean);
        System.out.println(JsonUtils.toJson(s));
        WxGetCardEventBean wgc = JsonUtils.readValue(s, WxGetCardEventBean.class);
        System.out.println(JsonUtils.toJson(wgc));
    }
}