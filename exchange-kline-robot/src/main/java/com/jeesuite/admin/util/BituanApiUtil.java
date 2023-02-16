package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.*;

/**
 * api 工具
 *
 * @author kew
 * @create 2018-08-18 下午6:11
 **/
@Slf4j
public class BituanApiUtil implements ApiUtil {

    private String serverUrl;

    private String accessKey;

    private String secretKey;

    public BituanApiUtil(String serverUrl, String accessKey, String securityKey) {
        this.serverUrl = serverUrl;
        this.accessKey = accessKey;
        this.secretKey = securityKey;
    }

    public BituanApiUtil() {
        this("http://api.bituan.com");
    }

    public BituanApiUtil(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    private String signStr(HashMap<String, String> param) {
        String sign = createLinkString(param);
        return sign(sign, secretKey);
    }

    public String sign(String message, String secret) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(message);
            stringBuilder.append(secret);
            String signString = stringBuilder.toString();
            System.out.println(signString);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            StringBuffer buffer = new StringBuffer();
            byte[] result = md5.digest(signString.getBytes("utf-8"));
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to sign message.", e);
        }
    }

    /**
     * 生成排序字符串
     *
     * @param params
     * @return
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            prestr = prestr + key + value;
        }
        return prestr;
    }

    public String signUpParam(HashMap<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        for (String key : paramMap.keySet()) {
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }
        return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
    }

    @Override
    public JSONObject getTicker(String currency) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("symbol", currency);
        JSONObject result = getData(HttpMethod.GET, "/open/api/get_ticker", paramMap);
        return result.getJSONObject("data");
    }

    public JSONArray getAllExchangeType() {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        JSONObject result = getData(HttpMethod.GET, "/open/api/common/symbols", paramMap);
        return result.getJSONArray("data");
    }


    public void getKline(String currency, String period) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("symbol", currency);
        paramMap.put("period", period);
        JSONObject result = getData(HttpMethod.GET, "/open/api/get_records", paramMap);
        System.out.println(result);
    }

    public JSONObject getDepth(String currency) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("symbol", currency);
        paramMap.put("type", "step0");
        JSONObject result = getData(HttpMethod.GET, "/open/api/market_dept", paramMap);
        if (result.getString("code").equals("0")) {
            return result.getJSONObject("data").getJSONObject("tick");
        } else {
            log.error("[获取深度异常] 下单失败 domain->{}, currencyCode->{}", serverUrl, currency);
            throw new RuntimeException(result.toString());
        }
    }


    public JSONArray getTrades(String currency) {
        HashMap<String, Object> paramMap = new LinkedHashMap();
        paramMap.put("symbol", currency);
        JSONObject result = getData(HttpMethod.GET, "/open/api/get_trades", paramMap);
        if (result.getString("code").equals("0")) {
            return result.getJSONArray("data");
        } else {
            log.error("[自动下单] 下单失败 domain->{}, currencyCode->{}", serverUrl, currency);
//            throw new RuntimeException(result.toString());
            return null;
        }
    }


    public JSONObject getBalance() {
        HashMap<String, String> paramMap = new LinkedHashMap();
        paramMap.put("api_key", accessKey);
        Long timestamp = System.currentTimeMillis() / 1000;
        paramMap.put("time", timestamp.toString());
        String sign = signStr(paramMap);
        paramMap.put("sign", sign);
        //JSONObject result = exchange(HttpMethod.GET, "/open/api/user/account",JSONObject.toJSONString(paramMap));
        JSONObject result = getData(HttpMethod.GET, "/open/api/user/account", paramMap);
        if (result.getString("code").equals("0")) {
            return result.getJSONObject("data");
        } else {
            log.error("[获取账户信息] 下单失败 domain->{}", serverUrl);
            throw new RuntimeException(result.toString());
        }
        //System.out.println(result);
    }

    public JSONObject getOrders(String currencyCode) {
        HashMap<String, String> paramMap = new LinkedHashMap();
        paramMap.put("api_key", accessKey);
        Long timestamp = System.currentTimeMillis() / 1000;
        paramMap.put("time", timestamp.toString());
        paramMap.put("symbol", currencyCode);
        //paramMap.put("pageSize","10");
        //paramMap.put("page","0");
        String sign = signStr(paramMap);
        paramMap.put("sign", sign);
        JSONObject result = getData(HttpMethod.GET, "/open/api/all_order", paramMap);
        if (result.getString("code").equals("0")) {
            return result.getJSONObject("data");
        } else {
            log.error("[自动下单] 下单失败 domain->{},currencyCode->{}", serverUrl, currencyCode);
            //throw new RuntimeException(result.toString());
            return null;
        }
        //System.out.println(result);
    }

    @Override
    public JSONObject getOrders(String currency, Integer pageSize) {
        return null;
    }

    public JSONObject getOrder(String orderId, String symbol) {
        HashMap<String, String> paramMap = new LinkedHashMap();
        paramMap.put("order_id", orderId);
        paramMap.put("api_key", accessKey);
        Long timestamp = System.currentTimeMillis() / 1000;
        paramMap.put("time", timestamp.toString());
        paramMap.put("symbol", symbol);
        //paramMap.put("fee_is_user_exchange_coin","0");//0，当交易所有平台币时，此参数表示是否使用用平台币支付手续费，0否，1是
        String sign = signStr(paramMap);
        paramMap.put("sign", sign);
        JSONObject result = getData(HttpMethod.GET, "/open/api/order_info", paramMap);
        if (result.getString("code").equals("0")) {
            log.info("res->{}", result);
            return result.getJSONObject("data").getJSONObject("order_info");
        } else {
            log.error("[自动下单] 下单失败 domain->{}, orderId->{},currencyCode->{}", serverUrl, orderId, symbol);
            throw new RuntimeException(result.toString());
        }
    }

    public void cancel(String orderId, String symbol) {
        HashMap<String, String> paramMap = new LinkedHashMap();
        paramMap.put("order_id", orderId);
        paramMap.put("api_key", accessKey);
        Long timestamp = System.currentTimeMillis() / 1000;
        paramMap.put("time", timestamp.toString());
        paramMap.put("symbol", symbol);
        //paramMap.put("fee_is_user_exchange_coin","0");//0，当交易所有平台币时，此参数表示是否使用用平台币支付手续费，0否，1是
        String sign = signStr(paramMap);
        paramMap.put("sign", sign);
        JSONObject result = exchange(HttpMethod.POST, "/open/api/cancel_order", paramMap);
        if (result.getString("code").equals("0")) {
        } else {
            log.error("[自动下单] 下单失败 domain->{}, orderId->{},currencyCode->{}", serverUrl, orderId, symbol);
            throw new RuntimeException(result.toString());
        }
    }

    public String createOrder(String symbol, boolean tradeType, String price, String amount) {
        HashMap<String, String> paramMap = new LinkedHashMap();
        //BUY,SELL
        paramMap.put("side", tradeType ? "BUY" : "SELL");
        paramMap.put("type", "1");//挂单类型，1:限价委托、2:市价委托
        paramMap.put("volume", amount);//购买数量（多义，复用字段）,type=1:表示买卖数量,type=2:买则表示总价格，卖表示总个数,买卖限制user/me-用户信息
        paramMap.put("api_key", accessKey);
        paramMap.put("price", price);//委托单价：type=2：不需要此参数
        Long timestamp = System.currentTimeMillis() / 1000;
        paramMap.put("time", timestamp.toString());
        paramMap.put("symbol", symbol);
        //paramMap.put("fee_is_user_exchange_coin","0");//0，当交易所有平台币时，此参数表示是否使用用平台币支付手续费，0否，1是
        String sign = signStr(paramMap);
        paramMap.put("sign", sign);
        JSONObject result = exchange(HttpMethod.POST, "/open/api/create_order", paramMap);
        if (result.getString("code").equals("0")) {
            return result.getJSONObject("data").getString("order_id");
        } else {
            log.error("[自动下单] 下单失败 domain->{}, price->{},amount->{},currencyCode->{}", serverUrl, price, amount, symbol);
            throw new RuntimeException(result.toString());
        }
    }

    private JSONObject getData(HttpMethod method, String path, HashMap param) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        String uri = signUpParam(param);
        StringBuffer request = new StringBuffer();
        request.append(path);
        request.append("?");
        request.append(uri);
        System.out.println(request);
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                System.out.println(response);
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
                System.out.println(response);
            }
        });
        ResponseEntity<String> response = restTemplate.getForEntity(serverUrl + request, String.class);
        return JSONObject.parseObject(response.getBody());
    }

    private JSONObject exchange(HttpMethod method, String path, HashMap param) {
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) {
                System.out.println(response);
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) {
                System.out.println(response);
            }
        });
        HttpHeaders requestHeaders = new HttpHeaders();
        //requestHeaders.add("FC-ACCESS-KEY", accessKey);
        //requestHeaders.add("FC-ACCESS-SIGNATURE", sign);
        //requestHeaders.add("FC-ACCESS-TIMESTAMP", timestamp.toString());
        if (method == HttpMethod.POST) {
            requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }
        String uri = signUpParam(param);
        StringBuffer request = new StringBuffer();
        request.append(path);
        request.append("?");
        request.append(uri);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        String response = restTemplate.postForObject(serverUrl + request, requestEntity, String.class);
        return JSONObject.parseObject(response);
    }


    public static void main(String[] args) {
        BituanApiUtil apiUtil = new BituanApiUtil("https://api.bituan.com", "479eaf72a3ceea8232700aae46b6153a", "59036dd607bd402e007178172d660bb2");
        //apiUtil.getDepth("ethbtc");
        //apiUtil.getKline();
        //apiUtil.getBalance();
        //apiUtil.getOrders("ethbtc");
        //apiUtil.getOrder("1","ethbtc");
        //apiUtil.cancel("1","ethbtc");
        System.out.println(apiUtil.getTicker("ethbtc"));
        //System.out.println(apiUtil.getTrades("ethbtc"));
//        System.out.println(apiUtil.getDepth("ethbtc"));
//        apiUtil.createOrder("ethbtc", "BUY", "1", "1.200");
    }

}
