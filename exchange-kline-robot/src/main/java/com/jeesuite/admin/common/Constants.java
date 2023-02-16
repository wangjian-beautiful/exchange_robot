package com.jeesuite.admin.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 *
 * @author kew
 * @create 2018-08-19 下午5:13
 **/

public class Constants {

    public static final String LOGIN_SESSION_KEY = "_login_key";

    public static final String DEFAULT_CONFIG_VERSION = "release";


    /********************************* robot ********************************/
    //报价类型 市价 限价
    public static final String TRADE_ORDER_LIMIT = "0";
    //订单下单失败
    public static final String TRADE_ORDER_FAILURE = "-1";
    //订单下单成功，等待撮合中
    public static final String TRADE_ORDER_WAIT_MATCH = "1";
    //订单取消成功
    public static final String TRADE_ORDER_CANCEL_SUCCESS = "4";
    //订单取消失败
    public static final String TRADE_ORDER_CANCEL_FAILURE = "7";

    public static final String TRADE_SCHEDULE_NORAMAL = "1";

    //机器人类型 K线机器人 和 交易机器人
    public static final String TRADE_TYPE_1 = "kline";
    public static final String TRADE_TYPE_2 = "trade";

    /**
     * 交易对处理时间记录
     * 准备弃用的属性，K线和盘口共用一个时间刻度产生不能预料的bug
     */
    public static Map<String, Date> MAP_TRADE_SCHEDULE_DATE = new HashMap<>();

//    //记录盘口时间最近开始刷新的时间
//    public static Map<String, Date> MAP_HANDICAP_SCHEDULE_DATE = new HashMap<>();
//    //记录K线最近开始刷新的时间
//    public static Map<String, Date> MAP_KLINE_SCHEDULE_DATE = new HashMap<>();


//    记录币种上一次的价格，计算波动率，防止异常大小的数据，出现K线插针
    public static Map<String, BigDecimal> MAP_TRADE_SCHEDULE_PRICE = new HashMap<>();


}
