package com.jeesuite.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class PriceLastBean {
    public PriceLastBean(String currency, BigDecimal price) {
        this.currency = currency;
        this.price = price;
        this.priceTime = new Date();
    }

    /**
     * 交易对
     */
    private String currency;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 价格时间
     */
    private Date priceTime;
}
