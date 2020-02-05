package com.dong.wx.ctl;

import com.dong.wx.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    static Logger log = LoggerFactory.getLogger(WeChatCtl.class);

    @RequestMapping("check")
    public Object t(String signature, String timestamp, String nonce, String echostr) {
        log.info("signature: {}, timestamp: {}, nonce: {}, echostr: {}", signature, timestamp, nonce, echostr);
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
        log.info("sign: {}", sign);
        if (sign.equals(signature)) {
            return echostr;
        }
        return null;
    }
}
