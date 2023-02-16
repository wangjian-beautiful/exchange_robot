package com.bjs.hedge.framework.platform.binance;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.binance.connector.client.impl.SpotClientImpl;
import com.bjs.hedge.common.enums.BinanceOrderRespType;
import com.bjs.hedge.common.enums.HedgePlaceOrderTypes;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.common.enums.TimeInForceStatus;
import com.bjs.hedge.config.binance.BinanceConfig;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.framework.platform.TradeService;
import com.bjs.hedge.util.MathUtils;
import com.bjs.hedge.util.PropertiesUtil;
import com.bjs.hedge.vo.BinanceOrderRespVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.function.Function;

@Component
@Slf4j
public class BinanceTradeService extends TradeService {

    @Autowired
    private BinanceConfig binanceConfig;

    public void setBinanceConfig(BinanceConfig binanceConfig) {
        this.binanceConfig = binanceConfig;
    }

    public SpotClientImpl getSpotClient() {
        return new SpotClientImpl(binanceConfig.getApiKey(), binanceConfig.getSecretKey(), binanceConfig.getBaseUrl());
    }
    public JSON execute(Function<LinkedHashMap<String, Object>, String> func, LinkedHashMap<String, Object> parameters) {
        return (JSON) JSON.parse(func.apply(parameters));
    }

    /**
     * 现货下单
     *
     * @param exHedgeOrder
     * @return
     */
    public JSON newOrder(ExHedgeOrder exHedgeOrder, HedgeRobotConfig hedgeRobotConfig) {
        HedgePlaceOrderTypes type = HedgePlaceOrderTypes.getOrderType(hedgeRobotConfig.getBinanceOrderType());
        TimeInForceStatus timeInForce = TimeInForceStatus.getTimeInForceStatus(hedgeRobotConfig.getTimeInForceStatus());
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", exHedgeOrder.getHedgeOrderSymbol().toUpperCase());
        parameters.put("side", exHedgeOrder.getHedgeSide());
        parameters.put("type", type.name());
        if (exHedgeOrder.getHedgeVolume() != null){
            parameters.put("quantity", exHedgeOrder.getHedgeVolume().toPlainString());
        }
        if(exHedgeOrder.getHedgePrice() != null){
            parameters.put("price", exHedgeOrder.getHedgePrice().toPlainString());
        }

        switch (type){
            case LIMIT:
            case STOP_LOSS_LIMIT:
            case TAKE_PROFIT_LIMIT:
                parameters.put("timeInForce", timeInForce.name());
                break;
            case MARKET:
                parameters.remove("price");
            default:break;
        }
        parameters.put("newOrderRespType", BinanceOrderRespType.FULL);
        return execute(p -> getSpotClient().createTrade().newOrder(p), parameters);
    }

    /**
     * 获取历史成交订单
     *
     * @param exHedgeOrder
     * @return
     */
    public JSON myTrades(ExHedgeOrder exHedgeOrder) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        if (exHedgeOrder != null && StringUtils.isNotBlank(exHedgeOrder.getHedgeOrderSymbol())) {
            parameters.put("symbol", exHedgeOrder.getHedgeOrderSymbol().toUpperCase());
        }
        if (StringUtils.isNotBlank(exHedgeOrder.getHedgeOrderId())){
            parameters.put("orderId",exHedgeOrder.getHedgeOrderId());
        }
        return execute(p -> getSpotClient().createTrade().myTrades(p), parameters);
    }

    /**
     * 查询当前订单
     *
     * @param timestamp
     * @return
     */
    public JSON getOrder(ExHedgeOrder order, Long timestamp) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        if (timestamp == null || timestamp.equals(0)) {
            timestamp = System.currentTimeMillis() - 60 * 1000;
        }
        parameters.put("timestamp", timestamp);
        parameters.put("symbol", order.getHedgeOrderSymbol().toUpperCase());
        if (order.getHedgeOrderId() != null) {
            parameters.put("orderId", order.getHedgeOrderId());
        }
        return execute(p -> getSpotClient().createTrade().getOrder(p), parameters);
    }

    /**
     * 查询所有订单
     *
     * @param timestamp
     * @return
     */
    public JSON getOrders(ExHedgeOrder order, Long timestamp) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        if (timestamp == null || timestamp.equals(0)) {
            timestamp = System.currentTimeMillis() - 60 * 1000;
        }
        parameters.put("timestamp", timestamp);
        parameters.put("symbol", order.getHedgeOrderSymbol());
        return execute(p -> getSpotClient().createTrade().getOrders(p), parameters);
    }

    /**
     * 获取账号信息
     *
     * @return
     */
    public JSON account() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        return execute(p -> getSpotClient().createTrade().account(p), parameters);
    }



    public static void main(String[] args) throws Exception {
//        symbol=BTCUSDT&side=SELL&type=LIMIT&timeInForce=GTC&quantity=1&price=0.2&recvWindow=5000
        BinanceTradeService binanceTradeService = new BinanceTradeService();
        BinanceConfig binanceConfig = PropertiesUtil.getInstance().getBean("binance", BinanceConfig.class);
        binanceTradeService.setBinanceConfig(binanceConfig);

        ExHedgeOrder order = new ExHedgeOrder();
        order.setHedgeOrderSymbol("BTCUSDT");
        order.setHedgeSide(HedgeSideTypes.SELL.name());
        order.setHedgeVolume((BigDecimal.valueOf(0.00295)));
        order.setHedgePrice(BigDecimal.valueOf(16900));
        HedgeRobotConfig config = new HedgeRobotConfig();
        config.setSymbol("BTCUSDT");
        config.setTimeInForceStatus(TimeInForceStatus.GTC.name());
        config.setBinanceOrderType(HedgePlaceOrderTypes.LIMIT.name());
        JSON resp = binanceTradeService.newOrder(order, config);
        BinanceOrderRespVO binanceOrderRespVO = JSON.parseObject(resp.toString(), BinanceOrderRespVO.class);

//        String resp = binanceTradeService.getMyTrades(order);
        System.out.println(JSON.toJSONString(binanceOrderRespVO, true));



    }
}
