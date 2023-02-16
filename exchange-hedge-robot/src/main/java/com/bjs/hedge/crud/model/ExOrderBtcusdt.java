package com.bjs.hedge.crud.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ex_order_btcusdt")
public class ExOrderBtcusdt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 买卖方向
     */
    private String side;

    /**
     * 限价单挂单价格
     */
    private BigDecimal price;

    /**
     * 挂单总数量
     */
    private BigDecimal volume;

    /**
     * 挂单手续费率
     */
    @Column(name = "fee_rate_maker")
    private Double feeRateMaker;

    /**
     * 吃单手续费率
     */
    @Column(name = "fee_rate_taker")
    private Double feeRateTaker;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 支付手续费币种折扣，0为关闭该币种支付手续费
     */
    @Column(name = "fee_coin_rate")
    private Double feeCoinRate;

    /**
     * 成交数量
     */
    @Column(name = "deal_volume")
    private BigDecimal dealVolume;

    /**
     * 已成交金额
     */
    @Column(name = "deal_money")
    private BigDecimal dealMoney;

    /**
     * 成交均价
     */
    @Column(name = "avg_price")
    private BigDecimal avgPrice;

    /**
     * 订单状态：0 init，1 new，2 filled，3 part_filled，4 canceled，5 pending_cancel，6 expired
     */
    private Byte status;

    /**
     * 委托类型：1 limit，2 market，3 stop
     */
    private Byte type;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 订单来源：1web，2app，3api
     */
    private Byte source;

    /**
     * 订单类型1:常规订单，2 杠杆订单
     */
    @Column(name = "order_type")
    private Byte orderType;

    private Date mtime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     * 获取限价单挂单价格
     *
     * @return price - 限价单挂单价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置限价单挂单价格
     *
     * @param price 限价单挂单价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取挂单总数量
     *
     * @return volume - 挂单总数量
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * 设置挂单总数量
     *
     * @param volume 挂单总数量
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * 获取挂单手续费率
     *
     * @return fee_rate_maker - 挂单手续费率
     */
    public Double getFeeRateMaker() {
        return feeRateMaker;
    }

    /**
     * 设置挂单手续费率
     *
     * @param feeRateMaker 挂单手续费率
     */
    public void setFeeRateMaker(Double feeRateMaker) {
        this.feeRateMaker = feeRateMaker;
    }

    /**
     * 获取吃单手续费率
     *
     * @return fee_rate_taker - 吃单手续费率
     */
    public Double getFeeRateTaker() {
        return feeRateTaker;
    }

    /**
     * 设置吃单手续费率
     *
     * @param feeRateTaker 吃单手续费率
     */
    public void setFeeRateTaker(Double feeRateTaker) {
        this.feeRateTaker = feeRateTaker;
    }

    /**
     * 获取手续费
     *
     * @return fee - 手续费
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 设置手续费
     *
     * @param fee 手续费
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 获取支付手续费币种折扣，0为关闭该币种支付手续费
     *
     * @return fee_coin_rate - 支付手续费币种折扣，0为关闭该币种支付手续费
     */
    public Double getFeeCoinRate() {
        return feeCoinRate;
    }

    /**
     * 设置支付手续费币种折扣，0为关闭该币种支付手续费
     *
     * @param feeCoinRate 支付手续费币种折扣，0为关闭该币种支付手续费
     */
    public void setFeeCoinRate(Double feeCoinRate) {
        this.feeCoinRate = feeCoinRate;
    }

    /**
     * 获取成交数量
     *
     * @return deal_volume - 成交数量
     */
    public BigDecimal getDealVolume() {
        return dealVolume;
    }

    /**
     * 设置成交数量
     *
     * @param dealVolume 成交数量
     */
    public void setDealVolume(BigDecimal dealVolume) {
        this.dealVolume = dealVolume;
    }

    /**
     * 获取已成交金额
     *
     * @return deal_money - 已成交金额
     */
    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    /**
     * 设置已成交金额
     *
     * @param dealMoney 已成交金额
     */
    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    /**
     * 获取成交均价
     *
     * @return avg_price - 成交均价
     */
    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    /**
     * 设置成交均价
     *
     * @param avgPrice 成交均价
     */
    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    /**
     * 获取订单状态：0 init，1 new，2 filled，3 part_filled，4 canceled，5 pending_cancel，6 expired
     *
     * @return status - 订单状态：0 init，1 new，2 filled，3 part_filled，4 canceled，5 pending_cancel，6 expired
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态：0 init，1 new，2 filled，3 part_filled，4 canceled，5 pending_cancel，6 expired
     *
     * @param status 订单状态：0 init，1 new，2 filled，3 part_filled，4 canceled，5 pending_cancel，6 expired
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取委托类型：1 limit，2 market，3 stop
     *
     * @return type - 委托类型：1 limit，2 market，3 stop
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置委托类型：1 limit，2 market，3 stop
     *
     * @param type 委托类型：1 limit，2 market，3 stop
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取创建时间
     *
     * @return ctime - 创建时间
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * 设置创建时间
     *
     * @param ctime 创建时间
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取订单来源：1web，2app，3api
     *
     * @return source - 订单来源：1web，2app，3api
     */
    public Byte getSource() {
        return source;
    }

    /**
     * 设置订单来源：1web，2app，3api
     *
     * @param source 订单来源：1web，2app，3api
     */
    public void setSource(Byte source) {
        this.source = source;
    }

    /**
     * 获取订单类型1:常规订单，2 杠杆订单
     *
     * @return order_type - 订单类型1:常规订单，2 杠杆订单
     */
    public Byte getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型1:常规订单，2 杠杆订单
     *
     * @param orderType 订单类型1:常规订单，2 杠杆订单
     */
    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    /**
     * @return mtime
     */
    public Date getMtime() {
        return mtime;
    }

    /**
     * @param mtime
     */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
}