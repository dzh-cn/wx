package com.jd.jr.wx.utils;


import com.jd.jr.wx.beans.WxSaveCardBean;
import com.jd.jr.wx.constants.WxAppConf;
import com.jd.jr.wx.enums.CardType;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class WxUtilTest {
    public static WxAppConf wxAppConf = WxAppConf.ze;

    public static String accessTokenKey = "accessToken";
    public static String cardIdKey = "cardId";

    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void getopenid() {
        String code = "134958468248706369";
        String openid = WxUtil.getopenid(wxAppConf.getApp(), code, wxAppConf.getSec());
        System.out.println(openid);
    }

    @Test
    public void getticket() {
    }

    @Test
    public void refreshAccessToken() {
        String accessToken = WxUtil.getAccessToken(wxAppConf.getApp(), wxAppConf.getSec());
        redisTemplate.opsForValue().set(accessTokenKey, accessToken);
        System.out.println(redisTemplate.opsForValue().get(accessTokenKey));
    }

    public String getAccessToken() {
        return redisTemplate.opsForValue().get(accessTokenKey);
    }

    @Test
    public void getUserInfo() {
    }

    @Test
    public void uploadImg() {
        String url = WxUtil.uploadImg(getAccessToken(), new File("/Users/dongzhihua/Documents/logo.png"));
        System.out.println(url);
        // back http://mmbiz.qpic.cn/mmbiz_jpg/A5vYnILS59VktFAT5c4biaUoLakQ3icict9mLJwsOXHzIkmicESem65TGlSibUSYfsfPRLdKCL4icvJoAOkB7yMTfgBA/0
        // logo http://mmbiz.qpic.cn/mmbiz_png/A5vYnILS59VktFAT5c4biaUoLakQ3icict9ibwd28dCkFdP8220jQzewLicfTPVV3ojNGw7beCAPIIaqemS8icVLIibjw/0
    }

    @Test
    public void uploadImgURL() throws Exception {
        String fileDownloadUrl = "http://mmbiz.qpic.cn/mmbiz_jpg/A5vYnILS59VktFAT5c4biaUoLakQ3icict9mLJwsOXHzIkmicESem65TGlSibUSYfsfPRLdKCL4icvJoAOkB7yMTfgBA/0";
        String s = WxUtil.uploadImg(getAccessToken(), fileDownloadUrl);
        System.out.println(s);
    }

    @Test
    public void addCard() throws IOException {
        String beanJson = FileUtils.readFileToString(new ClassPathResource("beans/WxSaveCardBean.json").getFile(), "utf8");
        WxSaveCardBean bean = JsonUtils.readValue(beanJson, WxSaveCardBean.class);
        bean.setCardId(getCardId());

        bean.setCardType(CardType.MEMBER_CARD);
        String res = WxUtil.saveCard(getAccessToken(), bean);
        if(bean.getCardId() == null) {
            setCardId(res);
        }
        System.out.println(res);
    }

    @Test
    public void updateCard() throws IOException {
        String beanJson = FileUtils.readFileToString(new ClassPathResource("beans/WxSaveCardBean.json").getFile(), "utf8");
        WxSaveCardBean bean = JsonUtils.readValue(beanJson, WxSaveCardBean.class);
        bean.setCardType(CardType.MEMBER_CARD);
        bean.setCardId(getCardId());
        bean.setTitle("理发会员卡xxx");
        String res = WxUtil.saveCard(getAccessToken(), bean);

        System.out.println(res);
    }

    @Test
    public void saveGeneralCard() {
        String res = WxUtil.saveGeneralCard(getAccessToken(), true);
        System.out.println(res);
    }

    @Test
    public void createCardByTemplate() throws IOException {
// pylauwfAvshLKJsPlBzFBi6fI8EA
//        String jsonTemp = "json/GrouponCard.json";
        String jsonTemp = "json/MemberCardMeCreate.json";
        String tem = FileUtils.readFileToString(new ClassPathResource(jsonTemp).getFile(), "utf8");
        String beanJson = FileUtils.readFileToString(new ClassPathResource("beans/WxSaveCardBean.json").getFile(), "utf8");
        WxSaveCardBean bean = JsonUtils.readValue(beanJson, WxSaveCardBean.class);
        bean.setCardType(CardType.MEMBER_CARD);
        bean.setTitle("洗头会员卡");

        String card_id = WxUtil.saveCardByTemplate(getAccessToken(), tem, bean);
        setCardId(card_id);
    }

    @Test
    public void updateCardByTemplate() throws IOException {
// pylauwfAvshLKJsPlBzFBi6fI8EA
//        String jsonTemp = "json/GrouponCard.json";
        String jsonTemp = "json/MemberCardMeUpdate.json";
        String tem = FileUtils.readFileToString(new ClassPathResource(jsonTemp).getFile(), "utf8");
//        String beanJson = FileUtils.readFileToString(new ClassPathResource("beans/WxSaveCardBean.json").getFile(), "utf8");

//        WxSaveCardBean bean = JsonUtils.readValue(beanJson, WxSaveCardBean.class);

        WxSaveCardBean bean = new WxSaveCardBean();
        String cardId = getCardId();
        bean.setCardId(cardId);
        bean.setCustom1Name("name1");

        bean.setCardId(getCardId());
        WxUtil.saveCardByTemplate(getAccessToken(), tem, bean);
    }

    @Test
    public void depositCardCode() {
        List<String> codes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            codes.add(String.format("L%05d", i));
        }
        String cardId = getCardId();

        WxUtil.depositCardCode(getAccessToken(), cardId, codes);
    }

    @Test
    public void updateStock() {
        String cardId = getCardId();
        WxUtil.updateStock(getAccessToken(), cardId, 10, 0);
    }

    @Test
    public void createGetCardQrcode() {
        String code = "q1101110";
        String cardId = getCardId();
//        String cardId = "ptC9mt_yC9GPRhYfqq8y2QHXWu18";// 野鸡大学 ptC9mt_sRzNVciSY8VU03oSCcHyQ
        String openId = wxAppConf.getOpenId();
        String res = WxUtil.createGetCardQrcode(getAccessToken(), cardId, code, openId);
        System.out.println(res);
        assertRes(res);
        System.out.println(res.replaceAll("\\\\", ""));
        System.out.println(JsonUtils.readMap(res).get("show_qrcode_url"));
    }

    @Test
    public void createGetCardH5() {
        String cardId = getCardId();
        String res = WxUtil.createGetCardH5(getAccessToken(), cardId);
        System.out.println(res);
        assertRes(res);
        System.out.println(res.replaceAll("\\\\", ""));
        System.out.println(JsonUtils.readMap(res).get("url"));
    }

    @Test
    public void getApiTicket() {
        Object apiTicket = WxUtil.getApiTicket(getAccessToken(), null);
        System.out.println(apiTicket);
    }

    @Test
    public void activeCard() {
//  {"isRestoreMemberCard":"1","msgType":"event","unionId":"oAAAAALeacKdruTOmqpU4dlLF0U8","isRecommendByFriend":"0","fromUserName":"oylauwX9xtuanvKxpsm8DTE-Ow_8","sourceScene":"SOURCE_SCENE_QRCODE","createTime":"1581178818","cardId":"pylauwQXtGhkRoaG-SFkkIwkHmzw","toUserName":"gh_bed3df181e1e","isGiveByFriend":"0","outerId":"0","event":"user_get_card","userCardCode":"L00001"}
        String code = "L00001";
        String cardNo = "L00001";
        String cardId = getCardId();
        String res = WxUtil.activeCard(getAccessToken(), code, cardNo, cardId);
        System.out.println(res);
        assertRes(res);
    }

    @Test
    public void unavailableCard() {
        String code = "q1101110";
        WxUtil.unavailableCard(getAccessToken(), getCardId(), code, code);
    }

    @Test
    public void httpPost() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("name", "dzh");
        String res = HttpUtil.sendPost("http://localhost:8080/t", JsonUtils.toJson(param));
        System.out.println(res);
    }

    void assertRes(String res) {
        Map<String, Object> map = JsonUtils.readMap(res);
        Object code = map.get("errcode");
        boolean suc = code == null || code.toString().equals("0");
        Assert.isTrue(suc, map.get("errmsg").toString());
    }
    
    public String getCardId() {
        return redisTemplate.opsForValue().get(cardIdKey);
    }

    public String setCardId(String cardId) {
        redisTemplate.opsForValue().set(cardIdKey, cardId);
        return cardId;
    }
}
