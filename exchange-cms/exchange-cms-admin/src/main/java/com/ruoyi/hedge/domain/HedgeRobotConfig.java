package com.ruoyi.hedge.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 对冲机器人配置对象 hedge_robot_config
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
public class HedgeRobotConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 币对 */
    @Excel(name = "币对")
    private String symbol;

    /** 金币所币对 */
    @Excel(name = "金币所币对")
    private String symbolBjs;

    /** 交易平台 */
    @Excel(name = "交易平台")
    private String tradePlatform;

    /** 下单类型; LIMIT 限价；MARKET市价 */
    @Excel(name = "下单类型; LIMIT 限价；MARKET市价")
    private String binanceOrderType;

    /** 下单方式;GTC:成交为止 ;IOC:无法立即成交的部分就撤销,FOK:无法全部立即成交就撤销 */
    @Excel(name = "下单方式;GTC:成交为止 ;IOC:无法立即成交的部分就撤销,FOK:无法全部立即成交就撤销")
    private String timeInForceStatus;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 对冲阈值 */
    @Excel(name = "对冲阈值")
    private BigDecimal hedgeOut;

    /** 手机号；已英文逗号分割, */
    @Excel(name = "手机号；已英文逗号分割,")
    private String mobile;

    /**
     * 对赌量
     */
    @Excel(name = "对赌量")
    private BigDecimal betting;

    public BigDecimal getBetting() {
        return betting;
    }

    public void setBetting(BigDecimal betting) {
        this.betting = betting;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSymbol(String symbol) 
    {
        this.symbol = symbol;
    }

    public String getSymbol() 
    {
        return symbol;
    }
    public void setSymbolBjs(String symbolBjs) 
    {
        this.symbolBjs = symbolBjs;
    }

    public String getSymbolBjs() 
    {
        return symbolBjs;
    }
    public void setTradePlatform(String tradePlatform) 
    {
        this.tradePlatform = tradePlatform;
    }

    public String getTradePlatform() 
    {
        return tradePlatform;
    }
    public void setBinanceOrderType(String binanceOrderType) 
    {
        this.binanceOrderType = binanceOrderType;
    }

    public String getBinanceOrderType() 
    {
        return binanceOrderType;
    }
    public void setTimeInForceStatus(String timeInForceStatus) 
    {
        this.timeInForceStatus = timeInForceStatus;
    }

    public String getTimeInForceStatus() 
    {
        return timeInForceStatus;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setHedgeOut(BigDecimal hedgeOut) 
    {
        this.hedgeOut = hedgeOut;
    }

    public BigDecimal getHedgeOut() 
    {
        return hedgeOut;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("symbol", getSymbol())
            .append("symbolBjs", getSymbolBjs())
            .append("tradePlatform", getTradePlatform())
            .append("binanceOrderType", getBinanceOrderType())
            .append("timeInForceStatus", getTimeInForceStatus())
            .append("status", getStatus())
            .append("hedgeOut", getHedgeOut())
            .append("mobile", getMobile())
            .toString();
    }
}
