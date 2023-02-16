package com.bjs.hedge.crud.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "hedge_fail")
public class HedgeFail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 币对
     */
    private String symbol;

    /**
     * 挂单价格
     */
    private BigDecimal price;

    /**
     * 挂单数量
     */
    private BigDecimal volume;

    /**
     * MQ消息
     */
    @Column(name = "mq_message")
    private String mqMessage;

    @Column(name = "`create_time`")
    private Date createTime;


    /**
     * 错误信息
     */
    private String error;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
     * 获取挂单价格
     *
     * @return price - 挂单价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置挂单价格
     *
     * @param price 挂单价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取挂单数量
     *
     * @return volume - 挂单数量
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * 设置挂单数量
     *
     * @param volume 挂单数量
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * 获取MQ消息
     *
     * @return mq_message - MQ消息
     */
    public String getMqMessage() {
        return mqMessage;
    }

    /**
     * 设置MQ消息
     *
     * @param mqMessage MQ消息
     */
    public void setMqMessage(String mqMessage) {
        this.mqMessage = mqMessage;
    }

    /**
     * 获取错误信息
     *
     * @return error - 错误信息
     */
    public String getError() {
        return error;
    }

    /**
     * 设置错误信息
     *
     * @param error 错误信息
     */
    public void setError(String error) {
        this.error = error;
    }
}