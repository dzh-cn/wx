package com.dong.wx.ctl;

import com.dong.wx.event.EventDeal;
import com.dong.wx.utils.JsonUtils;
import com.dong.wx.utils.WeChatUtil;
import com.dong.wx.utils.XmlParseSaxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 微信回调
 *
 * @Author dongzhihua
 * @Date 2020-02-05 17:13
 */
@RestController
@RequestMapping("/wx")
public class WeChatCtl {

    public static final String TOKEN = "Token12342231";
    public static final String EncodingAESKey = "NCHOrH3n9SmfDgG63HqGxPwa0Sf16FJHIaEcCSRNfIF";

    static Logger log = LoggerFactory.getLogger(WeChatCtl.class);

    @Resource
    EventDeal eventDeal;

    @RequestMapping()
    public Object wx(HttpServletRequest request, String signature, String timestamp, String nonce, String echostr) {

        try {
            boolean r = request.getInputStream().isReady();
            log.info("input stream is ready: {}", r);
            Map<String, String> map = XmlParseSaxUtil.parseToMap(request.getInputStream());
            log.info("event msg: {}", JsonUtils.toJson(map));

            boolean deal = eventDeal.deal(map);
            Assert.isTrue(deal, "处理失败");
        } catch (Exception e) {
            log.warn("解析失败", e);
        }

        log.debug("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
        List<String> list = new ArrayList<>();
        list.add(TOKEN);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);

        StringBuilder b = new StringBuilder();
        for (String s : list) {
            b.append(s);
        }

        String sign = WeChatUtil.signature(b.toString());
        log.debug("sign: {}", sign);
        if (sign.equals(signature)) {
            return echostr;
        }
        return null;
    }

}
