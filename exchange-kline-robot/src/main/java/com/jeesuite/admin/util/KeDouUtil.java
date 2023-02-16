package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by qiudanping on 2017/8/21.
 */
@Slf4j
public class KeDouUtil implements ApiUtil {

    private RestTemplate restTemplate = new RestTemplate();

    private String domainUrl;

    private String accessKey;

    private String secretKey;

    {
        domainUrl = "http://api.laex.pro/api/v2/";
        secretKey = "c5b4d124df264c7a96bc35e624b311ba";
        accessKey = "ak152291fdfff345e1";
    }


    public static void main1(String[] args) {
//        KeDouUtil api = new KeDouUtil("http://api.laex.pro/");
//        for (int i = 0; i < 200; i++) {
//            apiControllerTest.order();
//        }
//        System.out.println(api.getDepth("hsr_qc"));
//        System.out.println(System.currentTimeMillis());
//        apiControllerTest.getOrder();
//        apiControllerTest.getOrders();
//        apiControllerTest.cancel();
//        apiControllerTest.getAccountInfo();
//        apiControllerTest.getRechargeRecord();
//        apiControllerTest.getCnyRechargeRecord();
//        apiControllerTest.getCnyWithdrawRecord();
//        apiControllerTest.getRechargeAddress();
//        apiControllerTest.getWithdrawAddress();
//        apiControllerTest.getWithdrawRecord();
//        apiControllerTest.withdraw();

    }

    public KeDouUtil(String domainUrl, String accessKey, String secretKey) {
        this.domainUrl = domainUrl;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public KeDouUtil(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    @Override
    public JSONArray getTrades(String currencyCode) {
        String result = restTemplate.getForObject(domainUrl + "/data/v2/trades?currency=" + currencyCode,String.class);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getJSONArray("data");
    }

    public JSONObject getTicker(String currencyCode) {
        return JSONObject.parseObject(restTemplate.getForObject(domainUrl + "/data/v2/ticker?currency=" + currencyCode, String.class)).getJSONObject("data").getJSONObject("ticker");
    }

    @Override
    public JSONObject getDepth(String currencyCode) {
        return JSONObject.parseObject(restTemplate.getForObject(domainUrl + "/data/v2/depth?merge=0.00000001&currency=" + currencyCode, String.class)).getJSONObject("data");
    }


    @Override
    public String createOrder(String currencyCode, boolean orderType, String price, String count) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "order");
        paramMap.put("accesskey", accessKey);
        paramMap.put("price", price);
        paramMap.put("amount", count);
        paramMap.put("tradeType", orderType ? "0" : "1");
        paramMap.put("currency", currencyCode);
        signUp(paramMap);
        String path = domainUrl + "/api/v2/order?method={method}" +
                "&accesskey={accesskey}&price={price}&amount={amount}&tradeType={tradeType}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}";
//        System.out.println("创建订单路径：" + path);
        String result0 = restTemplate.getForObject(path, String.class, paramMap);
        JSONObject result = JSONObject.parseObject(result0);
        log.info("[自动下单] ---->开始下单 domain->{}, price->{},amount->{},currencyCode->{}", domainUrl, price, count, currencyCode);
        if (result.getString("code").equals("0000")) {
            return result.getString("data");
        } else {
            log.error("[自动下单] 下单失败 domain->{}, price->{},amount->{},currencyCode->{}", domainUrl, price, count, currencyCode);
            throw new RuntimeException(result.toString());
        }
    }

    @Override
    public void cancel(String id, String currencyCode) {
        if (getOrderInfo(id, currencyCode)) {
            return;
        }
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "cancel");
        paramMap.put("accesskey", accessKey);
        paramMap.put("id", id.toString());
        paramMap.put("currency", currencyCode);
        signUp(paramMap);
        String path = domainUrl + "/api/v2/cancel?method={method}" +
                "&accesskey={accesskey}&id={id}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}";
//        System.out.println("取消订单路径：" + path);
        JSONObject result = JSONObject.parseObject(restTemplate.getForObject(path, String.class, paramMap));
        log.info("[取消订单] --->取消订单 domain->{}, id->{},currencyCode->{}", domainUrl, id, currencyCode);
        if (result.getString("code").equals("0000")) {
//            return result.getInteger("id");
        } else {
            throw new RuntimeException(result.toString());
        }
    }

    @Override
    public JSONObject getOrder(String orderNo, String currencyCode) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getOrder");
        paramMap.put("accesskey", accessKey);
        paramMap.put("id", orderNo);
        paramMap.put("currency", currencyCode);
        signUp(paramMap);
        JSONObject result = JSONObject.parseObject(restTemplate.getForObject(domainUrl + "/api/v2/getOrder?method={method}" +
                "&accesskey={accesskey}&id={id}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap));
        if (result.getString("code").equals("0000")) {
            return result.getJSONObject("data");
        } else {
            log.error("[自动下单] 下单失败 domain->{}, orderId->{},currencyCode->{}", domainUrl, orderNo, currencyCode);
            throw new RuntimeException(result.toString());
        }
    }

    public boolean getOrderInfo(String orderId, String currency) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getOrder");
        paramMap.put("accesskey", accessKey);
        paramMap.put("id", orderId);
        paramMap.put("currency", currency);
        signUp(paramMap);
        JSONObject result = JSONObject.parseObject(restTemplate.getForObject(domainUrl + "/api/v2/getOrder?method={method}" +
                "&accesskey={accesskey}&id={id}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap));
        if (result.getString("code").equals("0000") && result.getJSONObject("data").getInteger("status").equals(3)) {
//            return result.getInteger("id");
            return true;
        } else {
            return false;
//            throw new RuntimeException(result.toString());
        }
    }

    @Override
    public JSONObject getOrders(String currency, Integer pageSize) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getOrders");
        paramMap.put("accesskey", accessKey);
        paramMap.put("tradeType", "1");
        paramMap.put("currency", currency);
        paramMap.put("isAll", 0); // 0-所有人的委托单，1-机器人自己的委托单
        paramMap.put("pageIndex", 1);
        paramMap.put("pageSize", pageSize);
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getOrders?method={method}" +
                "&accesskey={accesskey}&tradeType={tradeType}&currency={currency}&isAll={isAll}&pageIndex={pageIndex}&pageSize={pageSize}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        return JSONObject.parseObject(result);
    }

    @Override
    public JSONObject getOrders(String currency) {
        return getOrders(currency, 40);
    }

    public void getAccountInfo() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getAccountInfo");
        paramMap.put("accesskey", accessKey);
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getAccountInfo?method={method}" +
                "&accesskey={accesskey}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    public void getUserAddress() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getRechargeAddress");
        paramMap.put("accesskey", accessKey);
        paramMap.put("currency", "etc");
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getRechargeAddress?method={method}" +
                "&accesskey={accesskey}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    public void getWithdrawAddress() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getWithdrawAddress");
        paramMap.put("accesskey", accessKey);
        paramMap.put("currency", "etc");
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getWithdrawAddress?method={method}" +
                "&accesskey={accesskey}&currency={currency}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);

        System.out.println(result);
    }

    public void getWithdrawRecord() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getWithdrawRecord");
        paramMap.put("accesskey", accessKey);
        paramMap.put("currency", "btm");
        paramMap.put("pageIndex", 1);
        paramMap.put("pageSize", 40);
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getWithdrawRecord?method={method}" +
                "&accesskey={accesskey}&currency={currency}&pageIndex={pageIndex}&pageSize={pageSize}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }


    public void getChargeRecord() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getRechargeRecord");
        paramMap.put("accesskey", accessKey);
        paramMap.put("currency", "BTM");
        paramMap.put("pageIndex", 1);
        paramMap.put("pageSize", 40);
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getRechargeRecord?method={method}" +
                "&accesskey={accesskey}&currency={currency}&pageIndex={pageIndex}&pageSize={pageSize}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);

        System.out.println(result);
    }

    public void getCnyWithdrawRecord() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getCnyWithdrawRecord");
        paramMap.put("accesskey", accessKey);
        paramMap.put("pageIndex", 1);
        paramMap.put("pageSize", 40);
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getCnyWithdrawRecord?method={method}" +
                "&accesskey={accesskey}&pageIndex={pageIndex}&pageSize={pageSize}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    public void getCnyChargeRecord() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("method", "getCnyRechargeRecord");
        paramMap.put("accesskey", accessKey);
        paramMap.put("pageIndex", 1);
        paramMap.put("pageSize", 40);
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/getCnyRechargeRecord?method={method}" +
                "&accesskey={accesskey}&pageIndex={pageIndex}&pageSize={pageSize}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }


    public void withdraw() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("accesskey", accessKey);
        paramMap.put("amount", "2");
        paramMap.put("currency", "etc");
        paramMap.put("fees", "1");
        paramMap.put("method", "withdraw");
        paramMap.put("receiveAddr", "0x992c9dc9c0bcffa42f69b98d53800c1748febaa9");
        paramMap.put("safePwd", "123456qq");
        signUp(paramMap);
        String result = restTemplate.getForObject(domainUrl + "/api/v2/withdraw?" +
                "accesskey={accesskey}&amount={amount}&currency={currency}&fees={fees}&method={method}&receiveAddr={receiveAddr}&safePwd={safePwd}" +
                "&sign={sign}&reqTime={reqTime}", String.class, paramMap);
        System.out.println(result);
    }

    @Override
    public JSONArray getAllExchangeType() {
        //手动设置
        return null;
    }

    @Override
    public JSONObject getBalance() {
        return null;
    }


    private void signUp(HashMap paramMap) {
        try {
            //......
            String params = signUpParam(paramMap);
            String secret = EncryptDigestUtil.digest(secretKey);
            String sign = EncryptDigestUtil.hmacSign(params, secret);
            paramMap.put("sign", sign);
            paramMap.put("reqTime", System.currentTimeMillis());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public String signUpParam(HashMap<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (String key : paramMap.keySet()) {
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }


    public static void main(String[] args) {
        KeDouUtil util = new KeDouUtil("http://api.bbc366.com", "ak152291fdfff345e1", "c5b4d124df264c7a96bc35e624b311ba");
        JSONObject jsonObject = util.getOrders("5gl_usdt");
        System.out.println(jsonObject.toString());
    }
}