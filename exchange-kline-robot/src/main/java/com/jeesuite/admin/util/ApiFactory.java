package com.jeesuite.admin.util;

/**
 * api 工厂类
 *
 * @author kew
 * @create 2018-09-01 下午12:14
 **/

public class ApiFactory {

    public static ApiUtil getInstance(String channel, String serverUrl) {
        ApiUtil apiUtil = null;
        switch (channel) {
            case "flycoin":
                apiUtil = new KeDouUtil(serverUrl);
                break;
            case "bituan":
                apiUtil = new BituanApiUtil(serverUrl);
                break;
        }
        return apiUtil;
    }

    public static ApiUtil getInstance(String channel, String serverUrl, String accessKey, String securtiyKey) {
        ApiUtil apiUtil = null;
        switch (channel) {
            case "flycoin":
                apiUtil = new KeDouUtil(serverUrl, accessKey, securtiyKey);
                break;
            case "bituan":
                apiUtil = new BituanApiUtil(serverUrl, accessKey, securtiyKey);
                break;
        }
        return apiUtil;
    }
}
