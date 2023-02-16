package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class BianceUtil {

    private BianceUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ZbApiUtil.class);

    private final static String tickerUrl = "https://api.binance.com/api/v3/ticker/price?symbol=";

    public static BigDecimal getNewestPrice(String currency) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        String response = HttpClientUtil.sendGetRequest(tickerUrl+currency, false);
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson == null) {
            return null;
        }
        String symbol = resultJson.getString("symbol");
        if (symbol.equalsIgnoreCase(currency)) {
            bigDecimal = resultJson.getBigDecimal("price");
        }
        return bigDecimal;

    }


    public static void main(String[] args) {
//        System.out.println(BigDecimal.valueOf(Math.random() * 10 + 39000).setScale(2, RoundingMode.CEILING));
//        System.out.println(HttpClientUtil.sendGetRequest("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT", false));
        System.out.println(HttpClientUtil.sendGetRequest("http://www.facebook.com", false));
    }

}
