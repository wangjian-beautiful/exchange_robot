package com.jeesuite.admin.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.dao.entity.ExOrder;
import com.jeesuite.admin.dao.entity.TradeSchedule;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.vo.TradeAddVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author Doctor
 * @Date 11:15 2022/8/4
 * @Description 币金所行情接口
 * @return
 **/
@Slf4j
public class RobotBjs {

    private RestTemplate restTemplate = new RestTemplate();

    private String domainUrl;

    private String accessKey;

    private String secret;

    public RobotBjs(String domainUrl, String accessKey, String secret) {
        this.domainUrl = domainUrl;
        this.accessKey = accessKey;
        this.secret = secret;
    }


    /**
     * @Author Doctor
     * @Date 10:53 2022/8/5
     * @Description 自成交订单
     * @return
     **/
    public void selfTrade(String currencyCode, String side, String price, String volume){
        String formatCurrency = formatCurrency(currencyCode);
        String url = this.domainUrl + "open/api/self_trade";

//        side = "1".equals(side)?"BUY":"SELL";//1买 2 卖

        Integer time = curTime();
        Map<String, String> params = new HashMap<>();
        params.put("api_key", this.accessKey);
        params.put("side", side);
        params.put("type", "1");//限价单
        params.put("volume", volume);
        params.put("price", price);
        params.put("symbol", formatCurrency);
        params.put("time", String.valueOf(time));
        String sign = this.sign(params);
        params.put("sign", sign );
        MultiValueMap paramMap = parseMap(params );
        try {
//            log.info("[开始下单] domain->{},买卖类型type->{}, price->{},amount->{},currencyCode->{}", domainUrl, type, price, count, currencyCode);
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
//            JSONObject result = JSONObject.parseObject(result0);
            log.info("[自成交下单结果] result->{},domain->{},买卖类型side->{}, price->{},volume->{},currencyCode->{}",
                    result0, domainUrl, side, price, volume, currencyCode);
//            return result;
        } catch (Exception e) {//超时或网络异常
            log.error("[自成交下单失败] domain->{},买卖类型side:{} price->{},volume->{},currencyCode->{},e->{}", domainUrl, side, price, volume, currencyCode,e);
//            JSONObject result = new JSONObject();
//            result.put("error", "-1");
//            return result;
        }
    }

    /**
     * @Description 取消重启订单
     * @return
     **/
    public void cancelRestartOrder(String currencyCode){
        String formatCurrency = formatCurrency(currencyCode);
        String url = this.domainUrl + "open/api/cancel_restart_order";
        Map<String, String> params = new HashMap<>();
        params.put("symbol", formatCurrency);
        params.put("api_key", this.accessKey);
        MultiValueMap paramMap = parseMap(params );
        try {
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            log.info("[取消重启订单结果]  symbol->{}", formatCurrency);
        } catch (Exception e) {//超时或网络异常
            log.error("[取消重启订单失败] symbol->{},e->{}", formatCurrency,e);
        }
    }


    /**
     * @Author Doctor
     * @Date 10:53 2022/8/5
     * @Description 自成交订单
     * @return
     **/
    public void getAccount(){
        String url = this.domainUrl + "open/api/user/account";

//        side = "1".equals(side)?"BUY":"SELL";//1买 2 卖

        Integer time = curTime();
        Map<String, String> params = new HashMap<>();
        params.put("api_key", this.accessKey);
        params.put("time", String.valueOf(time));
        String sign = this.sign(params);
        params.put("sign", sign );
        MultiValueMap paramMap = parseMap(params );
        try {
//            log.info("[开始下单] domain->{},买卖类型type->{}, price->{},amount->{},currencyCode->{}", domainUrl, type, price, count, currencyCode);
            String result0 = restTemplate.getForObject(url,String.class, paramMap);
//            JSONObject result = JSONObject.parseObject(result0);
            log.info("[getAccount] result->{}",
                    result0);
//            return result;
        } catch (Exception e) {//超时或网络异常
//            log.error("[自成交下单失败] domain->{},买卖类型Ωside:{} price->{},volume->{},currencyCode->{},e->{}", domainUrl, side, price, volume, currencyCode,e);
//            JSONObject result = new JSONObject();
//            result.put("error", "-1");
//            return result;
        }
    }

    public void getAllTrade(String symbol,Integer pageSize, Integer lastId){
        String url = this.domainUrl + "open/api/all_trade?api_key={api_key}&time={time}&symbol={symbol}&sort={sort}&pageSize={pageSize}&id={id}&sign={sign}";

        Integer time = curTime();
        Map<String, String> params = new HashMap<>();
        params.put("api_key", this.accessKey);
        params.put("time", String.valueOf(time));
        params.put("symbol", symbol);
        params.put("sort", "0");
        params.put("pageSize", String.valueOf(pageSize));
        params.put("id", String.valueOf(lastId));
        String sign = this.sign(params);
        params.put("sign", sign );
//        MultiValueMap paramMap = parseMap(params );
        try {
//            log.info("[开始下单] domain->{},买卖类型type->{}, price->{},amount->{},currencyCode->{}", domainUrl, type, price, count, currencyCode);
            String result0 = restTemplate.getForObject(url,String.class, params);
//            JSONObject result = JSONObject.parseObject(result0);
            log.info("[getAllTrade] result->{}",
                    result0);
//            return result;
        } catch (Exception e) {//超时或网络异常
//            log.error("[自成交下单失败] domain->{},买卖类型Ωside:{} price->{},volume->{},currencyCode->{},e->{}", domainUrl, side, price, volume, currencyCode,e);
//            JSONObject result = new JSONObject();
//            result.put("error", "-1");
//            return result;
        }
    }

    private Integer curTime() {
        return Math.toIntExact(System.currentTimeMillis() / 1000);
    }

    /**
     * @Author Doctor
     * @Date 15:42 2022/8/5
     * @Description 一次性批量发单，同时撤回指定订单
     * @param currencyCode 币队符号
     * @param tradeAddVoList 批量发单对象
     * @param cancelIds 撤回的订单id数组
     * @return 返回本次批量下单的订单id数组
     **/
    public JSONArray replaceOrderV2(String currencyCode, List<TradeAddVo> tradeAddVoList, JSONArray cancelIds){
        Integer time = curTime();
        JSONArray addArr = transferAddArr(currencyCode, tradeAddVoList );
        String formatCurrency = currencyCode.replace("_", "").toUpperCase();

        if(cancelIds == null ){
            cancelIds = new JSONArray();
        }

        Map<String, String> params = new HashMap<>();
        params.put("api_key", this.accessKey);
        params.put("symbol", formatCurrency);
        params.put("time", String.valueOf(time));
        params.put("mass_cancel", JSON.toJSONString(cancelIds) );
        params.put("mass_place", JSON.toJSONString(addArr) );
        String sign = this.sign(params);
        params.put("sign", sign );
        MultiValueMap paramMap = parseMap(params );

        String url = this.domainUrl + "open/api/mass_replaceV2";
        try{
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            log.info("[批量下单撤单结果] result->{},domain->{},tradeAddVoList:{} cancelIds->{},currencyCode->{}",
                    result0, domainUrl, tradeAddVoList, cancelIds, currencyCode );
            JSONObject result = JSONObject.parseObject(result0);
            if("0".equals(result.getString("code") ) ){
                JSONArray placeArr = result.getJSONObject("data").getJSONArray("mass_place");
                if(CollUtil.isEmpty(placeArr) ){
                    return new JSONArray();
                }
                Object placeRsObj = placeArr.get(0);
                JSONObject placeRs = (JSONObject) placeRsObj;
                return placeRs.getJSONArray("order_id");
            }else {
                //本次失败，把这次取消的id集合返回，用于下次取消
                return cancelIds;
            }
        }catch (Exception e){
            log.error("[批量下单撤单失败] domain->{},tradeAddVoList:{} cancelIds->{},currencyCode->{},e->{}", domainUrl, tradeAddVoList, cancelIds, currencyCode,e);
            //本次失败，把这次取消的id集合返回，用于下次取消
            return cancelIds;
        }
    }


    /**
     * @Author Doctor
     * @Date 15:42 2022/8/5
     * @Description 一次性批量发单，同时撤回指定订单
     * @param tradeSchedule 机器人交易配置表
     * @param tradeAddVoList 批量发单对象
     * @param cancelIds 撤回的订单id数组
     * @return 返回本次批量下单的订单id数组
     **/
    public JSONArray replaceOrderV3(TradeScheduleEntity tradeSchedule, List<TradeAddVo> tradeAddVoList, JSONArray cancelIds){
        Integer time = curTime();
        String currencyCode = tradeSchedule.getCurrencyAlias();
        JSONArray addArr = transferAddArr(currencyCode, tradeAddVoList );
        String formatCurrency = currencyCode.replace("_", "").toUpperCase();

        if(cancelIds == null ){
            cancelIds = new JSONArray();
        }

        Map<String, String> params = new HashMap<>();
        params.put("api_key", this.accessKey);
        params.put("symbol", formatCurrency);
        params.put("time", String.valueOf(time));
        params.put("mass_cancel", JSON.toJSONString(cancelIds) );
        params.put("mass_place", JSON.toJSONString(addArr) );
        String sign = this.sign(params);
        params.put("sign", sign );
        MultiValueMap paramMap = parseMap(params );

        String url = this.domainUrl + "open/api/mass_replaceV3";
        try{
            String result0 = restTemplate.postForObject(url, paramMap, String.class);
            log.info("[批量下单撤单结果] result->{},domain->{},tradeAddVoList:{} cancelIds->{},currencyCode->{}",
                    result0, domainUrl, tradeAddVoList, cancelIds, currencyCode );
            JSONObject result = JSONObject.parseObject(result0);
            if("0".equals(result.getString("code") ) ){

                if (tradeSchedule.getLastOrderIds() != null) {
                    JSONArray removeOrder = new JSONArray();
                    Iterator<Object> o = tradeSchedule.getLastOrderIds().iterator();
                    while (o.hasNext()) {
                        Object next =  o.next();
                        ExOrder massCancelOrder = JSONObject.parseObject(next.toString(),ExOrder.class);
                        String orderId = String.valueOf(massCancelOrder.getId());
                        cancelIds.forEach(itemCancel -> {
                            ExOrder finalCancelIdsOrder = JSONObject.parseObject(itemCancel.toString(),ExOrder.class);
                            if(orderId.equals(String.valueOf(finalCancelIdsOrder.getId()))){
                                removeOrder.add(next);
                            }
                        });
                    }
                    tradeSchedule.getLastOrderIds().fluentRemoveAll(removeOrder);
                }
                JSONArray placeArr = result.getJSONObject("data").getJSONArray("mass_place");
                if(CollUtil.isEmpty(placeArr) ){
                    return new JSONArray();
                }
                Object placeRsObj = placeArr.get(0);
                JSONObject placeRs = (JSONObject) placeRsObj;
                return placeRs.getJSONArray("order_id");
            }else {
                //本次失败，把这次取消的id集合返回，用于下次取消
                return cancelIds;
            }
        }catch (Exception e){
            log.error("[批量下单撤单失败] domain->{},tradeAddVoList:{} cancelIds->{},currencyCode->{},e->{}", domainUrl, tradeAddVoList, cancelIds, currencyCode,e);
            //本次失败，把这次取消的id集合返回，用于下次取消
            return cancelIds;
        }
    }

    private JSONArray transferAddArr(String currencyCode, List<TradeAddVo> tradeAddVoList) {
        String formatCurrency = currencyCode.replace("_", "-").toUpperCase();
        JSONArray addArr = new JSONArray();
        for (TradeAddVo tradeAddVo : tradeAddVoList) {
            JSONObject item = new JSONObject();
            item.put("volume", tradeAddVo.getNum() );
            item.put("symbol", formatCurrency );
            item.put("side", tradeAddVo.getType() == 1?"BUY":"SELL" );
            item.put("price", tradeAddVo.getPrice() );
            item.put("type", 1 );

            addArr.add(item);
        }

        return addArr;
    }

    public BigDecimal getLatestPriceSix(String currencyCode){
        String formatCurrency = formatCurrency(currencyCode);

        String url = this.domainUrl + "open/api/get_allticker";

        try {
            String result0 = restTemplate.getForObject(url, String.class);
            JSONObject result = JSONObject.parseObject(result0);
            JSONArray tickers = result.getJSONObject("data").getJSONArray("ticker");
            BigDecimal last = BigDecimal.ZERO;
            for (Object tickerObj : tickers) {
                JSONObject ticker = (JSONObject) tickerObj;
                if(formatCurrency.equals(ticker.getString("symbol")) ){
                    if(ticker.containsKey("last")){
                        last = ticker.getBigDecimal("last");
                    }
                    break;
                }
            }
            return last;
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            return null;
        }
    }

    private String formatCurrency(String currencyCode) {
        return currencyCode.replace("_", "");
    }

    public JSONObject getDepthSix(String currencyCode){
        String formatCurrency = currencyCode.replace("_", "");
        String url = String.format("%sopen/api/market_dept?symbol=%s&type=step0", this.domainUrl, formatCurrency);

        try {
            String result0 = restTemplate.getForObject(url, String.class);
            JSONObject result = JSONObject.parseObject(result0);
            JSONObject depth = new JSONObject();

            if("0".equals(result.getString("code") ) ){
                JSONObject rawDepth = result.getJSONObject("data").getJSONObject("tick");
                depth.put("buy", rawDepth.getJSONArray("bids"));
                depth.put("sell", rawDepth.getJSONArray("asks"));
            }else {//没有盘口
                depth.put("buy", new JSONArray() );
                depth.put("sell", new JSONArray() );
            }
            return depth;
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public JSONObject getPricision(String currency){
        JSONObject rst = null;
        String url = this.domainUrl + "open/api/common/symbols";

        String formatCurrency = currency.replace("_", "");
        try {
            String result0 = restTemplate.getForObject(url, String.class);
            JSONObject result = JSONObject.parseObject(result0);
            JSONArray data = result.getJSONArray("data");
            JSONObject currencyItem = null;
            for (Object itemObj : data) {
                JSONObject item = (JSONObject) itemObj;
                if(formatCurrency.equals(item.getString("symbol") ) ) {
                    currencyItem = item;
                    break;
                }
            }
            if(currencyItem == null ) return null;

            rst = new JSONObject();
            rst.put("minTradeLimit",currencyItem.getBigDecimal("limit_volume_min"));
            rst.put("amountPrecision",currencyItem.getIntValue("amount_precision"));
            rst.put("pricePrecision",currencyItem.getIntValue("price_precision"));
            return rst;
        } catch (Exception e) {//超时或网络异常,error!=0返回错误
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private MultiValueMap<String, String> parseMap(Map<String, String> params){
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        params.forEach((k,v)->{
            paramMap.add(k,v);
        });
        return paramMap;
    }

    /**
     * 签名
     * @param params
     * @return
     */
    private String sign(Map<String,String> params){
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();

        // 遍历排序后的字典，将所有参数按"keyvalue"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            if(param.getKey().equals("sign")){//去掉签名字段
                continue;
            }

            if(!StrUtil.isBlank(param.getValue())) {
                basestring.append(param.getKey());
                basestring.append(param.getValue().toString());
            }
        }
        basestring.append(this.secret);

        // 使用MD5对待签名串求签
        String curSign = MD5Util.getMD5(basestring.toString());

        return curSign;
    }

    public static void main(String[] args) {
//        RobotBjs robotBjs = new RobotBjs("https://openapi.bjsuo1.com/","c58cda2615bd8bd47ca011f4a586ce18","6ae311c43babcfffe9f5e57c4b9c2e94");
//        RobotBjs robotBjs = new RobotBjs("http://localhost:9667/","c58cda2615bd8bd47ca011f4a586ce18","6ae311c43babcfffe9f5e57c4b9c2e94");
//        RobotBjs robotBjs = new RobotBjs("https://openapi.gcex.bio/","c58cda2615bd8bd47ca011f4a586ce18","6ae311c43babcfffe9f5e57c4b9c2e94");
        RobotBjs robotBjs = new RobotBjs("https://openapi.bjs93.com/","c58cda2615bd8bd47ca011f4a586ce18","6ae311c43babcfffe9f5e57c4b9c2e94");

//        JSONObject bsc_usdt = robotBjs.getPricision("bsc_usdt");
//        System.out.println(bsc_usdt );
//
//
//        JSONObject btc_usdt_depth = robotBjs.getDepthSix("btc_usdt");
//        System.out.println(btc_usdt_depth );
//
//        JSONObject bnb_usdt_depth = robotBjs.getDepthSix("bnb_usdt");
//        System.out.println(bnb_usdt_depth );
//
//        System.out.println(robotBjs.getLatestPriceSix("btc_usdt") );
//        System.out.println(robotBjs.getLatestPriceSix("adabep20_usdt") );

        robotBjs.selfTrade("bnb_usdt", "SELL", "270", "0.01");


//        TradeAddVo order0 = TradeAddVo.builder().price("2300.1").num("0.131").type(0).build();
//        TradeAddVo order1 = TradeAddVo.builder().price("2300.2").num("0.132").type(0).build();
//        TradeAddVo order2 = TradeAddVo.builder().price("2299.9").num("0.129").type(1).build();
//        TradeAddVo order3 = TradeAddVo.builder().price("2299.8").num("0.128").type(1).build();
//        List<TradeAddVo> list = new ArrayList<TradeAddVo>(){{add(order0);add(order1);add(order2);add(order3);}};
//
////        List<TradeAddVo> list = new ArrayList<>();
//        JSONArray cancelIds = JSON.parseArray("[26169211,26169212,26169213,26169214]");
//        System.out.println(JSON.toJSONString(robotBjs.replaceOrderV2("btc_usdt", list, cancelIds)) );

//        robotBjs.getAllTrade("BSCUSDT",3,678582);
    }
}
