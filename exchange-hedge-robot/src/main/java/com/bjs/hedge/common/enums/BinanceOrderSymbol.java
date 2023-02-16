package com.bjs.hedge.common.enums;


/**
 * 币安订单状态类型
 */
public enum BinanceOrderSymbol {
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
    EXPIRED,
}
