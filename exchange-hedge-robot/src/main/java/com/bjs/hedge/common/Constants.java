package com.bjs.hedge.common;

import java.math.BigDecimal;

/**
 * 常量
 *
 * @author
 * @create
 **/

public class Constants {

    /**
     * redis 存储MQ前缀
     */
    public static final String LOCK_HEDGE_MQ_MESSAGE_UNIQUE_KEY = "LOCK_HEDGE_MQ_MESSAGE_UNIQUE_KEY_";

    /**
     * redis 查询TradeKey 前缀
     */
    public static final String LOCK_HEDGE_ORIGIN_UNIQUE_KEY_PREFIX = "LOCK_HEDGE_ORIGIN_UNIQUE_KEY_PREFIX_";

    /**
     * MQ消费多次放入redis_key 前缀
     */
    public static final String MQ_MESSAGE_UNIQUE_KEY_PREFIX = "LOCK_HEDGE__MQ_MESSAGE_UNIQUE_KEY_";

    /**
     * 对冲消息消费失败重试次数
     */
    public static final Integer MQ_HEDGE_MESSAGE_CONSUM_RETRY = 6;

    /**
     * 对冲订单不完全成交 等待时间 毫秒
     */
    public static final long HEDGE_ORDER_TRADE_OUT_MILLISECONDS = 1000 * 60 * 3;


    /**
     * redis 获取锁 等待时间 SECONDS
     */
    public static final long REDIS_TRY_LOCK_WAIT_SECONDS = 10;

    /**
     * redis 自动释放锁时间 SECONDS
     */
    public static final long REDIS_TRY_LOCK_LEASE_SECONDS = 60;
    /**
     * 币对  统一默认 名称
     */
    public static final String SYMBOL_UNIFIED_DEFAULT_NAME = "统一默认";


    public static final String CONFIG_KV_STORE_ROBOT_WHITE_LIST = "robot.white.list";


    /**
     * redis 获取锁 等待时间 SECONDS
     */
    public static final BigDecimal PLACE_ORDER_VOLUME = BigDecimal.valueOf(0.0005);

}
