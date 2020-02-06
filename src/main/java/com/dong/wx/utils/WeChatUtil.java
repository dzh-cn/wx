package com.dong.wx.utils;

import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信工具类
 */
public class WeChatUtil {

    private static String getTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    private static String getOpenidUrl = "https://api.weixin.qq.com/sns/jscode2session";
    private static String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
    private static String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";
    private static String uploadImgUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";
    private static String createCardUrl = "https://api.weixin.qq.com/card/create?access_token=";
    private static String updateCardUrl = "https://api.weixin.qq.com/card/update?access_token=";
    private static String depositCardCodeUrl = "http://api.weixin.qq.com/card/code/deposit?access_token=";
    private static String activeCardUrl = "https://api.weixin.qq.com/card/generalcard/activate?access_token=";
    private static String createGetCardQrcodeUrl = "https://api.weixin.qq.com/card/qrcode/create?access_token=";

    private static Logger logger = LoggerFactory.getLogger(WeChatUtil.class);
    /**
     * 根据微信参数获取openId
     * @param appid
     * @param code
     * @param secret
     * @return
     */
    public static String getopenid(String appid, String code, String secret) {
        BufferedReader in = null;
        //appid和secret是开发者分别是小程序ID和小程序密钥，开发者通过微信公众平台-》设置-》开发设置就可以直接获取，
//        返回值json
//        openid 	string 	用户唯一标识
//        session_key 	string 	会话密钥
//        unionid 	string 	用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
//        errcode 	number 	错误码
//        errmsg 	string 	错误信息
        StringBuffer urlBuffer = new StringBuffer(getOpenidUrl);

        urlBuffer.append("?appid=");
        urlBuffer.append(appid);
        urlBuffer.append("&secret=");
        urlBuffer.append(secret);
        urlBuffer.append("&js_code=");
        urlBuffer.append(code);
        urlBuffer.append("&grant_type=authorization_code");

//        String url="https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        try {
            URL weChatUrl = new URL(urlBuffer.toString());
            // 打开和URL之间的连接
            URLConnection connection = weChatUrl.openConnection();
            // 设置通用的请求属性
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }  finally {//使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    /**
     * @author: lianghaifei
     * @method  payInfoSignature
     * @date: 2019/4/19 16:36
     * @param appId
     * @param nonceStr
     * @param prepayId
     * @param timeStamp
     * @param key
     * @return java.lang.String
     * @description 微信支付签名数据
     */
    public static String payInfoSignature(String appId, String nonceStr, String prepayId, String timeStamp, String key){
//        paySign = MD5(appId=wxd678efh567hg6787&nonceStr=5K8264ILTKCH16CQ2502SI8ZNMTM67VS&package=prepay_id=wx2017033010242291fcfe0db70013231072&signType=MD5&
//        timeStamp=1490840662&key=qazwsxedcrfvtgbyhnujmikolp111111) = 22D9B4E54AB1950F51E0649E8810ACD6
        logger.info("微信支付签名数据,appId:{},noncestr:{},prepayId:{},timestamp:{},key:{}", appId, nonceStr
                , prepayId, timeStamp, key);
        StringBuffer dataStr = new StringBuffer("appId=");
        dataStr.append(appId);
        dataStr.append("&nonceStr=");
        dataStr.append(nonceStr);
        dataStr.append("&package=");
        dataStr.append(prepayId);
        dataStr.append("&signType=MD5&timeStamp=");
        dataStr.append(timeStamp);
        dataStr.append("&key=");
        dataStr.append(key);

        String paySign =Md5Util.md5Upper32(null , dataStr.toString(), null);
        logger.info("微信支付签名结果:{},appId:{},noncestr:{},prepayId:{},timestamp:{},key:{}", paySign, appId, nonceStr
                , prepayId, timeStamp, key);
        return paySign;
    }
    /**
     * @author: lianghaifei
     * @method  signature
     * @date: 2019/4/15 17:13
     * @param noncestr
     * @param timestamp
     * @param url
     * @param jsapi_ticket
     * @return java.lang.String
     * @description 签名数据
     */
    public static String signature(String noncestr, String timestamp, String url, String jsapi_ticket){
//        jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
        logger.info("微信签名数据,noncestr:{},timestamp:{},url:{},jsapi_ticket:{}", noncestr, timestamp, url, jsapi_ticket);
        StringBuffer signature = new StringBuffer("jsapi_ticket=" + jsapi_ticket);
        signature.append("&noncestr=" + noncestr);
        signature.append("&timestamp=" + timestamp);
        signature.append("&url=" + url);

        String sign = signature(signature.toString());
        logger.info("微信签名结果:{},noncestr:{},timestamp:{},url:{},jsapi_ticket:{}", sign, noncestr, timestamp, url, jsapi_ticket);
        return sign;
    }
    /**
     * @author: lianghaifei
     * @method  signature
     * @date: 2019/11/19 17:36
     * @param data
     * @return java.lang.String
     * @description 数据签名标准接口
     */
    public static String signature(String data){
        logger.info("微信标准签名数据,data:{}", data);
        String sign = ShaUtil.SHA1(data);
        logger.info("微信标准签名结果:{},data:{}", sign, data);
        return sign;
    }
    /**
     * @author: lianghaifei
     * @method  getticket
     * @date: 2019/4/15 16:17
     * @param accessToken
     * @return java.lang.String
     * @description 获取加密秘钥
     */
    public static String getticket(String accessToken, String type){
        BufferedReader in = null;
        logger.info("微信获取加密秘钥,accessToken:{},type:{}", accessToken, type);
        //根据accesstoken获取秘钥，拿到的access_token 采用http GET方式请求获得jsapi_ticket（有效期7200秒，开发者必须在自己的服务全局缓存jsapi_ticket）
        // 成功返回如下JSON：
        //{
        //"errcode":0,
        //"errmsg":"ok",
        //"ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
        //"expires_in":7200
        //}
        StringBuilder urlBuf = new StringBuilder(getTicketUrl);
        urlBuf.append("?access_token=");
        urlBuf.append(accessToken);
        urlBuf.append("&type=");
        urlBuf.append(StringUtils.hasText(type) ? type : "jsapi");

        logger.info("微信获取加密秘钥url:{},accessToken:{},type:{}",
                urlBuf, accessToken, type);
//        String url="https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        try {
            URL weChatUrl = new URL(urlBuf.toString());
            // 打开和URL之间的连接
            URLConnection connection = weChatUrl.openConnection();
            // 设置通用的请求属性
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            logger.info("微信获取加密秘钥结果:{},accessToken:{}", sb, accessToken);
            return sb.toString();

        } catch (Exception e1) {
            logger.info("微信获取加密秘钥异常,accessToken:{}", accessToken, e1);
            throw new RuntimeException(e1);
        }  finally {//使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    /**
     * @author: lianghaifei
     * @method  getAccessToken
     * @date: 2019/4/30 16:57
     * @param appId
     * @param secret
     * @return java.lang.String
     * @description 获取微信AccessToken
     */
    public static String getAccessToken(String appId, String secret) {
        String access_token = "";
        String grant_type = "client_credential";//获取access_token填写client_credential
//        String AppId="APPID";//第三方用户唯一凭证
//        String secret="APPSECRET";//第三方用户唯一凭证密钥，即appsecret
        //这个url链接地址和参数皆不能变
        StringBuffer urlBuf = new StringBuffer(getTokenUrl);
        urlBuf.append("?grant_type=");
        urlBuf.append(grant_type);
        urlBuf.append("&appid=");
        urlBuf.append(appId);
        urlBuf.append("&secret=");
        urlBuf.append(secret);
//        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+grant_type+"&appid="+appId+"&secret="+secret;

        try {
            URL urlGet = new URL(urlBuf.toString());
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");

            Map demoJson = JacksonUtils.readValue(message, Map.class);
            logger.info("JSON字符串：{}", demoJson);
            access_token = demoJson.get("access_token").toString();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return access_token;
    }
    /**
     * @author: lianghaifei
     * @method  getUserInfo
     * @date: 2019/4/30 15:48
     * @param accessToken
     * @param openId
     * @return java.lang.String
     * @description 获取微信用户信息接口
     */
    public static String getUserInfo(String accessToken, String openId)
    {
        BufferedReader in = null;
        StringBuffer urlBuffer = new StringBuffer(getUserInfoUrl);

        urlBuffer.append("?access_token=");
        urlBuffer.append(accessToken);
        urlBuffer.append("&openid=");
        urlBuffer.append(openId);
        urlBuffer.append("&lang=zh_CN");

        try {
            URL weChatUrl = new URL(urlBuffer.toString());
            // 打开和URL之间的连接
            URLConnection connection = weChatUrl.openConnection();
            // 设置通用的请求属性
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }  finally {//使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 上传图片
     * @Author dongzhihua
     * @Date 2020-02-03 18:03
     */
    public static String uploadImg(String accessToken, File file) {
        String urlStr = uploadImgUrl + accessToken;
        String res;
        HttpURLConnection conn = null;
        String BOUNDARY = "--------";//"---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
        OutputStream out = null;
        InputStreamReader isr = null;
        BufferedReader reader = null;
        DataInputStream in = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            out = new DataOutputStream(conn.getOutputStream());

            // file
            String inputName = "";
            String filename = file.getName();
            String contentType = new MimetypesFileTypeMap().getContentType(file);
            if (filename.endsWith(".png")) {
                contentType = "image/png";
            }
            if (contentType == null || contentType.equals("")) {
                contentType = "application/octet-stream";
            }

            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            strBuf.append(
                    "Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
            strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
            out.write(strBuf.toString().getBytes());
            in = new DataInputStream(new FileInputStream(file));
            int bytes;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();

            // 读取返回数据
            StringBuffer strBuf2 = new StringBuffer();
            isr = new InputStreamReader(conn.getInputStream());
            reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                strBuf2.append(line).append("\n");
            }
            res = strBuf2.toString();
        } catch (Exception e) {
            throw new RuntimeException("上传图片出现异常", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            close(out);
            close(isr);
            close(reader);
            close(in);
        }
        return res;
    }

    /**
     * 保存卡片，通过模板
     * @Author dongzhihua
     * @Date 2020-02-06 21:00
     */
    public static String saveCardByTemplate(String accessToken, String paramTemplate, Object templateParam, boolean isCreate) {
        String json = VelocityUtil.evaluate(paramTemplate, templateParam);
        return saveCard(accessToken, json, isCreate);
    }

    /**
     * 保存卡片
     * @Author dongzhihua
     * @Date 2020-02-06 21:00
     */
    public static String saveCard(String accessToken, String param, boolean isCreate) {

        String url;
        if (isCreate) {
            url = createCardUrl + accessToken;
        } else {
            url = updateCardUrl + accessToken;
        }
        try {
            return sendPost(url, param);
        } catch (Exception e) {
            throw new RuntimeException("创建通用卡异常", e);
        }
    }

    /**
     * 创建卡片
     * @Author dongzhihua
     * @Date 2020-02-03 18:29
     */
    public static String saveGeneralCard(String accessToken, boolean isCreate) {

        Map<String, Object> map = new HashMap<>();
        map.put("activate_url", "https://api.abc.jd.com"); // 卡片激活链接
        map.put("brand_name", "北京大学"); // 卡片激活链接
        map.put("custom_url", "https://www.jd.com"); // 应用广场

        String paramTemp = "{\n" +
                "  \"card\": {\n" +
                "    \"card_type\": \"GENERAL_CARD\",\n" +
                "    \"general_card\": {\n" +
                "      \"sub_card_type\": \"CAMPUS_CARD\",\n" +
                "      \"activate_url\": \"$obj.activate_url\",\n" +
                "      \"base_info\": {\n" +
                "        \"use_dynamic_code\": true,\n" +
                "        \"logo_url\": \"https://mmbiz.qlogo.cn/mmbiz/p98FjXy8LaeMq67mEpDmkj05EtiaVcGOibVaVux3Agib1ibcHFkCoic7HuQWFawx9XGCNWIO085drjdxTib2nBHlYGAA/0?wx_fmt=gif\",\n" +
                "        \"brand_name\": \"$obj.brand_name\",\n" +
                "        \"code_type\": \"CODE_TYPE_TEXT\",\n" +
                "        \"title\": \"京东数字科技\",\n" +
                "        \"sub_title\": \"校园电子卡\",\n" +
                "        \"color\": \"Color020\",\n" +
                "        \"notice\": \"请出示二维码\",\n" +
                "        \"description\": \"校园出入和消费凭证\",\n" +
                "        \"date_info\": {\n" +
                "          \"type\": \"DATE_TYPE_PERMANENT\"\n" +
                "        },\n" +
                "        \"sku\": {\n" +
                "          \"quantity\": 500000000\n" +
                "        },\n" +
                "        \"get_limit\": 1,\n" +
                "        \"use_custom_code\": true,\n" +
                "        \"bind_openid\": true,\n" +
                "        \"can_give_friend\": false,\n" +
                "        \"need_push_on_view\": true,\n" +
                "        \"custom_url_name\": \"cus广场\",\n" +
                "        \"custom_url\": \"$obj.custom_url\",\n" +
                "        \"promotion_url_name\": \"pro广场\",\n" +
                "        \"promotion_url\": \"$obj.custom_url\"\n" +
                "      },\n" +
                "      \"supply_bonus\": true,\n" +
                "      \"supply_balance\": false,\n" +
                "      \"custom_field1\": {\n" +
                "        \"name_type\": \"FIELD_NAME_TYPE_LEVEL\",\n" +
                "        \"name\": \"name1\",\n" +
                "        \"url\": \"www.jd.com\"\n" +
                "      },\n" +
                "      \"custom_field2\": {\n" +
                "        \"name_type\": \"FIELD_NAME_TYPE_COUPON\",\n" +
                "        \"name\": \"name2\",\n" +
                "        \"url\": \"www.baidu.com\"\n" +
                "      },\n" +
                "      \"custom_field3\": {\n" +
                "        \"name\": \"name3\",\n" +
                "        \"url\": \"www.baidu.com\"\n" +
                "      },\n" +
                "      \"custom_cell1\": {\n" +
                "        \"name\": \"使用入口1\",\n" +
                "        \"tips\": \"激活后显示\",\n" +
                "        \"url\": \"http://www.qq.com\"\n" +
                "      },\n" +
                "      \"custom_cell2\": {\n" +
                "        \"name\": \"使用入口2\",\n" +
                "        \"tips\": \"激活后显示\",\n" +
                "        \"url\": \"http://www.qq.com\"\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        try {
            String param = VelocityUtil.evaluate(paramTemp, map);
            return saveCard(accessToken, param, isCreate);
        } catch (Exception e) {
            throw new RuntimeException("创建通用卡异常", e);
        }
    }

    /**
     * 导入卡号
     * @Author dongzhihua
     * @Date 2020-02-03 18:50
     */
    public static String depositCardCode(String accessToken, String cardId, List<String> codes) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("card_id", cardId);
            param.put("code", codes);
            String str = JacksonUtils.toJson(param);
            return sendPost(depositCardCodeUrl + accessToken, str);
        } catch (Exception e) {
            throw new RuntimeException("导入卡号异常", e);
        }
    }

    /**
     * 创建领卡二维码
     * @Author dongzhihua
     * @Date 2020-02-04 10:41
     */
    public static String createGetCardQrcode(String accessToken, String cardId, String code, String openId) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("cardId", cardId);
            map.put("code", code);
            map.put("openId", openId);
            String param = "{\n" +
                    "    \"action_name\": \"QR_CARD\", \n" +
                    "    \"expire_seconds\": 1800, \n" +
                    "    \"action_info\": {\n" +
                    "        \"card\": {\n" +
                    "            \"card_id\": \"$obj.cardId\", \n" +
                    "            \"code\": \"$obj.code\", \n" +
                    "            \"openid\": \"$obj.openId\"\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            param = VelocityUtil.evaluate(param, map);
            return sendPost(createGetCardQrcodeUrl + accessToken, param);
        } catch (Exception e) {
            throw new RuntimeException("生成领卡二维码异常", e);
        }
    }

    /**
     *
     * 激活卡片
     * @Author dongzhihua
     * @Date 2020-02-04 10:27
     * @param accessToken
     * @param code 领卡返回的卡片code
     * @param cardNo 显示的卡号
     * @param cardId 卡券id
     * @param picUrl 背景图片
     * @return
     */
    public static String activeCard(String accessToken, String code, String cardNo, String cardId, String picUrl) {
        String paramTemplate = "{" +
                "  \"card_number\": \"$obj.cardNo\"," +
                "  \"code\": \"$obj.code\"," +
                "  \"card_id\": \"$obj.cardId\"," +
                "  \"background_pic_url\": \"$obj.backgroundPicUrl\"" +
                "}";
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("cardNo", cardNo);
        map.put("cardId", cardId);
        map.put("picUrl", picUrl);
        String param = VelocityUtil.evaluate(paramTemplate, map);
        try {
            return sendPost(activeCardUrl + accessToken, param);
        } catch (Exception e) {
            throw new RuntimeException("激活卡片失败", e);
        }
    }

    /**
     * 发送post请求
     * @Author dongzhihua
     * @Date 2020-02-03 18:08
     */
    public static String sendPost(String url, String param) throws Exception {
        logger.info("sendPost > url: {}, param: {}", url, param);
        OutputStreamWriter out = null;
        BufferedReader in = null;
        HttpURLConnection conn = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            conn = (HttpURLConnection) realUrl.openConnection();
            // 打开和URL之间的连接

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法

            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Type","application/json");

            conn.connect();

            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } finally {
            conn.disconnect();
            close(out);
            close(in);
        }
        return result;
    }

    public static void close(Closeable a) {
        if (a != null) {
            try {
                a.close();
            } catch (IOException e) {
                logger.warn("关闭流异常");
            }
        }
    }
    public static void main(String[] args) {
        String aa = signature("7f0949ad45bf46e4","1555590328", "http://jpay.jd.com/pay-c-ecard-m", "HoagFKDcsGMVCIY2vOjf9nEXfTGxsBl5ekoM7EOzzQa0cxkSPVvO3fCE69gbYXFFH91it2jzFsyqkSbZa3rhxQ");
        System.out.println(aa);
    }
}