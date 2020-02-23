package com.jd.jr.wx.ctl;

import com.jd.jr.wx.event.WxEventDeal;
import com.jd.jr.wx.utils.JsonUtils;
import com.jd.jr.wx.utils.WxUtil;
import com.jd.jr.wx.utils.XmlParseSaxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 微信回调
 *
 * @Author dongzhihua
 * @Date 2020-02-05 17:13
 */
@RestController
@RequestMapping("wx")
public class WxCtl {

    public static final String TOKEN = "Token12342231";
    public static final String EncodingAESKey = "NCHOrH3n9SmfDgG63HqGxPwa0Sf16FJHIaEcCSRNfIF";

    static Logger log = LoggerFactory.getLogger(WxCtl.class);

    @Resource
    WxEventDeal eventDeal;

    @RequestMapping()
    public Object wx(HttpServletRequest request, String signature, String timestamp, String nonce, String echostr) {

        String sign = WxUtil.signature(timestamp, nonce, TOKEN);
        if (sign.equals(signature)) {

            try {
                Map<String, String> map = XmlParseSaxUtil.parseToMap(request.getInputStream());
                log.info("event msg: {}", JsonUtils.toJson(map));
                boolean deal = eventDeal.deal(map);
                Assert.isTrue(deal, "处理失败");
            } catch (Exception e) {
                log.warn("解析失败", e);
            }
            return echostr;
        }
        return "fail";
    }

    @RequestMapping("1")
    public Object wx1(@RequestParam Map<String, String> param, @RequestBody(required = false) String body) {
        log.info("body: {}", body);
        log.info("param: {}", param);

        String sign = WxUtil.signature(param.get("timestamp"), param.get("nonce"), TOKEN);
        if (sign.equals(param.get("signature"))) {

            try {
                Map<String, String> map = XmlParseSaxUtil.parseToMap(body);
                log.info("event msg: {}", JsonUtils.toJson(map));
                boolean deal = eventDeal.deal(map);
                Assert.isTrue(deal, "处理失败");
            } catch (Exception e) {
                log.warn("解析失败", e);
            }
            return param.get("echostr");
        }
        return "fail";
    }
}
