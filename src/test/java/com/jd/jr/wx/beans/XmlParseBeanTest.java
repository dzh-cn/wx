package com.jd.jr.wx.beans;

import com.jd.jr.wx.utils.JsonUtils;
import com.jd.jr.wx.utils.XmlParseSaxUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

class XmlParseBeanTest {

    @Test
    public void parseWxGetCardActionBean() {
        parse(WxGetCardEventBean.class);
    }

    @Test
    public void parseWxCardAuditActionBean() {
        parse(WxCardAuditEventBean.class);
    }

    @Test
    public void parseWxCardDelActionBean() {
        parse(WxCardDelEventBean.class);
    }

    @Test
    public void parseWxCardSkuWarnBean() {
        parse(WxCardSkuWarnEventBean.class);
    }

    public <T> T parse(Class<T> cla) {

        String format = "/Users/dongzhihua/Documents/project/wx/src/test/resources/xml/%s.xml";
        File file = new File(String.format(format, cla.getSimpleName()));
        T obj = XmlParseSaxUtil.parse(file, cla);
        System.out.println(JsonUtils.toJson(obj));
        return obj;
    }
}