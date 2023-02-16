package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.model.TickerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class ZbApiUtil {
    private ZbApiUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(ZbApiUtil.class);

    public static String API_DOMAIN = "http://api.zb.cn";

    // 获取交易对符号
    public static String getCurrency(String leftCurrency, String rightCurrency) {
        return leftCurrency.toLowerCase() + "_" + rightCurrency.toLowerCase();
    }
    //  ~~~~~~~~~~~~~~~~ 转换  ~~~~~~~~~~~~~~~~~~~~~~~//

    /**
     * 获取最新深度
     *
     * @param currency
     * @return
     */
    public static JSONArray getNewDepth(String currency, boolean isBuy) {
        String ss[] = currency.split("_");
        String url = ZbApiUtil.API_DOMAIN + "/data/v1/depth?market=" + getCurrency(ss[0], ss[1]) + "&size=1";
        String result = HttpClientUtil.sendGetRequest(url, false);
        JSONObject jsonData = JSONObject.parseObject(result);
        if (isBuy) {
            return jsonData.getJSONArray("bids");
        } else {
            return jsonData.getJSONArray("asks");
        }
    }

    /**
     * 获取当前最新价
     *
     * @param currency
     * @return
     */
    public static BigDecimal getNewestPrice(String currency) {
        String ss[] = currency.split("_");
        TickerBean tmTicker = getTicker(ss[0], ss[1]);
        return new BigDecimal(tmTicker.getLast());
    }

    /**
     * 获取行情数据
     *
     * @param leftCoin
     * @param rightCoin
     * @return
     */
    public static TickerBean getTicker(String leftCoin, String rightCoin) {
        logger.debug("【左币】: " + leftCoin + "【右币】: " + rightCoin);
        TickerBean tmTicker = null;
        try {
            if (rightCoin.equalsIgnoreCase("cny") || rightCoin.equalsIgnoreCase("cnyt")) {
                rightCoin = "QC";
            }
            String url = ZbApiUtil.API_DOMAIN + "/data/v1/ticker?market=" + getCurrency(leftCoin, rightCoin);
            String result = HttpClientUtil.sendGetRequest(url, false);
            JSONObject jsonData = JSONObject.parseObject(result);
            tmTicker = ZbApiUtil.convertTickerData(jsonData);
        } catch (Exception ex) {
        }
        return tmTicker;
    }

    /**
     * 转化行情数据
     *
     * @return
     */
    public static TickerBean convertTickerData(JSONObject resultData) {
        if (resultData == null) {
            return null;
        }
        JSONObject tickerData = resultData.getJSONObject("ticker");
        logger.debug("【tickerData】: " + tickerData);
        if (tickerData == null) {
            return null;
        }
        TickerBean tmTicker = new TickerBean();
        try {
            String last = tickerData.getString("last");
            String low = tickerData.getString("low");
            String high = tickerData.getString("high");
            String vol = tickerData.getString("vol");
            String buy = tickerData.getString("buy");
            String sell = tickerData.getString("sell");
            tmTicker.setBuy(buy);
            tmTicker.setSell(sell);
            tmTicker.setHigh(high);
            tmTicker.setLow(low);
            tmTicker.setLast(last);
            tmTicker.setVol(vol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmTicker;
    }

    public static void main(String[] args) {
        System.out.println(getTicker("usdt", "qc"));
    }
}
