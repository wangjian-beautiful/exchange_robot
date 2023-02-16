package com.ruoyi.hedge.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 对冲失败对象 hedge_fail
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
public class HedgeFail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 币对 */
    @Excel(name = "币对")
    private String symbol;

    /** 挂单价格 */
    @Excel(name = "挂单价格")
    private BigDecimal price;

    /** 挂单数量 */
    @Excel(name = "挂单数量")
    private BigDecimal volume;

    /** MQ消息 */
    @Excel(name = "MQ消息")
    private String mqMessage;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String error;

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
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setVolume(BigDecimal volume) 
    {
        this.volume = volume;
    }

    public BigDecimal getVolume() 
    {
        return volume;
    }
    public void setMqMessage(String mqMessage) 
    {
        this.mqMessage = mqMessage;
    }

    public String getMqMessage() 
    {
        return mqMessage;
    }
    public void setError(String error) 
    {
        this.error = error;
    }

    public String getError() 
    {
        return error;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("symbol", getSymbol())
            .append("price", getPrice())
            .append("volume", getVolume())
            .append("mqMessage", getMqMessage())
            .append("createTime", getCreateTime())
            .append("error", getError())
            .toString();
    }
}
