package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.vo.TradeAddVo;
import com.jeesuite.admin.vo.TradeBatchAddVo;
import com.jeesuite.admin.vo.TradeCheBuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class RobotSix {

    private RestTemplate restTemplate = new RestTemplate();

    private String domainUrl;

    private String accessKey;

    private String secret;

    public RobotSix(String domainUrl, String accessKey, String secret) {
        this.domainUrl = domainUrl;
        this.accessKey = accessKey;
        this.secret = secret;
    }

    public void createOrderEx(String currencyCode, String type, String price, String count){
        String url = this.domainUrl + "api/v1/user/trade";
        String ts = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();//参数按字典序排列
        paramMap.add("accessKey", this.accessKey);
        paramMap.add("timestamp", ts);
        paramMap.add("market", currencyCode);//btc_usdt
        paramMap.add("num", count);
        paramMap.add("price", price);
        paramMap.add("type", type);//1买2卖

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currencyCode);//btc_usdt
        params.put("num", count);
        params.put("price", price);
        params.put("type", type);//1买2卖
        String sign = this.getSignature(params,this.secret);
        paramMap.add("sign",sign);
        try {
//            log.info("[开始下单] domain->{},买卖类型type->{}, price->{},amount->{},currencyCode->{}", domainUrl, type, price, count, currencyCode);
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            JSONObject result = JSONObject.parseObject(result0);
//            log.info("[下单结果] result->{},domain->{},买卖类型type->{}, price->{},amount->{},currencyCode->{}",
//                    result.toString(), domainUrl, type, price, count, currencyCode);
//            return result;
        } catch (Exception e) {//超时或网络异常
            log.error("[下单失败] domain->{},买卖type:{} price->{},amount->{},currencyCode->{},e->{}", domainUrl, type, price, count, currencyCode,e);
//            JSONObject result = new JSONObject();
//            result.put("error", "-1");
//            return result;
        }
    }

    public void batchCreateOrderEx(String currencyCode, List<TradeAddVo> tradeAddVoList){
        String ts = String.valueOf(System.currentTimeMillis());
        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currencyCode);//btc_usdt
        String sign = this.getSignature(params,this.secret);

        String url = String.format("%s%s?accessKey=%s&timestamp=%s&market=%s&sign=%s",this.domainUrl,"api/v1/user/tradeBatch", this.accessKey, ts, currencyCode, sign );


        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

            String content = JSONObject.toJSONString(TradeBatchAddVo.builder().trades(tradeAddVoList).build() );

            HttpEntity<String> request = new HttpEntity<>(content, headers);

//            log.info("[开始下单] domain->{},买卖类型type->{}, price->{},amount->{},currencyCode->{}", domainUrl, type, price, count, currencyCode);
            String result0 = restTemplate.postForEntity(url, request, String.class).getBody();

            JSONObject result = JSONObject.parseObject(result0);

//            log.info("[批量下单结果] result->{},domain->{},currencyCode->{},交易数据->{}",
//                    result.toString(), domainUrl, currencyCode, tradeAddVoList);
//            return result;
        } catch (Exception e) {//超时或网络异常
            log.error("[下单失败] domain->{},currencyCode->{},交易数据->{},e->{}", domainUrl, tradeAddVoList, currencyCode,e);
//            JSONObject result = new JSONObject();
//            result.put("error", "-1");
//            return result;
        }
    }



    public void batchChebudan(String currency, TradeCheBuVo tradeCheBuVo) {
        String ts = String.valueOf(System.currentTimeMillis());
        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currency);//btc_usdt
        String sign = this.getSignature(params,this.secret);

        String url = String.format("%s%s?accessKey=%s&timestamp=%s&market=%s&sign=%s",this.domainUrl,"api/v2/user/tradeChebuBatch", this.accessKey, ts, currency, sign );


        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

            String content = JSONObject.toJSONString(tradeCheBuVo );

            HttpEntity<String> request = new HttpEntity<>(content, headers);

//            log.info("[开始下单] domain->{},买卖类型type->{}, price->{},amount->{},currencyCode->{}", domainUrl, type, price, count, currencyCode);
            String result0 = restTemplate.postForEntity(url, request, String.class).getBody();

            JSONObject result = JSONObject.parseObject(result0);

//            log.info("[批量下单结果] result->{},domain->{},currencyCode->{},交易数据->{}",
//                    result.toString(), domainUrl, currencyCode, tradeAddVoList);
//            return result;
        } catch (Exception e) {//超时或网络异常
            log.error("[撤补单失败] domain->{},currencyCode->{},交易数据->{},e->{}", domainUrl, currency, tradeCheBuVo,e);
//            JSONObject result = new JSONObject();
//            result.put("error", "-1");
//            return result;
        }

    }

    public void cancelOrderByPrice(String currencyCode, String price, String type){
        String url = this.domainUrl + "api/v1/user/cancelOrderByPrice";
        String ts = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("accessKey", this.accessKey);
        paramMap.add("timestamp", ts);
        paramMap.add("market", currencyCode);//BTC/USDT
        paramMap.add("price", price);
        paramMap.add("type", type);//1买0卖

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currencyCode);//btc_usdt
        params.put("price", price);
        params.put("type", type);//1买2卖
        String sign = this.getSignature(params,this.secret);
        paramMap.add("sign",sign);
        try {
//            log.info("[开始撤单] currency->{}, price->{}, type->{}",currencyCode,price,type);
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
//            JSONObject result = JSONObject.parseObject(result0);
//            log.info("[撤单结果] currency->{}, price->{}, type->{}，result->{}",currencyCode,price,type,result.toString());
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            log.info("[撤单异常] url->{},currency->{}, price->{}, type->{}",url,currencyCode,price,type);
            e.printStackTrace();
        }
    }




    public void cancelRobotOrder(String currencyCode, String curPrice) {
        String url = this.domainUrl + "api/v1/user/cancelRobotOrder";
        String ts = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("accessKey", this.accessKey);
        paramMap.add("timestamp", ts);
        paramMap.add("market", currencyCode);//BTC/USDT
        paramMap.add("curPrice", curPrice);

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currencyCode);//btc_usdt
        params.put("curPrice", curPrice);
        String sign = this.getSignature(params,this.secret);
        paramMap.add("sign",sign);
        try {
//            log.info("[开始撤单] currency->{}, price->{}, type->{}",currencyCode,price,type);
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
//            JSONObject result = JSONObject.parseObject(result0);
//            log.info("[撤单结果] currency->{}, price->{}, type->{}，result->{}",currencyCode,price,type,result.toString());
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            log.info("[撤单异常] url->{},currency->{}, price->{}",url,currencyCode,curPrice);
            e.printStackTrace();
        }
    }

    public void cancelOrderByPriceList(String currencyCode, String priceList, String type){
        String url = this.domainUrl + "api/v1/user/cancelOrderByPriceList";
        String ts = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("accessKey", this.accessKey);
        paramMap.add("timestamp", ts);
        paramMap.add("market", currencyCode);//BTC/USDT
        paramMap.add("priceList", priceList);
        paramMap.add("type", type);//1买0卖

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currencyCode);//btc_usdt
        params.put("priceList", priceList);
        params.put("type", type);//1买2卖
        String sign = this.getSignature(params,this.secret);
        paramMap.add("sign",sign);
        try {
//            log.info("[开始撤单] currency->{}, price->{}, type->{}",currencyCode,price,type);
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            JSONObject result = JSONObject.parseObject(result0);
//            log.info("[撤单结果] currency->{}, price->{}, type->{}，result->{}",currencyCode,priceList,type,result.toString());
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            log.info("[撤单异常] url->{},currency->{}, price->{}, type->{}",url,currencyCode,priceList,type);
            e.printStackTrace();
        }
    }

    public BigDecimal getLatestPriceSix(String currencyCode){
        String url = this.domainUrl + "api/v1/ticker/getTicker";
        String ts = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("accessKey", this.accessKey);
        paramMap.add("timestamp", ts);
        paramMap.add("market", currencyCode);

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currencyCode);
        String sign = this.getSignature(params,this.secret);
        paramMap.add("sign",sign);
        try {
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            JSONObject result = JSONObject.parseObject(result0);
            BigDecimal last = result.getJSONObject("data").getJSONObject(currencyCode).getBigDecimal("last");
            return last;
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            return null;
        }
    }

    public JSONObject getDepthSix(String currencyCode){
        String url = this.domainUrl + "api/v1/ticker/getDepth";
        String ts = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("accessKey", this.accessKey);
        paramMap.add("timestamp", ts);
        paramMap.add("market", currencyCode);

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        params.put("market", currencyCode);
        String sign = this.getSignature(params,this.secret);
        paramMap.add("sign",sign);
        try {
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            JSONObject result = JSONObject.parseObject(result0);
            JSONObject depth = result.getJSONObject("data").getJSONObject(currencyCode);
            return depth;
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getPricision(String currency){
        JSONObject rst = null;
        String url = this.domainUrl + "api/v1/ticker/getAllCoin";
        String ts = String.valueOf(System.currentTimeMillis());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();//参数按字典序排列
        paramMap.add("accessKey", this.accessKey);
        paramMap.add("timestamp", ts);

        Map<String, String> params = new HashMap<>();
        params.put("accessKey", this.accessKey);
        params.put("timestamp", ts);
        String sign = this.getSignature(params,this.secret);
        paramMap.add("sign",sign);

        try {
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            JSONObject result = JSONObject.parseObject(result0);
            JSONObject job = result.getJSONObject("data").getJSONObject(currency);
            rst = new JSONObject();
            rst.put("minTradeLimit",job.getBigDecimal("minTradeLimit"));
            rst.put("amountPrecision",job.getIntValue("amountPrecision"));
            rst.put("pricePrecision",job.getIntValue("pricePrecision"));
            return rst;
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            e.printStackTrace();
            return null;
        }
    }

    private String getSignature(Map<String, String> params, String secret) {
        String hash = "";
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder baseStr = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            baseStr.append(param.getKey()).append("=").append(param.getValue());
        }
        baseStr.append(secret);
//        System.out.println("baseStr:" + baseStr);
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(baseStr.toString().getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    private String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    public static void main(String[] args) {
//        RobotSix rs = new RobotSix("http://192.168.43.106:8060/",
//                "15f542a0295841ca24a71bfdc132258a","4f3a7684-4c40-4139-9375-0a6eaca18f60");
        RobotSix rs = new RobotSix("http://api.bitcoin360.io/",
                "cfca82b4bf9bd1078940ff0f572865dc","d59cd340-8506-471c-94e0-41910c6312a6");
        JSONObject pri = rs.getPricision("btc_usdt");
    }
}
