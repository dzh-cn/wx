package com.dong.wx.ctl;

import com.dong.wx.beans.WxCardAuditActionBean;
import com.dong.wx.beans.WxCardDelActionBean;
import com.dong.wx.beans.WxCardSkuWarnBean;
import com.dong.wx.beans.WxGetCardActionBean;
import com.dong.wx.utils.WeChatUtil;
import com.dong.wx.utils.XmlParseSaxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    public static final String EncodingAESKey = "7TnWrCafA43jvJ5zkLbpT4F8aFZYtFvoIREOVB1WpXz";

    static Logger log = LoggerFactory.getLogger(WeChatCtl.class);

    @RequestMapping()
    public Object check(HttpServletRequest request, String signature, String timestamp, String nonce, String echostr) {

        try {
            boolean r = request.getInputStream().isReady();
            log.info("input stream is ready: {}", r);
            XmlParseSaxUtil.parseToMap(request.getInputStream());
        } catch (Exception e) {
            log.warn("解析失败", e);
        }

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

    @RequestMapping("cardAudit")
    public Object cardAudit(HttpServletRequest request) throws Exception {
        WxCardAuditActionBean auditAction = XmlParseSaxUtil.parse(request.getInputStream(), WxCardAuditActionBean.class);
        log.info("cardAudit param: {}", auditAction);
        return true;
    }

    @RequestMapping("cardDel")
    public Object cardDel(HttpServletRequest request) throws Exception {
        WxCardDelActionBean auditAction = XmlParseSaxUtil.parse(request.getInputStream(), WxCardDelActionBean.class);
        log.info("cardDel param: {}", auditAction);
        return true;
    }

    @RequestMapping("cardSkuWarn")
    public Object cardSkuWarn(HttpServletRequest request) throws Exception {
        WxCardSkuWarnBean auditAction = XmlParseSaxUtil.parse(request.getInputStream(), WxCardSkuWarnBean.class);
        log.info("cardSkuWarn param: {}", auditAction);
        return true;
    }

    @RequestMapping("getCard")
    public Object getCard(HttpServletRequest request) throws Exception {
        WxGetCardActionBean auditAction = XmlParseSaxUtil.parse(request.getInputStream(), WxGetCardActionBean.class);
        log.info("getCard param: {}", auditAction);
        return true;
    }
}
