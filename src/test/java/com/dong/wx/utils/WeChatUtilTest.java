package com.dong.wx.utils;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtilTest {
    String app = "wx36eded4e9c811a90";
    String sec = "beeee4bc5d7243c01c61ddfaedb044ee";
    String accessToken = "30_Nm-VeWQcjZdnRecRvaXxq79bpedH2SRjAq2_R4tUXBVmWAX_HTu8Q8T5jKDWX1RHhOZhjawiK5HgZXY0WulXr8y39ImHPNV5fDdAVb4GdLW-EjfDSJUGde6eCHoDIQgAHATYG";
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
        String res = WeChatUtil.uploadImg(accessToken, new File("/Users/dongzhihua/Documents/abc.jpeg"));
        System.out.println(res);
        // {"url":"http://mmbiz.qpic.cn/mmbiz_jpg/A5vYnILS59Uk6VPAibMYCBxM3OfNW7iaSCWLICzLPxjgPX2KaEYpxD2iaE5ibGbZzBjdBR0lQf3dTric6qLblSicicCNg/0"}
    }

    @Test
    public void saveGeneralCard() {
        String res = WeChatUtil.saveGeneralCard(accessToken, true);
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
