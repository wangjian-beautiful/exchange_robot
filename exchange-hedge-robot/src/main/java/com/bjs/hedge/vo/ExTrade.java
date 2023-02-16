package com.bjs.hedge.vo;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ExTrade implements Serializable {
    private Long id;


    private BigDecimal price;


    private BigDecimal volume;


    private Long bidId;


    private Long askId;

    @Length(max = 4)

    private String trendSide;


    private Integer bidUserId;


    private Integer askUserId;


    private BigDecimal buyFee;


    private BigDecimal sellFee;

    @Length(max = 32)

    private String buyFeeCoin;

    @Length(max = 32)

    private String sellFeeCoin;


    private Date ctime;


    private Date mtime;

    private String tableName;

    private Double highest;//半小时内最高
    private Double lowest;//半小时内最低
    private int volumeMins;//设定的分钟数
    private Double volumeCount;//设定时间的交易量总和
    private Integer userId; //交易相关用户ID
    private static final long serialVersionUID = 1L;

    // 数据库额外字段   仅供查询使用
    private Long orderId;  // 订单Id

    @Override
    public String toString() {
        return "ExTrade [id=" + id + ", price=" + price + ", volume=" + volume + ", bidId=" + bidId + ", askId=" + askId
                + ", trendSide=" + trendSide + ", bidUserId=" + bidUserId + ", askUserId=" + askUserId + ", buyFee="
                + buyFee + ", sellFee=" + sellFee + ", buyFeeCoin=" + buyFeeCoin + ", sellFeeCoin=" + sellFeeCoin
                + ", ctime=" + ctime + ", mtime=" + mtime + ", tableName=" + tableName + ", highest=" + highest
                + ", lowest=" + lowest + ", volumeMins=" + volumeMins + ", volumeCount=" + volumeCount + ", userId="
                + userId + "]";
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Double getHighest() {
        return highest;
    }

    public void setHighest(Double highest) {
        this.highest = highest;
    }

    public Double getLowest() {
        return lowest;
    }

    public void setLowest(Double lowest) {
        this.lowest = lowest;
    }

    public int getVolumeMins() {
        return volumeMins;
    }

    public void setVolumeMins(int volumeMins) {
        this.volumeMins = volumeMins;
    }

    public Double getVolumeCount() {
        return volumeCount;
    }

    public void setVolumeCount(Double volumeCount) {
        this.volumeCount = volumeCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Long getAskId() {
        return askId;
    }

    public void setAskId(Long askId) {
        this.askId = askId;
    }

    public String getTrendSide() {
        return trendSide;
    }

    public void setTrendSide(String trendSide) {
        this.trendSide = trendSide == null ? null : trendSide.trim();
    }

    public Integer getBidUserId() {
        return bidUserId;
    }

    public void setBidUserId(Integer bidUserId) {
        this.bidUserId = bidUserId;
    }

    public Integer getAskUserId() {
        return askUserId;
    }

    public void setAskUserId(Integer askUserId) {
        this.askUserId = askUserId;
    }

    public BigDecimal getBuyFee() {
        return buyFee;
    }

    public void setBuyFee(BigDecimal buyFee) {
        this.buyFee = buyFee;
    }

    public BigDecimal getSellFee() {
        return sellFee;
    }

    public void setSellFee(BigDecimal sellFee) {
        this.sellFee = sellFee;
    }

    public String getBuyFeeCoin() {
        return buyFeeCoin;
    }

    public void setBuyFeeCoin(String buyFeeCoin) {
        this.buyFeeCoin = buyFeeCoin == null ? null : buyFeeCoin.trim();
    }

    public String getSellFeeCoin() {
        return sellFeeCoin;
    }

    public void setSellFeeCoin(String sellFeeCoin) {
        this.sellFeeCoin = sellFeeCoin == null ? null : sellFeeCoin.trim();
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public static class Builder {
        private ExTrade obj;

        public Builder() {
            this.obj = new ExTrade();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder price(BigDecimal price) {
            obj.price = price;
            return this;
        }

        public Builder volume(BigDecimal volume) {
            obj.volume = volume;
            return this;
        }

        public Builder bidId(Long bidId) {
            obj.bidId = bidId;
            return this;
        }

        public Builder askId(Long askId) {
            obj.askId = askId;
            return this;
        }

        public Builder trendSide(String trendSide) {
            obj.trendSide = trendSide;
            return this;
        }

        public Builder bidUserId(Integer bidUserId) {
            obj.bidUserId = bidUserId;
            return this;
        }

        public Builder askUserId(Integer askUserId) {
            obj.askUserId = askUserId;
            return this;
        }

        public Builder buyFee(BigDecimal buyFee) {
            obj.buyFee = buyFee;
            return this;
        }

        public Builder sellFee(BigDecimal sellFee) {
            obj.sellFee = sellFee;
            return this;
        }

        public Builder buyFeeCoin(String buyFeeCoin) {
            obj.buyFeeCoin = buyFeeCoin;
            return this;
        }

        public Builder sellFeeCoin(String sellFeeCoin) {
            obj.sellFeeCoin = sellFeeCoin;
            return this;
        }

        public Builder ctime(Date ctime) {
            obj.ctime = ctime;
            return this;
        }

        public Builder mtime(Date mtime) {
            obj.mtime = mtime;
            return this;
        }

        public ExTrade build() {
            return this.obj;
        }
    }
}