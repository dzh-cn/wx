package com.jd.jr.wx.ctl;

import com.jd.jr.wx.constants.ApiTicketType;
import com.jd.jr.wx.constants.WxAppConf;
import com.jd.jr.wx.utils.JsonUtils;
import com.jd.jr.wx.utils.WxUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("wx/card")
@Controller
public class CardCtl {

    @Resource
    RedisTemplate<String, String> redisTemplate;
    String appId = WxAppConf.campls.getApp();

    @RequestMapping("open")
    String card(Model model, String code) {
        String url = "http://www.jauking.xyz/wx/card/open";

        Map<String, Object> apiTicketRes = WxUtil.getApiTicket(getAccessToken(), ApiTicketType.jsApi);
        System.out.println("apiTicketRes: " + apiTicketRes);
        String apiTicket = apiTicketRes.get("ticket").toString();
        String noncestr = WxUtil.getNonceStr();
        String timestamp = WxUtil.getTimestamp();
        String str = "jsapi_ticket=" + apiTicket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = WxUtil.signature(str);

        Map<String, String> js_sdk = new HashMap<>();
        js_sdk.put("appId", appId);
        js_sdk.put("nonceStr", noncestr);
        js_sdk.put("signature", signature);
        js_sdk.put("jsapi_ticket", apiTicket);
        js_sdk.put("timestamp", timestamp);

        System.out.println("js_sdk :" + js_sdk);
        model.addAttribute("jsSign", js_sdk);
        model.addAttribute("url", url);


        String card_id = redisTemplate.opsForValue().get("cardId");
        model.addAttribute("cardId", card_id);
        Map<String, Object> cardTicketRes = WxUtil.getApiTicket(getAccessToken(), ApiTicketType.wxCard);
        System.out.println("cardTicketRes: " + cardTicketRes);
        String cardTicket = cardTicketRes.get("ticket").toString();
        String openid = "otC9mt_XtmNkWmOi1iLf5D8FBDeM";
        String timestamp1 = WxUtil.getTimestamp();
        String nonce_str = WxUtil.getNonceStr();

        String sign = WxUtil.signature(cardTicket, timestamp1, card_id, code, openid, nonce_str);

        Map<String, String> cardExt = new HashMap<>();
        cardExt.put("code", code);
        cardExt.put("openid", openid);
        cardExt.put("timestamp", timestamp1);
        cardExt.put("nonce_str", nonce_str);
        cardExt.put("signature", sign);

        model.addAttribute("cardExt", cardExt);
        model.addAttribute("cardTicket", cardTicket);
        return "card.html";
    }

    String getAccessToken() {
        String accessToken = redisTemplate.opsForValue().get("accessToken");
        return accessToken;
    }

    @RequestMapping("sign")
    Object sign() {
        return null;
    }
}
