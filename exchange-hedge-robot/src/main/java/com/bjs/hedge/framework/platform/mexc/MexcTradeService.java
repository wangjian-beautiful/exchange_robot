package com.bjs.hedge.framework.platform.mexc;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjs.hedge.common.enums.HedgePlaceOrderTypes;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.common.enums.TimeInForceStatus;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.framework.platform.TradeService;
import com.bjs.hedge.framework.platform.mexc.exapple.common.JsonUtil;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.MexcApiV3AuthExample;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.Account;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.MyTrades;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.Order;
import com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo.OrderPlaceResp;
import com.bjs.hedge.util.MathUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.MexcApiV3AuthExample.*;

@Component
@Slf4j
public class MexcTradeService extends TradeService {


    /**
     * 现货下单
     *
     * @param exHedgeOrder
     * @return
     */
    public JSON newOrder(ExHedgeOrder exHedgeOrder, HedgeRobotConfig hedgeRobotConfig) {
        HedgePlaceOrderTypes type = HedgePlaceOrderTypes.getOrderType(hedgeRobotConfig.getBinanceOrderType());
        TimeInForceStatus timeInForce = TimeInForceStatus.getTimeInForceStatus(hedgeRobotConfig.getTimeInForceStatus());
        Map<String, String> params = new HashMap<>();
        //symbol=AEUSDT&side=SELL&type=LIMIT&timeInForce=GTC&quantity=1&price=20
        params.put("symbol", exHedgeOrder.getHedgeOrderSymbol().toUpperCase());
        params.put("side",exHedgeOrder.getHedgeSide());
        if (timeInForce.getCode() == null) {
            params.put("type", type.name());
        } else {
            params.put("type", timeInForce.getCode());
        }
        switch (type) {
            case LIMIT:
                params.put("price", exHedgeOrder.getHedgePrice().toPlainString());
                params.put("quantity", exHedgeOrder.getHedgeVolume().toPlainString());
                break;
            case MARKET:
                if(HedgeSideTypes.BUY.name().equalsIgnoreCase(exHedgeOrder.getHedgeSide())){
                    params.put("quoteOrderQty", exHedgeOrder.getHedgeVolume().multiply(exHedgeOrder.getHedgePrice()).stripTrailingZeros().toPlainString());
                }else{
                    params.put("quantity",exHedgeOrder.getHedgeVolume().toPlainString());
                }
            break;
            default:break;
        }
        params.put("recvWindow", "60000");
        OrderPlaceResp placeRespTest = placeOrder(params);
        return JSONObject.parseObject(JSON.toJSONString(placeRespTest));
    }

    /**
     * 获取账户成交历史
     *
     * @param exHedgeOrder
     * @return
     */
    public JSON myTrades(ExHedgeOrder exHedgeOrder) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("symbol",exHedgeOrder.getHedgeOrderSymbol().toUpperCase());
        if(StringUtils.isNotBlank(exHedgeOrder.getHedgeOrderId())){
            params.put("orderId",exHedgeOrder.getHedgeOrderId());
        }
        List<MyTrades> myTrades = MexcApiV3AuthExample.myTrades(params);
        log.info("==>>myTrades:{}", JsonUtil.toJson(myTrades));
        return JSON.parseArray(JSON.toJSONString(myTrades));
    }




    /**
     * 查询当前订单
     *
     * @param timestamp
     * @return
     */
    public JSON getOrder(ExHedgeOrder exHedgeOrder, Long timestamp) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        if (timestamp == null || timestamp.equals(0)) {
            timestamp = System.currentTimeMillis() - 60 * 1000;
        }
        Order order = MexcApiV3AuthExample.getOrder(Maps.newHashMap(ImmutableMap.<String, String>builder()
                .put("symbol", exHedgeOrder.getHedgeOrderSymbol().toUpperCase())
                .put("orderId", exHedgeOrder.getHedgeOrderId().toString())
                .put("recvWindow", "60000")
                .build()));
        log.info("==>>order:{}", JsonUtil.toJson(order));
        return JSON.parseObject(JSON.toJSONString(order));
    }

    /**
     * 查询所有订单
     *
     * @param timestamp
     * @return
     */
    public JSON getOrders(ExHedgeOrder order, Long timestamp) {
        return null;
    }

    /**
     * 获取账号信息
     *
     * @return
     */
    public JSON account() {
        Account account = MexcApiV3AuthExample.account(Maps.newHashMap(ImmutableMap.<String, String>builder()
                .put("recvWindow", "60000")
                .build()));
        log.info("==>>account:{}", JsonUtil.toJson(account));
        return JSON.parseObject(JSON.toJSONString(account));
    }

}
