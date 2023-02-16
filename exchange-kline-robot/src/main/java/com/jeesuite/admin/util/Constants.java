package com.jeesuite.admin.util;

import com.alibaba.fastjson.JSONObject;
import com.jeesuite.admin.dao.entity.TradeSchedule;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 */
public class Constants {
    /**
     * 价格最大异常范围，如果超过此范围，则不下单
     */
    public static double SYS_PRICE_EXP_SCALE = 10.0d;
    /**
     * 价格记录，某币种的最后一次下单价格记录
     */
    public static Map<String, BigDecimal> SYS_PRICE_LAST_MAP = new HashMap<>();
    /**
     * 用于存储所有机器人
     * 存储格式：key-交易对|机器人类型 value-机器人数据
     */
    public static Map<String, TradeScheduleEntity> MAP_TRADESCHEDULE = new HashMap<>();
    /**
     * 价格记录（定时器定时获取的价格）
     * 存储格式：key-交易对 value-价格
     */
    public static Map<String, BigDecimal> MAP_TRADESCHEDULE_PRICE = new HashMap<>();
    public static Map<String, BigDecimal[]> MAP_MAX_MIN_PRICES = new HashMap<>();//内存中的价格最大值和最小值
    //波动率机器人使用的锚定价格，[0]-自己交易对jwx_usdt锚定价，[1]-目标交易对btc_usdt锚定价
    public static Map<String, BigDecimal[]> MAP_TRADESCHEDULE_PRICE_ANCHOR = new HashMap<>();

    //价格跟随机器人和波动率机器人跟随的交易对的价格，用于K线下单计算波动率，计算下单量
    //[0]-跟随币种上一个价格刷新周期的价格，[1]-当前周期波动率
    public static Map<String, BigDecimal[]> MAP_TRADESCHEDULE_TRAIL_RATIO = new HashMap<>();

    //记录盘口时间最近开始刷新的时间
    public static Map<String, Date> MAP_HANDICAP_SCHEDULE_DATE = new HashMap<>();
    //记录K线最近开始刷新的时间
    public static Map<String, Date> MAP_KLINE_SCHEDULE_DATE = new HashMap<>();
    //记录币种精度
    public static Map<String, JSONObject> MAP_PRICISION = new HashMap<>();

    /**
     * 币种最后下单时间
     * 储存格式：key-交易对_买或者卖 value-时间
     */
    public static Map<String, Long> MAP_TRADESCHEDULE_TIME = new HashMap<>();
}
