package com.jd.jr.wx.ctl;

import com.jd.jr.wx.utils.WxUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 测试ctl
 *
 * @Author dongzhihua
 * @Date 2020-02-07 14:25
 */
@RestController
@RequestMapping("wt")
public class WxTextCtl {

    @RequestMapping("audit")
    public Object audit(String accessToken, String code, String cardNo, String cardId, String picUrl) {
        return WxUtil.activeCard(accessToken, code, cardNo, cardId);
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response,
                         @RequestParam(defaultValue = "wx-1.0.jar") String file) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file);
        InputStream in = null;
        try {
            in = new FileInputStream("target/" + file);
            OutputStream out = response.getOutputStream();
            IOUtils.copy(in, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
