package com.bjs.hedge.crud.model;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "hedge_robot_config")
public class HedgeRobotConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 币对
     */
    private String symbol;

    /**
     * 金币所币对
     */
    @Column(name = "symbol_bjs")
    private String symbolBjs;

    /**
     * 交易平台
     */
    @Column(name = "trade_platform")
    private String tradePlatform;

    /**
     * 下单类型; LIMIT 限价；MARKET市价
     */
    @Column(name = "binance_order_type")
    private String binanceOrderType;

    /**
     * 下单方式;GTC:成交为止 ;IOC:无法立即成交的部分就撤销,FOK:无法全部立即成交就撤销
     */
    @Column(name = "time_in_force_status")
    private String timeInForceStatus;

    /**
     * 状态
     */
    private String status;

    /**
     * 对冲阈值
     */
    @Column(name = "hedge_out")
    private BigDecimal hedgeOut;

    /**
     * 手机号；已英文逗号分割,
     */
    private String mobile;

    /**
     * 对赌量
     */
    @Column(name = "`betting`")
    private BigDecimal betting;

    public BigDecimal getBetting() {
        return betting;
    }

    public void setBetting(BigDecimal betting) {
        this.betting = betting;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取币对
     *
     * @return symbol - 币对
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 设置币对
     *
     * @param symbol 币对
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 获取金币所币对
     *
     * @return symbol_bjs - 金币所币对
     */
    public String getSymbolBjs() {
        return symbolBjs;
    }

    /**
     * 设置金币所币对
     *
     * @param symbolBjs 金币所币对
     */
    public void setSymbolBjs(String symbolBjs) {
        this.symbolBjs = symbolBjs;
    }

    /**
     * 获取交易平台
     *
     * @return trade_platform - 交易平台
     */
    public String getTradePlatform() {
        return tradePlatform;
    }

    /**
     * 设置交易平台
     *
     * @param tradePlatform 交易平台
     */
    public void setTradePlatform(String tradePlatform) {
        this.tradePlatform = tradePlatform;
    }

    /**
     * 获取下单类型; LIMIT 限价；MARKET市价
     *
     * @return binance_order_type - 下单类型; LIMIT 限价；MARKET市价
     */
    public String getBinanceOrderType() {
        return binanceOrderType;
    }

    /**
     * 设置下单类型; LIMIT 限价；MARKET市价
     *
     * @param binanceOrderType 下单类型; LIMIT 限价；MARKET市价
     */
    public void setBinanceOrderType(String binanceOrderType) {
        this.binanceOrderType = binanceOrderType;
    }

    /**
     * 获取下单方式;GTC:成交为止 ;IOC:无法立即成交的部分就撤销,FOK:无法全部立即成交就撤销
     *
     * @return time_in_force_status - 下单方式;GTC:成交为止 ;IOC:无法立即成交的部分就撤销,FOK:无法全部立即成交就撤销
     */
    public String getTimeInForceStatus() {
        return timeInForceStatus;
    }

    /**
     * 设置下单方式;GTC:成交为止 ;IOC:无法立即成交的部分就撤销,FOK:无法全部立即成交就撤销
     *
     * @param timeInForceStatus 下单方式;GTC:成交为止 ;IOC:无法立即成交的部分就撤销,FOK:无法全部立即成交就撤销
     */
    public void setTimeInForceStatus(String timeInForceStatus) {
        this.timeInForceStatus = timeInForceStatus;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取对冲阈值
     *
     * @return hedge_out - 对冲阈值
     */
    public BigDecimal getHedgeOut() {
        return hedgeOut;
    }

    /**
     * 设置对冲阈值
     *
     * @param hedgeOut 对冲阈值
     */
    public void setHedgeOut(BigDecimal hedgeOut) {
        this.hedgeOut = hedgeOut;
    }

    /**
     * 获取手机号；已英文逗号分割,
     *
     * @return mobile - 手机号；已英文逗号分割,
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号；已英文逗号分割,
     *
     * @param mobile 手机号；已英文逗号分割,
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}