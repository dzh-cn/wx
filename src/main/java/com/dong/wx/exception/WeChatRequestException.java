package com.dong.wx.exception;

import lombok.Data;

/**
 * 微信交互异常
 *
 * @Author dongzhihua
 * @Date 2020-02-09 22:24
 */
@Data
public class WeChatRequestException extends RuntimeException {
    private String code;
    private String msg;

    public WeChatRequestException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
