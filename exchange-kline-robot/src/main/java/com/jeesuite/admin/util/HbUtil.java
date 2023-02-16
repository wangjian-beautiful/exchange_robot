package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * Created by wenlu on 18/10/12.
 */
@Slf4j
@Service
public class HbUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    private final static String tickerUrl = "https://api.huobi.br.com/market/tickers";

    // 获取聚合行情(TickerBean)
    private final static String mergedUrl = "https://api.huobi.br.com/market/detail/merged";

    public static BigDecimal getNewestPrice(String currency) {
        BigDecimal bigDecimal = BigDecimal.ZERO;
        String response = HttpClientUtil.sendGetRequest(tickerUrl, false);
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson == null) {
            return null;
        }
        String status = resultJson.getString("status");
        if (StringUtils.isEmpty(status) || !status.equals("ok")) {
            return null;
        }
        JSONArray jsonArray = resultJson.getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String symbol = jsonObject.getString("symbol");
            if (symbol.equalsIgnoreCase(currency)) {
                bigDecimal = jsonObject.getBigDecimal("close");
                break;
            }
        }

        return bigDecimal;
    }

    /**
     * 获取交易所的买一和卖一价格
     *
     * @param currency
     * @param isBuy
     * @return
     */
    public static JSONArray getNewestPrice(String currency, boolean isBuy) {
        log.info("[查询汇率] 火币 currency->{}, isBuy->{}", currency, isBuy);
        String response = HttpClientUtil.sendGetRequest(mergedUrl + "?symbol=" + currency, false);
        JSONObject resultJson = JSONObject.parseObject(response);
        if (resultJson == null) {
            return null;
        }
        String status = resultJson.getString("status");
        if (StringUtils.isEmpty(status) || !status.equals("ok")) {
            return null;
        }
        if (isBuy) {
            return resultJson.getJSONObject("tick").getJSONArray("bid");
        } else {
            return resultJson.getJSONObject("tick").getJSONArray("ask");
        }
    }

    public static void main(String[] args) {
        //System.out.println(HbUtil.getNewestPrice("ethbtc"));
        JSONArray jsonArray = HbUtil.getNewestPrice("btcusdt", false);
        System.out.println(jsonArray.getBigDecimal(0));
        System.out.println(jsonArray.getBigDecimal(1));
        //System.out.println(HbUtil.getNewestPrice("ethusdt",false).getBigDecimal(0));
        //System.out.println(HbUtil.getNewestPrice("ethusdt",false).getBigDecimal(1));
    }
}
