package com.jeesuite.admin.common;

import java.math.BigDecimal;

/**
 * description:枚举常量类
 * author:pc
 * date:2019/3/13
 */
public enum EnumConst {

    // 查询订单
    ROBOT_QUERY_NO_ORDER("不查询订单", "0"),
    ROBOT_QUERY_ORDER("查询订单", "1"),

    // 跟随类型
    ROBOT_FOLLOW_TYPE_PRICE("价格跟随", "1"),
    ROBOT_FOLLOW_TYPE_WAVE("波动率跟随", "2"),

    // 跟随交易所类型
    ROBOT_FOLLOW_MARKET_BINANCE("币安", "1", new BigDecimal("5")),
    ROBOT_FOLLOW_MARKET_MEXC("抹茶", "2", new BigDecimal("1")),
//    ROBOT_FOLLOW_MARKET_COMBINATION("组合计算", "3"),

    // 状态
    ROBOT_SATATUS_NO_ORDER("不下单", "0"),
    ROBOT_SATATUS_ORDER("下单", "1"),

    // 机器人类型
    ROBOT_KLINE("K线机器人", "kline"),
    ROBOT_TRADE("刷量机器人", "trade"),

    // 状态
    STATE_VAILD("有效", "0"),
    STATE_INVAILD("无效", "1"),
    STATE_DELETE("删除", "2"),

    // 操作类型
    OPERATOR_ADD("新增", "add"),
    OPERATOR_EDIT("修改", "edit"),
    OPERATOR_DELETE("删除", "delete");

    // 属性构造方法
    public final String NAME;
    public final String VALUE;

    private BigDecimal volumeMultiple;

    EnumConst(String name, String value) {
        this.NAME = name;
        this.VALUE = value;
    }

    EnumConst(String NAME, String VALUE, BigDecimal volumeMultiple) {
        this.NAME = NAME;
        this.VALUE = VALUE;
        this.volumeMultiple = volumeMultiple;
    }

    public BigDecimal getVolumeMultiple() {
        return volumeMultiple;
    }
}

