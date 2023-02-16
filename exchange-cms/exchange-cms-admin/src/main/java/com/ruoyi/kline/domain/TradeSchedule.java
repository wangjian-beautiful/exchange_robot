package com.ruoyi.kline.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * K线机器人配置对象 trade_schedule
 * 
 * @author yolo
 * @date 2022-10-09
 */
public class TradeSchedule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户access_key */
    @Excel(name = "用户access_key")
    private String accessKey;

    /** 用户security_key */
    @Excel(name = "用户security_key")
    private String securityKey;

    /** 交易对 */
    @Excel(name = "交易对")
    private String currency;

    /** 交易对别名（内部使用） */
    @Excel(name = "交易对别名", readConverterExp = "内=部使用")
    private String currencyAlias;

    /** 跟随的交易对-可单一交易对，也可多个交易对乘法计算 */
    @Excel(name = "跟随的交易对-可单一交易对，也可多个交易对乘法计算")
    private String currencyTrail;

    /** 状态0不下单 1下单 */
    @Excel(name = "状态0不下单 1下单")
    private String status;

    /** 类型 kline K线机器人，trade刷量机器人 */
    @Excel(name = "类型 kline K线机器人，trade刷量机器人")
    private String type;

    /** 跟随类型 1价格跟随 2波动率跟随【火币】 */
    @Excel(name = "跟随类型 1价格跟随 2波动率跟随【火币】")
    private String followType;

    /** 跟随交易所类型1火币 2ZB 3组合计算 */
    @Excel(name = "跟随交易所类型1火币 2ZB 3组合计算")
    private String followMarket;

    /** 波动率-波动率跟随有效 */
    @Excel(name = "波动率-波动率跟随有效")
    private Long fluctuationRatio;

    /** 交易间隔（毫秒） */
    @Excel(name = "交易间隔", readConverterExp = "毫=秒")
    private Long duration;

    /** robot新盘口配置，盘口价格间隔，默认为1; */
    @Excel(name = "robot新盘口配置，盘口价格间隔，默认为1;")
    private BigDecimal handicapPriceGap;

    /** 买卖一档与行情价间隔 */
    @Excel(name = "买卖一档与行情价间隔")
    private BigDecimal level1PriceGap;

    /** 价格波动，主动下单 */
    @Excel(name = "价格波动，主动下单")
    private BigDecimal activeWavePrice;

    /** 主动下单的时间间隔，避免下单速率太快 */
    @Excel(name = "主动下单的时间间隔，避免下单速率太快")
    private Long activeDuration;

    /** 交易持续时间 */
    @Excel(name = "交易持续时间")
    private Long endTime;

    /** 密码配置 */
    @Excel(name = "密码配置")
    private String password;

    /** 交易API地址 */
    @Excel(name = "交易API地址")
    private String serverUrl;

    /** 开始时间 */
    @Excel(name = "开始时间")
    private Long startTime;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 最低价格 */
    @Excel(name = "最低价格")
    private BigDecimal minPrice;

    /** 最高价格 */
    @Excel(name = "最高价格")
    private BigDecimal maxPrice;

    /** 交易所备注 */
    @Excel(name = "交易所备注")
    private String channel;

    /** 机器人用户ID-在业务系统中的用户ID trade刷量机器人时配置 */
    @Excel(name = "机器人用户ID-在业务系统中的用户ID trade刷量机器人时配置")
    private Long fuid;

    /** 交易对盘口纵深数量，例如20 trade刷量机器人时配置 */
    @Excel(name = "交易对盘口纵深数量，例如20 trade刷量机器人时配置")
    private Long priceCount;

    /** robot新盘口配置，价格档位下单数量随机数乘以的倍数，默认为1 */
    @Excel(name = "robot新盘口配置，价格档位下单数量随机数乘以的倍数，默认为1")
    private BigDecimal handicapRandomFold;

    /** robot盘口配置类型，0表示抛物线形，1表示线性，默认为0 */
    @Excel(name = "robot盘口配置类型，0表示抛物线形，1表示线性，默认为0")
    private Long handicapShape;

    /** 随机数区间的开始值，深度图形状为1线性时有效 */
    @Excel(name = "随机数区间的开始值，深度图形状为1线性时有效")
    private BigDecimal randomBegin;

    /** 随机数区间的结束值，深度图形状为1线性时有效 */
    @Excel(name = "随机数区间的结束值，深度图形状为1线性时有效")
    private BigDecimal randomEnd;

    /** robot新盘口配置，K线下单随机数倍数，默认为1 */
    @Excel(name = "robot新盘口配置，K线下单随机数倍数，默认为1")
    private BigDecimal klineRandomFold;

    /**  */
    @Excel(name = "")
    private Long countTrailScale;

    /**  */
    @Excel(name = "")
    private BigDecimal maxAmount;

    /**  */
    @Excel(name = "")
    private BigDecimal minAmount;

    /**  */
    @Excel(name = "")
    private Long priceInterval;

    /**  */
    @Excel(name = "")
    private Long priceMaxSum;

    /**  */
    @Excel(name = "")
    private Long priceTrailScale;

    /**  */
    @Excel(name = "")
    private Long query;

    /**  */
    @Excel(name = "")
    private Long refreshTime;

    /** 执行的服务编号 */
    @Excel(name = "执行的服务编号")
    private Long serverIndex;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAccessKey(String accessKey) 
    {
        this.accessKey = accessKey;
    }

    public String getAccessKey() 
    {
        return accessKey;
    }
    public void setSecurityKey(String securityKey) 
    {
        this.securityKey = securityKey;
    }

    public String getSecurityKey() 
    {
        return securityKey;
    }
    public void setCurrency(String currency) 
    {
        this.currency = currency;
    }

    public String getCurrency() 
    {
        return currency;
    }
    public void setCurrencyAlias(String currencyAlias) 
    {
        this.currencyAlias = currencyAlias;
    }

    public String getCurrencyAlias() 
    {
        return currencyAlias;
    }
    public void setCurrencyTrail(String currencyTrail) 
    {
        this.currencyTrail = currencyTrail;
    }

    public String getCurrencyTrail() 
    {
        return currencyTrail;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setFollowType(String followType) 
    {
        this.followType = followType;
    }

    public String getFollowType() 
    {
        return followType;
    }
    public void setFollowMarket(String followMarket) 
    {
        this.followMarket = followMarket;
    }

    public String getFollowMarket() 
    {
        return followMarket;
    }
    public void setFluctuationRatio(Long fluctuationRatio) 
    {
        this.fluctuationRatio = fluctuationRatio;
    }

    public Long getFluctuationRatio() 
    {
        return fluctuationRatio;
    }
    public void setDuration(Long duration) 
    {
        this.duration = duration;
    }

    public Long getDuration() 
    {
        return duration;
    }
    public void setHandicapPriceGap(BigDecimal handicapPriceGap) 
    {
        this.handicapPriceGap = handicapPriceGap;
    }

    public BigDecimal getHandicapPriceGap() 
    {
        return handicapPriceGap;
    }
    public void setLevel1PriceGap(BigDecimal level1PriceGap) 
    {
        this.level1PriceGap = level1PriceGap;
    }

    public BigDecimal getLevel1PriceGap() 
    {
        return level1PriceGap;
    }
    public void setActiveWavePrice(BigDecimal activeWavePrice) 
    {
        this.activeWavePrice = activeWavePrice;
    }

    public BigDecimal getActiveWavePrice() 
    {
        return activeWavePrice;
    }
    public void setActiveDuration(Long activeDuration) 
    {
        this.activeDuration = activeDuration;
    }

    public Long getActiveDuration() 
    {
        return activeDuration;
    }
    public void setEndTime(Long endTime) 
    {
        this.endTime = endTime;
    }

    public Long getEndTime() 
    {
        return endTime;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setServerUrl(String serverUrl) 
    {
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() 
    {
        return serverUrl;
    }
    public void setStartTime(Long startTime) 
    {
        this.startTime = startTime;
    }

    public Long getStartTime() 
    {
        return startTime;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setMinPrice(BigDecimal minPrice) 
    {
        this.minPrice = minPrice;
    }

    public BigDecimal getMinPrice() 
    {
        return minPrice;
    }
    public void setMaxPrice(BigDecimal maxPrice) 
    {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMaxPrice() 
    {
        return maxPrice;
    }
    public void setChannel(String channel) 
    {
        this.channel = channel;
    }

    public String getChannel() 
    {
        return channel;
    }
    public void setFuid(Long fuid) 
    {
        this.fuid = fuid;
    }

    public Long getFuid() 
    {
        return fuid;
    }
    public void setPriceCount(Long priceCount) 
    {
        this.priceCount = priceCount;
    }

    public Long getPriceCount() 
    {
        return priceCount;
    }
    public void setHandicapRandomFold(BigDecimal handicapRandomFold) 
    {
        this.handicapRandomFold = handicapRandomFold;
    }

    public BigDecimal getHandicapRandomFold() 
    {
        return handicapRandomFold;
    }
    public void setHandicapShape(Long handicapShape) 
    {
        this.handicapShape = handicapShape;
    }

    public Long getHandicapShape() 
    {
        return handicapShape;
    }
    public void setRandomBegin(BigDecimal randomBegin) 
    {
        this.randomBegin = randomBegin;
    }

    public BigDecimal getRandomBegin() 
    {
        return randomBegin;
    }
    public void setRandomEnd(BigDecimal randomEnd) 
    {
        this.randomEnd = randomEnd;
    }

    public BigDecimal getRandomEnd() 
    {
        return randomEnd;
    }
    public void setKlineRandomFold(BigDecimal klineRandomFold) 
    {
        this.klineRandomFold = klineRandomFold;
    }

    public BigDecimal getKlineRandomFold() 
    {
        return klineRandomFold;
    }
    public void setCountTrailScale(Long countTrailScale) 
    {
        this.countTrailScale = countTrailScale;
    }

    public Long getCountTrailScale() 
    {
        return countTrailScale;
    }
    public void setMaxAmount(BigDecimal maxAmount) 
    {
        this.maxAmount = maxAmount;
    }

    public BigDecimal getMaxAmount() 
    {
        return maxAmount;
    }
    public void setMinAmount(BigDecimal minAmount) 
    {
        this.minAmount = minAmount;
    }

    public BigDecimal getMinAmount() 
    {
        return minAmount;
    }
    public void setPriceInterval(Long priceInterval) 
    {
        this.priceInterval = priceInterval;
    }

    public Long getPriceInterval() 
    {
        return priceInterval;
    }
    public void setPriceMaxSum(Long priceMaxSum) 
    {
        this.priceMaxSum = priceMaxSum;
    }

    public Long getPriceMaxSum() 
    {
        return priceMaxSum;
    }
    public void setPriceTrailScale(Long priceTrailScale) 
    {
        this.priceTrailScale = priceTrailScale;
    }

    public Long getPriceTrailScale() 
    {
        return priceTrailScale;
    }
    public void setQuery(Long query) 
    {
        this.query = query;
    }

    public Long getQuery() 
    {
        return query;
    }
    public void setRefreshTime(Long refreshTime) 
    {
        this.refreshTime = refreshTime;
    }

    public Long getRefreshTime() 
    {
        return refreshTime;
    }
    public void setServerIndex(Long serverIndex) 
    {
        this.serverIndex = serverIndex;
    }

    public Long getServerIndex() 
    {
        return serverIndex;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accessKey", getAccessKey())
            .append("securityKey", getSecurityKey())
            .append("currency", getCurrency())
            .append("currencyAlias", getCurrencyAlias())
            .append("currencyTrail", getCurrencyTrail())
            .append("status", getStatus())
            .append("type", getType())
            .append("followType", getFollowType())
            .append("followMarket", getFollowMarket())
            .append("fluctuationRatio", getFluctuationRatio())
            .append("duration", getDuration())
            .append("handicapPriceGap", getHandicapPriceGap())
            .append("level1PriceGap", getLevel1PriceGap())
            .append("activeWavePrice", getActiveWavePrice())
            .append("activeDuration", getActiveDuration())
            .append("endTime", getEndTime())
            .append("password", getPassword())
            .append("serverUrl", getServerUrl())
            .append("startTime", getStartTime())
            .append("updateTime", getUpdateTime())
            .append("userName", getUserName())
            .append("minPrice", getMinPrice())
            .append("maxPrice", getMaxPrice())
            .append("channel", getChannel())
            .append("fuid", getFuid())
            .append("priceCount", getPriceCount())
            .append("createTime", getCreateTime())
            .append("handicapRandomFold", getHandicapRandomFold())
            .append("handicapShape", getHandicapShape())
            .append("randomBegin", getRandomBegin())
            .append("randomEnd", getRandomEnd())
            .append("klineRandomFold", getKlineRandomFold())
            .append("countTrailScale", getCountTrailScale())
            .append("maxAmount", getMaxAmount())
            .append("minAmount", getMinAmount())
            .append("priceInterval", getPriceInterval())
            .append("priceMaxSum", getPriceMaxSum())
            .append("priceTrailScale", getPriceTrailScale())
            .append("query", getQuery())
            .append("refreshTime", getRefreshTime())
            .append("serverIndex", getServerIndex())
            .toString();
    }
}
