package com.bjs.hedge.crud.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* @author facheng
*/
public class ConfigSymbol implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 币对编码：大写：ETHBTC
     */
    private String symbol;

    /**
     * 基准货币，symbol的前半段
     */
    private String base;

    /**
     * 计价货币，symbol的后半段
     */
    private String quote;

    /**
     * 是否开放交易，0否，1是
     */
    private Byte isOpen;


    /**
     * 是否推荐，0否，1是
     */
    private Byte isRecommend;
    /**
     * 开盘价
     */
    private BigDecimal openPrice;

    /**
     * 深度精度计算时使用；1：深度精确位数算整数位数，0：深度精确位数算小数位数
     */
    private Byte isFiat;

    /**
     * 深度精度0
     */
    private Integer depth0Pre;

    /**
     * 深度精度1
     */
    private Integer depth1Pre;

    /**
     * 深度精度2
     */
    private Integer depth2Pre;

    /**
     * 价格精度
     */
    private Integer pricePre;

    /**
     * 数量精度
     */
    private Integer volumePre;

    /**
     * 深度进度条满格数量，新版已废弃
     */
    private Integer depthFullVolume;

    /**
     * 限价最小价
     */
    private BigDecimal limitPriceMin;

    /**
     * 限价最小数量
     */
    private BigDecimal limitVolumeMin;

    /**
     * 市价买最小值
     */
    private BigDecimal marketBuyMin;

    /**
     * 市价卖最小值
     */
    private BigDecimal marketSellMin;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
     */
    private String description;

    /**
     * 上线状态（1沙盒、2线上）
     */
    private Byte releaseStatus;

    /**
     * 是否需要重新发布 1是，0否
     */
    private Byte isRelease;

    /**
     * 是否开启杠杆交易 0：关闭 1：开启
     */
    private Byte isOpenLever;

    /**
     *
     */
    private Date ctime;

    /**
     *
     */
    private Date mtime;

    /**
     * 是否创新区币；0否，1是
     */
    private Byte newcoinFlag;

    /**
     * 列表显示: 0否 1是
     */
    private Byte isShow;

    /**
     * 是否开启mq 0:关闭 1:开启
     */
    private Byte isSendMq;

    /**
     * 主页涨跌幅列表是否显示：0否 1是
     */
    private Byte isIndexShow;

    /**
     * 0 该币对不开放etf  1 该币对开放etf
     */
    private Byte openEtf;

    /**
     * 币对采用计价货币支付手续费开关, 0 关, 1 开
     */
    private Byte openQuoteFee;
    /**
     * 网格交易开关
     */
    private Byte isGridOpen;

    private static final long serialVersionUID = 1L;

    public ConfigSymbol() {
    }

    public Byte getIsGridOpen() {
        return isGridOpen;
    }

    public void setIsGridOpen(Byte isGridOpen) {
        this.isGridOpen = isGridOpen;
    }

    public Byte getOpenQuoteFee() {
        return openQuoteFee;
    }

    public void setOpenQuoteFee(Byte openQuoteFee) {
        this.openQuoteFee = openQuoteFee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol == null ? null : symbol.toLowerCase();
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base == null ? null : base.trim();
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote == null ? null : quote.trim();
    }

    public Byte getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Byte isOpen) {
        this.isOpen = isOpen;
    }

    public BigDecimal getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(BigDecimal openPrice) {
        this.openPrice = openPrice;
    }

    public Byte getIsFiat() {
        return isFiat;
    }

    public void setIsFiat(Byte isFiat) {
        this.isFiat = isFiat;
    }

    public Integer getDepth0Pre() {
        return depth0Pre;
    }

    public void setDepth0Pre(Integer depth0Pre) {
        this.depth0Pre = depth0Pre;
    }

    public Integer getDepth1Pre() {
        return depth1Pre;
    }

    public void setDepth1Pre(Integer depth1Pre) {
        this.depth1Pre = depth1Pre;
    }

    public Integer getDepth2Pre() {
        return depth2Pre;
    }

    public void setDepth2Pre(Integer depth2Pre) {
        this.depth2Pre = depth2Pre;
    }

    public Integer getPricePre() {
        return pricePre;
    }

    public void setPricePre(Integer pricePre) {
        this.pricePre = pricePre;
    }

    public Integer getVolumePre() {
        return volumePre;
    }

    public void setVolumePre(Integer volumePre) {
        this.volumePre = volumePre;
    }

    public Integer getDepthFullVolume() {
        return depthFullVolume;
    }

    public void setDepthFullVolume(Integer depthFullVolume) {
        this.depthFullVolume = depthFullVolume;
    }

    public BigDecimal getLimitPriceMin() {
        return limitPriceMin;
    }

    public void setLimitPriceMin(BigDecimal limitPriceMin) {
        this.limitPriceMin = limitPriceMin;
    }

    public BigDecimal getLimitVolumeMin() {
        return limitVolumeMin;
    }

    public void setLimitVolumeMin(BigDecimal limitVolumeMin) {
        this.limitVolumeMin = limitVolumeMin;
    }

    public BigDecimal getMarketBuyMin() {
        return marketBuyMin;
    }

    public void setMarketBuyMin(BigDecimal marketBuyMin) {
        this.marketBuyMin = marketBuyMin;
    }

    public BigDecimal getMarketSellMin() {
        return marketSellMin;
    }

    public void setMarketSellMin(BigDecimal marketSellMin) {
        this.marketSellMin = marketSellMin;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Byte releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Byte getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Byte isRelease) {
        this.isRelease = isRelease;
    }

    public Byte getIsOpenLever() {
        return isOpenLever;
    }

    public void setIsOpenLever(Byte isOpenLever) {
        this.isOpenLever = isOpenLever;
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

    public Byte getNewcoinFlag() {
        return newcoinFlag;
    }

    public void setNewcoinFlag(Byte newcoinFlag) {
        this.newcoinFlag = newcoinFlag;
    }

    public Byte getIsShow() {
        return isShow;
    }

    public void setIsShow(Byte isShow) {
        this.isShow = isShow;
    }

    public Byte getIsSendMq() {
        return isSendMq;
    }

    public void setIsSendMq(Byte isSendMq) {
        this.isSendMq = isSendMq;
    }

    public Byte getIsIndexShow() {
        return isIndexShow;
    }

    public void setIsIndexShow(Byte isIndexShow) {
        this.isIndexShow = isIndexShow;
    }

    public Byte getOpenEtf() {
        return openEtf;
    }

    public void setOpenEtf(Byte openEtf) {
        this.openEtf = openEtf;
    }

    public Byte getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Byte isRecommend) {
        this.isRecommend = isRecommend;
    }

    public static class Builder {
        private ConfigSymbol obj;

        public Builder() {
            this.obj = new ConfigSymbol();
        }

        public Builder isRecommend(Byte isRecommend) {
            obj.isRecommend = isRecommend;
            return this;
        }

        public Builder id(Integer id) {
            obj.id = id;
            return this;
        }

        public Builder symbol(String symbol) {
            obj.symbol = symbol;
            return this;
        }

        public Builder base(String base) {
            obj.base = base;
            return this;
        }

        public Builder quote(String quote) {
            obj.quote = quote;
            return this;
        }

        public Builder isOpen(Byte isOpen) {
            obj.isOpen = isOpen;
            return this;
        }

        public Builder openPrice(BigDecimal openPrice) {
            obj.openPrice = openPrice;
            return this;
        }

        public Builder isFiat(Byte isFiat) {
            obj.isFiat = isFiat;
            return this;
        }

        public Builder depth0Pre(Integer depth0Pre) {
            obj.depth0Pre = depth0Pre;
            return this;
        }

        public Builder depth1Pre(Integer depth1Pre) {
            obj.depth1Pre = depth1Pre;
            return this;
        }

        public Builder depth2Pre(Integer depth2Pre) {
            obj.depth2Pre = depth2Pre;
            return this;
        }

        public Builder pricePre(Integer pricePre) {
            obj.pricePre = pricePre;
            return this;
        }

        public Builder volumePre(Integer volumePre) {
            obj.volumePre = volumePre;
            return this;
        }

        public Builder depthFullVolume(Integer depthFullVolume) {
            obj.depthFullVolume = depthFullVolume;
            return this;
        }

        public Builder limitPriceMin(BigDecimal limitPriceMin) {
            obj.limitPriceMin = limitPriceMin;
            return this;
        }

        public Builder limitVolumeMin(BigDecimal limitVolumeMin) {
            obj.limitVolumeMin = limitVolumeMin;
            return this;
        }

        public Builder marketBuyMin(BigDecimal marketBuyMin) {
            obj.marketBuyMin = marketBuyMin;
            return this;
        }

        public Builder marketSellMin(BigDecimal marketSellMin) {
            obj.marketSellMin = marketSellMin;
            return this;
        }

        public Builder sort(Integer sort) {
            obj.sort = sort;
            return this;
        }

        public Builder description(String description) {
            obj.description = description;
            return this;
        }

        public Builder releaseStatus(Byte releaseStatus) {
            obj.releaseStatus = releaseStatus;
            return this;
        }

        public Builder isRelease(Byte isRelease) {
            obj.isRelease = isRelease;
            return this;
        }

        public Builder isOpenLever(Byte isOpenLever) {
            obj.isOpenLever = isOpenLever;
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

        public Builder newcoinFlag(Byte newcoinFlag) {
            obj.newcoinFlag = newcoinFlag;
            return this;
        }

        public Builder isShow(Byte isShow) {
            obj.isShow = isShow;
            return this;
        }

        public Builder isIndexShow(Byte isIndexShow) {
            obj.isIndexShow = isIndexShow;
            return this;
        }

        public Builder openEtf(Byte openEtf) {
            obj.openEtf = openEtf;
            return this;
        }

        public Builder isGridOpen(Byte isGridOpen){
            obj.isGridOpen = isGridOpen;
            return this;
        }
        public ConfigSymbol build() {
            return this.obj;
        }


    }
}
