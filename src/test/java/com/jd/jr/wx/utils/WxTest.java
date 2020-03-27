package com.jd.jr.wx.utils;

import com.jd.jr.wx.beans.WxSaveCardBean;
import com.jd.jr.wx.enums.SubCardType;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author dongzhihua
 * @Date 2020-02-09 20:23
 */
public class WxTest extends WxUtilTest {

    @Test
    public void t() throws Exception {
        super.refreshAccessToken();
        saveCardByTemplate();
//        super.addCard();

//        super.depositCardCode();
//        super.updateStock();
        super.createGetCardQrcode();
    }

    @Test
    public void refreshAccessToken() {
        super.refreshAccessToken();
    }

    @Test
    void saveCardByTemplate() throws IOException {
        saveCardByTemplate(SubCardType.GIFT_CARD);
    }

    @Test
    void saveAllCardByTemplate() {
        List<String> result = new ArrayList<>();
        for (SubCardType t: SubCardType.values()) {
            try {
                saveCardByTemplate(t);
                result.add(String.format("%s(%s):%s", t.getText(), t.getKey(), "ok"));
            } catch (Exception e) {
                result.add(String.format("%s(%s):%s", t.getText(), t.getKey(), e.getMessage()));
            }
        }
        System.out.println(JsonUtils.toJson(result));
    }


    void saveCardByTemplate(SubCardType subCardType) throws IOException {
        String accessToken = super.getAccessToken();

        ClassPathResource beanResource = new ClassPathResource("beans/WxSaveCardBean.json");
        String beanJson = FileUtils.readFileToString(beanResource.getFile(), "utf8");
        WxSaveCardBean bean = JsonUtils.readValue(beanJson, WxSaveCardBean.class);
        bean.setSubCardType(subCardType);

        ClassPathResource res = new ClassPathResource("json/CampusCard.json");
        String tem = FileUtils.readFileToString(res.getFile(), "utf8");

        tem = VelocityUtil.evaluate(tem, bean);

        String cardId = WxUtil.saveCardByTemplate(accessToken, tem, bean);
        super.setCardId(cardId);
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
