package com.dong.wx.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: ecard-web
 * @Package: com.jd.jr.ecard.web.common.util
 * @ClassName: ShaUtil
 * @Author: lianghaifei
 * @CreateDate: 2019/4/15 17:04
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/15 17:04
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * @Description: java类作用描述
 */
public class ShaUtil {

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
