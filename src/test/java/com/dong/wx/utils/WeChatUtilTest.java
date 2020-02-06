package com.dong.wx.utils;


import com.dong.wx.beans.WxCardParam;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtilTest {
    String app = "wx81c571702b5f3680";
    String sec = "ea00bac8ac711f0fb6aa5cc68ec03c5d";
    String accessToken = "30_hqNWDbclZw5HROSIB9jE-maCvBLJmZbMPqpD3szjQkd-GP9FlqZrZJaY_zBg-vMcw62YAlCZlZPabuIq687OFmuVl9V97ATqvOZcCfZ1WUYpQjIjJVABTWlNgsqQ5Mj_G0wzDscq-gyTLG5PNAHeAIAQDK";
    String cardId = "pRR0Ss78_Yvew1FnHWnr2CXHpEms";

    @Test
    public void getopenid() {
        String code = "134958468248706369";
        String openid = WeChatUtil.getopenid(app, code, sec);
        System.out.println(openid);
    }

    @Test
    public void getticket() {
    }

    @Test
    public void getAccessToken() {
        String accessToken = WeChatUtil.getAccessToken(app, sec);
        System.out.println(accessToken);
    }

    @Test
    public void getUserInfo() {
    }

    @Test
    public void uploadImg() {
        String res = WeChatUtil.uploadImg(accessToken, new File("/Users/dongzhihua/Documents/logo.png"));
        System.out.println(res);
        System.out.print(res.replaceAll("\\\\", ""));
        // back http://mmbiz.qpic.cn/mmbiz_jpg/A5vYnILS59VktFAT5c4biaUoLakQ3icict9mLJwsOXHzIkmicESem65TGlSibUSYfsfPRLdKCL4icvJoAOkB7yMTfgBA/0
        // logo http://mmbiz.qpic.cn/mmbiz_png/A5vYnILS59VktFAT5c4biaUoLakQ3icict9ibwd28dCkFdP8220jQzewLicfTPVV3ojNGw7beCAPIIaqemS8icVLIibjw/0
    }

    @Test
    public void saveGeneralCard() {
        String res = WeChatUtil.saveGeneralCard(accessToken, true);
        System.out.println(res);
    }

    @Test
    public void saveCardByTemplate() throws IOException {

        String tem = FileUtils.readFileToString(new ClassPathResource("json/createCardTemplate.json").getFile(), "utf8");
        String beanJson = FileUtils.readFileToString(new ClassPathResource("beans/WxCardParam.json").getFile(), "utf8");
        WxCardParam wxCardParam = JacksonUtils.readValue(beanJson, WxCardParam.class);

        String res = WeChatUtil.saveCardByTemplate(accessToken, tem, wxCardParam, true);
        System.out.println(res);
    }

    @Test
    public void createGetCardQrcode() {
        String openId = "oRR0Ss9hz1r5x-XEeWYEhpSeRwiQ";
        String code = "108342023";
        String res = WeChatUtil.createGetCardQrcode(accessToken, cardId, code, openId);
        System.out.println(res);
    }

    @Test
    public void activeCard() {
        String code = "";
        String cardNo = "";
        String picUrl = "";
        String cardId = "";
        String res = WeChatUtil.activeCard(accessToken, code, cardNo, cardId, picUrl);
        System.out.println(res);
    }

    @Test
    public void httpPost() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "dzh");
        String res = WeChatUtil.sendPost("http://localhost:8080/t", JacksonUtils.toJson(param));
        System.out.println(res);
    }

    @Test
    public void ve() {

        String str = "My name is $obj.name, $obj.age";
        Map<String, Object> param = new HashMap<>();
        param.put("name", "lth");
        param.put("age", 18);

        String evaluate = VelocityUtil.evaluate(str, param);
        System.out.println(evaluate);
    }

    public static class Desc{
        String name = "lth";
        Integer age = 18;

        public String getName() {
            return name;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
