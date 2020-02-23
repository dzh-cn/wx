package com.jd.jr.wx.utils;

import com.jd.jr.wx.beans.WxSaveCardBean;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @Author dongzhihua
 * @Date 2020-02-09 20:23
 */
public class WxTest extends WxUtilTest {

    @Test
    public void t() throws Exception {
        super.refreshAccessToken();
//        saveCardByTemplate();
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
        String accessToken = super.getAccessToken();

        ClassPathResource beanResource = new ClassPathResource("beans/WxSaveCardBean.json");
        String beanJson = FileUtils.readFileToString(beanResource.getFile(), "utf8");
        WxSaveCardBean bean = JsonUtils.readValue(beanJson, WxSaveCardBean.class);

        ClassPathResource res = new ClassPathResource("json/CampusCard.json");
        String tem = FileUtils.readFileToString(res.getFile(), "utf8");

        String cardId = WxUtil.saveCardByTemplate(accessToken, tem, bean);
        super.setCardId(cardId);
    }
}
