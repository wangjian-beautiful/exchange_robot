package com.bjs.hedge.crud.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "hedge_origin")
public class HedgeOrigin {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private Integer id;

    /**
     * 对冲原始表名
     */
    @Column(name = "`origin_table`")
    private String originTable;

    /**
     * 对冲原始ID
     */
    @Column(name = "`origin_id`")
    private Long originId;

    /**
     * 价格
     */
    @Column(name = "`price`")
    private BigDecimal price;

    /**
     * 数量
     */
    @Column(name = "`volume`")
    private BigDecimal volume;

    /**
     * 币对
     */
    @Column(name = "`symbol`")
    private String symbol;

    /**
     * 买卖方向
     */
    @Column(name = "`side`")
    private String side;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private String status;

    /**
     * 对赌量
     */
    @Column(name = "`betting`")
    private BigDecimal betting;

    @Column(name = "`create_time`")
    private Date createTime;

    @Column(name = "`update_time`")
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

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
     * 获取对冲原始表名
     *
     * @return origin_table - 对冲原始表名
     */
    public String getOriginTable() {
        return originTable;
    }

    /**
     * 设置对冲原始表名
     *
     * @param originTable 对冲原始表名
     */
    public void setOriginTable(String originTable) {
        this.originTable = originTable;
    }

    /**
     * 获取对冲原始ID
     *
     * @return origin_id - 对冲原始ID
     */
    public Long getOriginId() {
        return originId;
    }

    /**
     * 设置对冲原始ID
     *
     * @param originId 对冲原始ID
     */
    public void setOriginId(Long originId) {
        this.originId = originId;
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
     * @return volume - 数量
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * 设置数量
     *
     * @param volume 数量
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
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
     * 获取对赌量
     *
     * @return betting - 对赌量
     */
    public BigDecimal getBetting() {
        return betting;
    }

    /**
     * 设置对赌量
     *
     * @param betting 对赌量
     */
    public void setBetting(BigDecimal betting) {
        this.betting = betting;
    }
}