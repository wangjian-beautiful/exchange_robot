package com.ruoyi.hedge.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 成交明细对象 ex_hedge_trade
 * 
 * @author yolo
 * @date 2022-10-13
 */
public class ExHedgeTrade extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 成交ID */
    @Excel(name = "成交ID")
    private String tradeId;

    /** 交易币对 */
    @Excel(name = "交易币对")
    private String symbol;

    /** 订单ID */
    @Excel(name = "订单ID")
    private String orderId;

    /** 成交平台 */
    @Excel(name = "成交平台")
    private String platform;

    /** 买卖方向 */
    @Excel(name = "买卖方向")
    private String side;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal qty;

    /** 成交金额 */
    @Excel(name = "成交金额")
    private BigDecimal quoteQty;

    /** 成交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "成交时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal commission;

    /** 手续费币种 */
    @Excel(name = "手续费币种")
    private BigDecimal commissionAsset;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTradeId(String tradeId) 
    {
        this.tradeId = tradeId;
    }

    public String getTradeId() 
    {
        return tradeId;
    }
    public void setSymbol(String symbol) 
    {
        this.symbol = symbol;
    }

    public String getSymbol() 
    {
        return symbol;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setPlatform(String platform) 
    {
        this.platform = platform;
    }

    public String getPlatform() 
    {
        return platform;
    }
    public void setSide(String side) 
    {
        this.side = side;
    }

    public String getSide() 
    {
        return side;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setQty(BigDecimal qty) 
    {
        this.qty = qty;
    }

    public BigDecimal getQty() 
    {
        return qty;
    }
    public void setQuoteQty(BigDecimal quoteQty) 
    {
        this.quoteQty = quoteQty;
    }

    public BigDecimal getQuoteQty() 
    {
        return quoteQty;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setCommission(BigDecimal commission) 
    {
        this.commission = commission;
    }

    public BigDecimal getCommission() 
    {
        return commission;
    }
    public void setCommissionAsset(BigDecimal commissionAsset) 
    {
        this.commissionAsset = commissionAsset;
    }

    public BigDecimal getCommissionAsset() 
    {
        return commissionAsset;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tradeId", getTradeId())
            .append("symbol", getSymbol())
            .append("orderId", getOrderId())
            .append("platform", getPlatform())
            .append("side", getSide())
            .append("price", getPrice())
            .append("qty", getQty())
            .append("quoteQty", getQuoteQty())
            .append("time", getTime())
            .append("commission", getCommission())
            .append("commissionAsset", getCommissionAsset())
            .toString();
    }
}
