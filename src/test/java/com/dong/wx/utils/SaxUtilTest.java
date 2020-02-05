package com.dong.wx.utils;

import com.dong.wx.beans.WxGetCardActionBean;
import org.junit.jupiter.api.Test;

import java.io.File;

class SaxUtilTest {

    @Test
    void parse() {
        File file = new File("/Users/dongzhihua/Documents/project/wx/src/test/resources/xml/get.xml");
        Object bean = SaxUtil.parseToMap(file);
        String s = JacksonUtils.toJson(bean);
        System.out.println(JacksonUtils.toJson(s));
        WxGetCardActionBean wgc = JacksonUtils.readValue(s, WxGetCardActionBean.class);
        System.out.println(JacksonUtils.toJson(wgc));
    }
}