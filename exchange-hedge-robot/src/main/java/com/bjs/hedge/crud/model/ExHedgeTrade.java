package com.bjs.hedge.crud.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ex_hedge_trade")
public class ExHedgeTrade {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 成交ID
     */
    @Column(name = "`trade_id`")
    private String tradeId;

    /**
     * 交易币对
     */
    @Column(name = "`symbol`")
    private String symbol;

    /**
     * 订单ID
     */
    @Column(name = "`order_id`")
    private String orderId;

    /**
     * 成交平台
     */
    @Column(name = "`platform`")
    private String platform;

    /**
     * 买卖方向
     */
    @Column(name = "`side`")
    private String side;

    /**
     * 价格
     */
    @Column(name = "`price`")
    private BigDecimal price;

    /**
     * 数量
     */
    @Column(name = "`qty`")
    private BigDecimal qty;

    /**
     * 成交金额
     */
    @Column(name = "`quoteQty`")
    private BigDecimal quoteqty;

    /**
     * 成交时间
     */
    @Column(name = "`time`")
    private Date time;

    /**
     * 手续费
     */
    @Column(name = "`commission`")
    private BigDecimal commission;

    /**
     * 手续费币种
     */
    @Column(name = "`commissionAsset`")
    private BigDecimal commissionasset;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取成交ID
     *
     * @return trade_id - 成交ID
     */
    public String getTradeId() {
        return tradeId;
    }

    /**
     * 设置成交ID
     *
     * @param tradeId 成交ID
     */
    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    /**
     * 获取交易币对
     *
     * @return symbol - 交易币对
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 设置交易币对
     *
     * @param symbol 交易币对
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取成交平台
     *
     * @return platform - 成交平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置成交平台
     *
     * @param platform 成交平台
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 获取买卖方向
     *
     * @return side - 买卖方向
     */
    public String getSide() {
        return side;
    }

    /**
     * 设置买卖方向
     *
     * @param side 买卖方向
     */
    public void setSide(String side) {
        this.side = side;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取数量
     *
     * @return qty - 数量
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * 设置数量
     *
     * @param qty 数量
     */
    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    /**
     * 获取成交金额
     *
     * @return quoteQty - 成交金额
     */
    public BigDecimal getQuoteqty() {
        return quoteqty;
    }

    /**
     * 设置成交金额
     *
     * @param quoteqty 成交金额
     */
    public void setQuoteqty(BigDecimal quoteqty) {
        this.quoteqty = quoteqty;
    }

    /**
     * 获取成交时间
     *
     * @return time - 成交时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置成交时间
     *
     * @param time 成交时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取手续费
     *
     * @return commission - 手续费
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * 设置手续费
     *
     * @param commission 手续费
     */
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**
     * 获取手续费币种
     *
     * @return commissionAsset - 手续费币种
     */
    public BigDecimal getCommissionasset() {
        return commissionasset;
    }

    /**
     * 设置手续费币种
     *
     * @param commissionasset 手续费币种
     */
    public void setCommissionasset(BigDecimal commissionasset) {
        this.commissionasset = commissionasset;
    }
}