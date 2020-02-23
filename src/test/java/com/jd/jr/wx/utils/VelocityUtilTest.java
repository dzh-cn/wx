package com.jd.jr.wx.utils;

import com.jd.jr.wx.beans.WxSaveCardBean;
import com.jd.jr.wx.enums.CardType;
import org.junit.jupiter.api.Test;

/**
 * @Author dongzhihua
 * @Date 2020-02-12 12:44
 */
public class VelocityUtilTest {

    @Test
    public void l() {
        WxSaveCardBean bean = new WxSaveCardBean();
        bean.setCardType(CardType.GENERAL_CARD);
        System.out.println(VelocityUtil.evaluate("$obj.cardType.key", bean));
    }
}
