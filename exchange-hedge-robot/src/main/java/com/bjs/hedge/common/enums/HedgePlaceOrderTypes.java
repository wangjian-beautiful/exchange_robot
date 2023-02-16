package com.bjs.hedge.common.enums;


/**
 * 币安下单类型
 */
public enum HedgePlaceOrderTypes {

    /*
    * LIMIT 限价单
    MARKET 市价单
    STOP_LOSS 止损单
    STOP_LOSS_LIMIT 限价止损单
    TAKE_PROFIT 止盈单
    TAKE_PROFIT_LIMIT 限价止盈单
    LIMIT_MAKER 限价只挂单
    * */
    LIMIT,
    MARKET,
    STOP_LOSS,
    STOP_LOSS_LIMIT,
    TAKE_PROFIT,
    TAKE_PROFIT_LIMIT,
    LIMIT_MAKER;

    public static HedgePlaceOrderTypes getOrderType(String name){
        for (HedgePlaceOrderTypes binanceOrderType : HedgePlaceOrderTypes.values()) {
            if (binanceOrderType.name().equals(name)){
                return binanceOrderType;
            }
        }
        return null;
    }
}
