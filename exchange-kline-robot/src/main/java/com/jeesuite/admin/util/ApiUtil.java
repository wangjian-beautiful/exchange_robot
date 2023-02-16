package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * api 接口
 *
 * @author kew
 * @create 2018-09-01 下午12:12
 **/

public interface ApiUtil {
    String createOrder(String currencyCode, boolean orderType, String price, String count);

    JSONObject getOrder(String orderNo, String currencyCode);

    void cancel(String orderNo, String currencyCode);

    JSONArray getTrades(String currency);

    JSONObject getDepth(String currency);

    JSONArray getAllExchangeType();

    JSONObject getBalance();

    JSONObject getOrders(String currencyCode);
    JSONObject getOrders(String currency, Integer pageSize);
    JSONObject getTicker(String currency);
}
