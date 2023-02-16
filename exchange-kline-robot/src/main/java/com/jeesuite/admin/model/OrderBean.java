package com.jeesuite.admin.model;

import com.jeesuite.admin.util.MathUtils;

import java.math.BigDecimal;

public class OrderBean implements Comparable<OrderBean> {
    Integer fuid;//挂单用户ID
    String currency;// 交易类型
    String id;// 委托挂单号
    BigDecimal price;// 单价
    Integer status;// 挂单状态(1：待成交,2：待成交未交易部份,3：交易完成,5：已取消)
    BigDecimal total_amount;// 挂单总数量
    BigDecimal trade_amount;//已成交数量
    Long trade_date;// 委托时间
    BigDecimal trade_money;// 已成交总金额
    BigDecimal trade_price;// 成交均价
    Integer type;// 挂单类型 0/1[buy/sell]
    BigDecimal other_amount;// 成交均价

    public Integer getFuid() {
        return fuid;
    }

    public void setFuid(Integer fuid) {
        this.fuid = fuid;
    }

    public BigDecimal getOther_amount() {
        return MathUtils.sub(total_amount, trade_amount);
    }

    public void setOther_amount(BigDecimal other_amount) {
        this.other_amount = other_amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public BigDecimal getTrade_amount() {
        return trade_amount;
    }

    public void setTrade_amount(BigDecimal trade_amount) {
        this.trade_amount = trade_amount;
    }

    public Long getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(Long trade_date) {
        this.trade_date = trade_date;
    }

    public BigDecimal getTrade_money() {
        return trade_money;
    }

    public void setTrade_money(BigDecimal trade_money) {
        this.trade_money = trade_money;
    }

    public BigDecimal getTrade_price() {
        return trade_price;
    }

    public void setTrade_price(BigDecimal trade_price) {
        this.trade_price = trade_price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public int compareTo(OrderBean o) {
        return this.getOther_amount().compareTo(o.getOther_amount());
    }

    public static void main(String[] args) {
        System.out.println("---:"+Math.exp(4));
    }
}
