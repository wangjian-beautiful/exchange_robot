package com.bjs.hedge.util;


import com.alibaba.fastjson.JSONObject;

import com.bjs.hedge.util.http.HttpClientRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 短信、邮箱验证码
 *
 * @author zouyj
 */
public class ValidateCodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeUtil.class);
    private static final Integer SUCCESS = 100;
    //阿里验证码错误次数
    private static Integer errorNum = 0;


    public static JSONObject getJsonByKey() {
        //表：config_kv_store     config_key=sms_by_smsbao
        String str = "{\"urlIn\":\"https://api.smsbao.com/wsms\",\"urlCn\":\"https://api.smsbao.com/sms\",\"user\":\"jiaodayu\",\"password\":\"bijinsuo01\"}";
        return JSONObject.parseObject(str);
    }

    public static Boolean sendSmsBySmsbao(String countryCode, String mobile, String msg) {

        if (StringUtils.isEmpty(mobile)) {
            logger.error("短信发送错误：手机号码为空。手机号码：" + mobile);
            return false;
        }
        if (StringUtils.isEmpty(msg)) {
            logger.error("短信发送错误：短信内容为空");
            return false;
        }
        if (StringUtils.isEmpty(countryCode)) {
            logger.error("短信发送错误：国家号内容为空");
            return false;
        }
        if (!msg.contains("【")) {
            msg = "【BJS】" + msg;
        }
        try {
            JSONObject smsBySmsbao = getJsonByKey();
            String httpUrl = "";
            String username = ""; //在短信宝注册的用户名
            String password = ""; //在短信宝注册的密码

            if (smsBySmsbao != null) {
                /** 短信宝国内 国外通道*/
                if (countryCode.contains("86")) {
                    httpUrl = smsBySmsbao.getString("urlCn");
                } else {
                    httpUrl = smsBySmsbao.getString("urlIn");
                }
                username = smsBySmsbao.getString("user");
                password = smsBySmsbao.getString("password");
            } else {
                logger.error("sms_by_smsbao内容为空");
                return false;
            }
            StringBuffer httpArg = new StringBuffer();
            httpArg.append("?u=").append(username).append("&");
            httpArg.append("p=").append(MD5Util.getMD5(password)).append("&");
            /** 短信宝国内 不需要 countryCode 号码不需要encodeUrlString*/
            if (countryCode.contains("86")) {
                httpArg.append("m=").append(mobile).append("&");
            } else {
                countryCode = countryCode.trim().replace("+", "");
                mobile = "+" + countryCode + mobile;
                httpArg.append("m=").append(encodeUrlString(mobile, "UTF-8")).append("&");
            }
            httpArg.append("c=").append(encodeUrlString(msg, "UTF-8"));

            String result = HttpClientRequest.get(httpUrl + httpArg, null, null);
            if (StringUtils.isEmpty(result)) {
                logger.error("短信发送错误：返回结果为null." + mobile);
                return false;
            }
            //TODO 处理返回值,参见HTTP协议文档
            // 成功时返回json字符串
            if ("0".equals(result)) {
                return true;
            } else {
                logger.error("发送短信错误，返回结果格式不正确：" + result);
                return false;
            }
        } catch (Exception e) {
            //TODO 处理异常
            logger.error("发送短信请求接口异常：" + e.getMessage());
            return false;
        }
    }

    public static String encodeUrlString(String str, String charset) {
        String strret = null;
        if (str == null) return str;
        try {
            strret = java.net.URLEncoder.encode(str, charset);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strret;
    }


    public static void main(String[] args) {

        Boolean 测试短信 = sendSmsBySmsbao("+86", "18301354739", "测试短信");
        System.out.println(测试短信);
    }

}
