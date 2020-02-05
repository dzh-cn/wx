package com.dong.wx.utils;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Velocity 工具类
 *
 * @Author dongzhihua
 * @Date 2020-02-04 15:38
 */
public abstract class VelocityUtil {

    /**
     * 渲染字符串
     * @Author dongzhihua
     * @Date 2020-02-04 15:42
     */
    public static String evaluate(String template, Object param) {

        VelocityContext context = new VelocityContext();
        context.internalPut("obj", param);

        StringWriter stringWriter = new StringWriter();

        boolean suc;
        try {
            suc = Velocity.evaluate(context, stringWriter, "", template);
        } catch (IOException e) {
            throw new RuntimeException("字符串渲染异常", e);
        }
        if (suc) {
            return stringWriter.toString();
        }
        throw new RuntimeException("字符串渲染失败");
    }
}