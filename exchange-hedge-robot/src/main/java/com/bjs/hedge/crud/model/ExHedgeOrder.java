package com.bjs.hedge.crud.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "ex_hedge_order")
public class ExHedgeOrder {
    /**
     * 对冲订单ID
     */
    @Id
    @Column(name = "hedge_order_id")
    private String hedgeOrderId;

    /**
     * 对手币对
     */
    @Column(name = "opponent_order_symbol")
    private String opponentOrderSymbol;

    /**
     * 对冲币对
     */
    @Column(name = "hedge_order_symbol")
    private String hedgeOrderSymbol;

    /**
     * 对冲平台
     */
    @Column(name = "hedge_platform")
    private String hedgePlatform;

    /**
     * 对冲下单用户
     */
    @Column(name = "hedge_user_id")
    private String hedgeUserId;

    /**
     * 对冲买卖方向
     */
    @Column(name = "hedge_side")
    private String hedgeSide;

    /**
     * 对冲订单更新时间
     */
    @Column(name = "hedge_update_time")
    private Date hedgeUpdateTime;

    /**
     * 对冲订单创建时间
     */
    @Column(name = "hedge_create_time")
    private Date hedgeCreateTime;

    /**
     * 对冲挂单价格
     */
    @Column(name = "hedge_price")
    private BigDecimal hedgePrice;

    /**
     * 对冲挂单总数量
     */
    @Column(name = "hedge_volume")
    private BigDecimal hedgeVolume;

    /**
     * 对冲挂单手续费率
     */
    @Column(name = "hedge_fee_rate_maker")
    private BigDecimal hedgeFeeRateMaker;

    /**
     * 对冲吃单手续费率
     */
    @Column(name = "hedge_fee_rate_taker")
    private BigDecimal hedgeFeeRateTaker;

    /**
     * 对冲手续费
     */
    @Column(name = "hedge_fee")
    private BigDecimal hedgeFee;

    /**
     * 对冲成交均价
     */
    @Column(name = "hedge_deal_avg_price")
    private BigDecimal hedgeDealAvgPrice;

    /**
     * 对冲已成交金额
     */
    @Column(name = "hedge_deal_money")
    private BigDecimal hedgeDealMoney;

    /**
     * 对冲成交数量
     */
    @Column(name = "hedge_deal_volume")
    private BigDecimal hedgeDealVolume;

    /**
     * 对冲订单状态
     */
    @Column(name = "hedge_status")
    private String hedgeStatus;

    /**
     * 对冲结果数据
     */
    @Column(name = "hedge_res")
    private String hedgeRes;

    /**
     * 对手订单所在表
     */
    @Column(name = "opponent_order_table")
    private String opponentOrderTable;

    /**
     * 对手订单所在表唯一ID
     */
    @Column(name = "opponent_order_table_unique_id")
    private Long opponentOrderTableUniqueId;

    /**
     * 对手买卖方向
     */
    @Column(name = "opponent_side")
    private String opponentSide;

    /**
     * 对手成交数量
     */
    @Column(name = "opponent_deal_volume")
    private BigDecimal opponentDealVolume;

    /**
     * 对手已成交金额
     */
    @Column(name = "opponent_deal_money")
    private BigDecimal opponentDealMoney;

    /**
     * 对手成交均价
     */
    @Column(name = "opponent_avg_price")
    private BigDecimal opponentAvgPrice;

    /**
     * 对手成交时间
     */
    @Column(name = "opponent_create_time")
    private Date opponentCreateTime;

    /**
     * 对手手续费
     */
    @Column(name = "opponent_fee")
    private BigDecimal opponentFee;

    /**
     * 对冲订单详情
     */
    @Column(name = "hedge_order_detail")
    private String hedgeOrderDetail;
    /**
     * 盈利
     */
    @Column(name = "`profit`")
    private BigDecimal profit;

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    /**
     * 获取对冲订单ID
     *
     * @return hedge_order_id - 对冲订单ID
     */
    public String getHedgeOrderId() {
        return hedgeOrderId;
    }

    /**
     * 设置对冲订单ID
     *
     * @param hedgeOrderId 对冲订单ID
     */
    public void setHedgeOrderId(String hedgeOrderId) {
        this.hedgeOrderId = hedgeOrderId;
    }

    /**
     * 获取对手币对
     *
     * @return opponent_order_symbol - 对手币对
     */
    public String getOpponentOrderSymbol() {
        return opponentOrderSymbol;
    }

    /**
     * 设置对手币对
     *
     * @param opponentOrderSymbol 对手币对
     */
    public void setOpponentOrderSymbol(String opponentOrderSymbol) {
        this.opponentOrderSymbol = opponentOrderSymbol;
    }

    /**
     * 获取对冲币对
     *
     * @return hedge_order_symbol - 对冲币对
     */
    public String getHedgeOrderSymbol() {
        return hedgeOrderSymbol;
    }

    /**
     * 设置对冲币对
     *
     * @param hedgeOrderSymbol 对冲币对
     */
    public void setHedgeOrderSymbol(String hedgeOrderSymbol) {
        this.hedgeOrderSymbol = hedgeOrderSymbol;
    }

    /**
     * 获取对冲平台
     *
     * @return hedge_platform - 对冲平台
     */
    public String getHedgePlatform() {
        return hedgePlatform;
    }

    /**
     * 设置对冲平台
     *
     * @param hedgePlatform 对冲平台
     */
    public void setHedgePlatform(String hedgePlatform) {
        this.hedgePlatform = hedgePlatform;
    }

    /**
     * 获取对冲下单用户
     *
     * @return hedge_user_id - 对冲下单用户
     */
    public String getHedgeUserId() {
        return hedgeUserId;
    }

    /**
     * 设置对冲下单用户
     *
     * @param hedgeUserId 对冲下单用户
     */
    public void setHedgeUserId(String hedgeUserId) {
        this.hedgeUserId = hedgeUserId;
    }

    /**
     * 获取对冲买卖方向
     *
     * @return hedge_side - 对冲买卖方向
     */
    public String getHedgeSide() {
        return hedgeSide;
    }

    /**
     * 设置对冲买卖方向
     *
     * @param hedgeSide 对冲买卖方向
     */
    public void setHedgeSide(String hedgeSide) {
        this.hedgeSide = hedgeSide;
    }

    /**
     * 获取对冲订单更新时间
     *
     * @return hedge_update_time - 对冲订单更新时间
     */
    public Date getHedgeUpdateTime() {
        return hedgeUpdateTime;
    }

    /**
     * 设置对冲订单更新时间
     *
     * @param hedgeUpdateTime 对冲订单更新时间
     */
    public void setHedgeUpdateTime(Date hedgeUpdateTime) {
        this.hedgeUpdateTime = hedgeUpdateTime;
    }

    /**
     * 获取对冲订单创建时间
     *
     * @return hedge_create_time - 对冲订单创建时间
     */
    public Date getHedgeCreateTime() {
        return hedgeCreateTime;
    }

    /**
     * 设置对冲订单创建时间
     *
     * @param hedgeCreateTime 对冲订单创建时间
     */
    public void setHedgeCreateTime(Date hedgeCreateTime) {
        this.hedgeCreateTime = hedgeCreateTime;
    }

    /**
     * 获取对冲挂单价格
     *
     * @return hedge_price - 对冲挂单价格
     */
    public BigDecimal getHedgePrice() {
        return hedgePrice;
    }

    /**
     * 设置对冲挂单价格
     *
     * @param hedgePrice 对冲挂单价格
     */
    public void setHedgePrice(BigDecimal hedgePrice) {
        this.hedgePrice = hedgePrice;
    }

    /**
     * 获取对冲挂单总数量
     *
     * @return hedge_volume - 对冲挂单总数量
     */
    public BigDecimal getHedgeVolume() {
        return hedgeVolume;
    }

    /**
     * 设置对冲挂单总数量
     *
     * @param hedgeVolume 对冲挂单总数量
     */
    public void setHedgeVolume(BigDecimal hedgeVolume) {
        this.hedgeVolume = hedgeVolume;
    }

    /**
     * 获取对冲挂单手续费率
     *
     * @return hedge_fee_rate_maker - 对冲挂单手续费率
     */
    public BigDecimal getHedgeFeeRateMaker() {
        return hedgeFeeRateMaker;
    }

    /**
     * 设置对冲挂单手续费率
     *
     * @param hedgeFeeRateMaker 对冲挂单手续费率
     */
    public void setHedgeFeeRateMaker(BigDecimal hedgeFeeRateMaker) {
        this.hedgeFeeRateMaker = hedgeFeeRateMaker;
    }

    /**
     * 获取对冲吃单手续费率
     *
     * @return hedge_fee_rate_taker - 对冲吃单手续费率
     */
    public BigDecimal getHedgeFeeRateTaker() {
        return hedgeFeeRateTaker;
    }

    /**
     * 设置对冲吃单手续费率
     *
     * @param hedgeFeeRateTaker 对冲吃单手续费率
     */
    public void setHedgeFeeRateTaker(BigDecimal hedgeFeeRateTaker) {
        this.hedgeFeeRateTaker = hedgeFeeRateTaker;
    }

    /**
     * 获取对冲手续费
     *
     * @return hedge_fee - 对冲手续费
     */
    public BigDecimal getHedgeFee() {
        return hedgeFee;
    }

    /**
     * 设置对冲手续费
     *
     * @param hedgeFee 对冲手续费
     */
    public void setHedgeFee(BigDecimal hedgeFee) {
        this.hedgeFee = hedgeFee;
    }

    /**
     * 获取对冲成交均价
     *
     * @return hedge_deal_avg_price - 对冲成交均价
     */
    public BigDecimal getHedgeDealAvgPrice() {
        return hedgeDealAvgPrice;
    }

    /**
     * 设置对冲成交均价
     *
     * @param hedgeDealAvgPrice 对冲成交均价
     */
    public void setHedgeDealAvgPrice(BigDecimal hedgeDealAvgPrice) {
        this.hedgeDealAvgPrice = hedgeDealAvgPrice;
    }

    /**
     * 获取对冲已成交金额
     *
     * @return hedge_deal_money - 对冲已成交金额
     */
    public BigDecimal getHedgeDealMoney() {
        return hedgeDealMoney;
    }

    /**
     * 设置对冲已成交金额
     *
     * @param hedgeDealMoney 对冲已成交金额
     */
    public void setHedgeDealMoney(BigDecimal hedgeDealMoney) {
        this.hedgeDealMoney = hedgeDealMoney;
    }

    /**
     * 获取对冲成交数量
     *
     * @return hedge_deal_volume - 对冲成交数量
     */
    public BigDecimal getHedgeDealVolume() {
        return hedgeDealVolume;
    }

    /**
     * 设置对冲成交数量
     *
     * @param hedgeDealVolume 对冲成交数量
     */
    public void setHedgeDealVolume(BigDecimal hedgeDealVolume) {
        this.hedgeDealVolume = hedgeDealVolume;
    }

    /**
     * 获取对冲订单状态
     *
     * @return hedge_status - 对冲订单状态
     */
    public String getHedgeStatus() {
        return hedgeStatus;
    }

    /**
     * 设置对冲订单状态
     *
     * @param hedgeStatus 对冲订单状态
     */
    public void setHedgeStatus(String hedgeStatus) {
        this.hedgeStatus = hedgeStatus;
    }

    /**
     * 获取对冲结果数据
     *
     * @return hedge_res - 对冲结果数据
     */
    public String getHedgeRes() {
        return hedgeRes;
    }

    /**
     * 设置对冲结果数据
     *
     * @param hedgeRes 对冲结果数据
     */
    public void setHedgeRes(String hedgeRes) {
        this.hedgeRes = hedgeRes;
    }

    /**
     * 获取对手订单所在表
     *
     * @return opponent_order_table - 对手订单所在表
     */
    public String getOpponentOrderTable() {
        return opponentOrderTable;
    }

    /**
     * 设置对手订单所在表
     *
     * @param opponentOrderTable 对手订单所在表
     */
    public void setOpponentOrderTable(String opponentOrderTable) {
        this.opponentOrderTable = opponentOrderTable;
    }

    /**
     * 获取对手订单所在表唯一ID
     *
     * @return opponent_order_table_unique_id - 对手订单所在表唯一ID
     */
    public Long getOpponentOrderTableUniqueId() {
        return opponentOrderTableUniqueId;
    }

    /**
     * 设置对手订单所在表唯一ID
     *
     * @param opponentOrderTableUniqueId 对手订单所在表唯一ID
     */
    public void setOpponentOrderTableUniqueId(Long opponentOrderTableUniqueId) {
        this.opponentOrderTableUniqueId = opponentOrderTableUniqueId;
    }

    /**
     * 获取对手买卖方向
     *
     * @return opponent_side - 对手买卖方向
     */
    public String getOpponentSide() {
        return opponentSide;
    }

    /**
     * 设置对手买卖方向
     *
     * @param opponentSide 对手买卖方向
     */
    public void setOpponentSide(String opponentSide) {
        this.opponentSide = opponentSide;
    }

    /**
     * 获取对手成交数量
     *
     * @return opponent_deal_volume - 对手成交数量
     */
    public BigDecimal getOpponentDealVolume() {
        return opponentDealVolume;
    }

    /**
     * 设置对手成交数量
     *
     * @param opponentDealVolume 对手成交数量
     */
    public void setOpponentDealVolume(BigDecimal opponentDealVolume) {
        this.opponentDealVolume = opponentDealVolume;
    }

    /**
     * 获取对手已成交金额
     *
     * @return opponent_deal_money - 对手已成交金额
     */
    public BigDecimal getOpponentDealMoney() {
        return opponentDealMoney;
    }

    /**
     * 设置对手已成交金额
     *
     * @param opponentDealMoney 对手已成交金额
     */
    public void setOpponentDealMoney(BigDecimal opponentDealMoney) {
        this.opponentDealMoney = opponentDealMoney;
    }

    /**
     * 获取对手成交均价
     *
     * @return opponent_avg_price - 对手成交均价
     */
    public BigDecimal getOpponentAvgPrice() {
        return opponentAvgPrice;
    }

    /**
     * 设置对手成交均价
     *
     * @param opponentAvgPrice 对手成交均价
     */
    public void setOpponentAvgPrice(BigDecimal opponentAvgPrice) {
        this.opponentAvgPrice = opponentAvgPrice;
    }

    /**
     * 获取对手成交时间
     *
     * @return opponent_create_time - 对手成交时间
     */
    public Date getOpponentCreateTime() {
        return opponentCreateTime;
    }

    /**
     * 设置对手成交时间
     *
     * @param opponentCreateTime 对手成交时间
     */
    public void setOpponentCreateTime(Date opponentCreateTime) {
        this.opponentCreateTime = opponentCreateTime;
    }

    /**
     * 获取对手手续费
     *
     * @return opponent_fee - 对手手续费
     */
    public BigDecimal getOpponentFee() {
        return opponentFee;
    }

    /**
     * 设置对手手续费
     *
     * @param opponentFee 对手手续费
     */
    public void setOpponentFee(BigDecimal opponentFee) {
        this.opponentFee = opponentFee;
    }

    /**
     * 获取对冲订单详情
     *
     * @return hedge_order_detail - 对冲订单详情
     */
    public String getHedgeOrderDetail() {
        return hedgeOrderDetail;
    }

    /**
     * 设置对冲订单详情
     *
     * @param hedgeOrderDetail 对冲订单详情
     */
    public void setHedgeOrderDetail(String hedgeOrderDetail) {
        this.hedgeOrderDetail = hedgeOrderDetail;
    }
}