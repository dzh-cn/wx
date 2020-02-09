package com.dong.wx.ctl;

import com.dong.wx.utils.WeChatUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试ctl
 *
 * @Author dongzhihua
 * @Date 2020-02-07 14:25
 */
@RestController
@RequestMapping("/wt")
public class WeChatTextCtl {

    @RequestMapping("audit")
    public Object audit(String accessToken, String code, String cardNo, String cardId, String picUrl) {
        return WeChatUtil.activeCard(accessToken, code, cardNo, cardId, picUrl);
    }
}
