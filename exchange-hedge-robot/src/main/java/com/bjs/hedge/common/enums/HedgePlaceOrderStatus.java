package com.bjs.hedge.common.enums;


/**
 * 币安订单状态类型
 */
public class HedgePlaceOrderStatus {
    public enum Binance{
        NEW,//	订单被交易引擎接受
        PARTIALLY_FILLED,//	部分订单被成交
        FILLED,   //订单完全成交
        CANCELED, //	用户撤销了订单
        PENDING_CANCEL,//	撤销中(目前并未使用)
        REJECTED,	//订单没有被交易引擎接受，也没被处理
        /**
         * 订单被交易引擎取消, 比如
         * LIMIT FOK 订单没有成交
         * 市价单没有完全成交
         * 强平期间被取消的订单
         * 交易所维护期间被取消的订单
         */
        EXPIRED;

    }
   public  enum Mexc{
        NEW,//	订单被交易引擎接受
        FILLED,   //订单完全成交
       PARTIALLY_FILLED,// 部分成交
       CANCELED,// 已撤销
       PARTIALLY_CANCELED; //部分撤销
    }



     public static Binance getBinanceOrderStatus(String name){
          for (Binance binance : Binance.values()) {
              if (binance.name().equals(name)){
                  return binance;
              }
          }
          return null;
    }
    public static Mexc getMexcOrderStatus(String name){
        for (Mexc mexc : Mexc.values()) {
            if (mexc.name().equals(name)){
                return mexc;
            }
        }
        return null;
    }

}
