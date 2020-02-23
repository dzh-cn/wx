package com.jd.jr.wx.utils;

import com.jd.jr.wx.beans.WxGetCardEventBean;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

class JsonUtilsTest {

    @Test
    void readValue() throws IOException {
        String json = FileUtils.readFileToString(new ClassPathResource("beans/WxGetCardEventBean.json").getFile(), "utf8");

        WxGetCardEventBean b = JsonUtils.readValue(json, WxGetCardEventBean.class);
        System.out.println(JsonUtils.toJson(b));
    }
}