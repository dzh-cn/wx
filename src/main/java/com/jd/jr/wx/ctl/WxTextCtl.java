package com.jd.jr.wx.ctl;

import com.jd.jr.wx.utils.WxUtil;
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
public class WxTextCtl {

    @RequestMapping("audit")
    public Object audit(String accessToken, String code, String cardNo, String cardId, String picUrl) {
        return WxUtil.activeCard(accessToken, code, cardNo, cardId);
    }
}
