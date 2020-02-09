package com.dong.wx.utils;

import org.junit.jupiter.api.Test;

/**
 * @Author dongzhihua
 * @Date 2020-02-09 20:23
 */
public class WeChatTest extends WeChatUtilTest {

    @Test
    public void t() throws Exception {
        super.saveCardByTemplate();
        super.depositCardCode();
        super.updateStock();
        super.createGetCardQrcode();
    }
}
