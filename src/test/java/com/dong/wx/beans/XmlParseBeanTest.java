package com.dong.wx.beans;

import com.dong.wx.utils.JacksonUtils;
import com.dong.wx.utils.XmlParseSaxUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

class XmlParseBeanTest {

    @Test
    public void parseWxGetCardActionBean() {
        parse(WxGetCardActionBean.class);
    }

    @Test
    public void parseWxCardAuditActionBean() {
        parse(WxCardAuditActionBean.class);
    }

    @Test
    public void parseWxCardDelActionBean() {
        parse(WxCardDelActionBean.class);
    }

    @Test
    public void parseWxCardSkuWarnBean() {
        parse(WxCardSkuWarnBean.class);
    }

    public <T> T parse(Class<T> cla) {

        String format = "/Users/dongzhihua/Documents/project/wx/src/test/resources/xml/%s.xml";
        File file = new File(String.format(format, cla.getSimpleName()));
        T obj = XmlParseSaxUtil.parse(file, cla);
        System.out.println(JacksonUtils.toJson(obj));
        return obj;
    }
}