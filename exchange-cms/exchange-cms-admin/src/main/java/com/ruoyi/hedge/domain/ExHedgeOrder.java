package com.ruoyi.hedge.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 对冲订单对象 ex_hedge_order
 * 
 * @author yolo
 * @date 2022-10-11
 */
public class ExHedgeOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 对手币对 */
    @Excel(name = "对手币对")
    private String opponentOrderSymbol;

    /** 对冲币对 */
    @Excel(name = "对冲币对")
    private String hedgeOrderSymbol;

    /** 对冲订单ID */
    private String hedgeOrderId;

    /** 对冲平台 */
    @Excel(name = "对冲平台")
    private String hedgePlatform;

    /** 对冲下单用户 */
    @Excel(name = "对冲下单用户")
    private String hedgeUserId;

    /** 对冲买卖方向 */
    @Excel(name = "对冲买卖方向")
    private String hedgeSide;

    /** 对冲订单更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "对冲订单更新时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date hedgeUpdateTime;

    /** 对冲订单创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "对冲订单创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date hedgeCreateTime;

    /** 对冲挂单价格 */
    @Excel(name = "对冲挂单价格")
    private BigDecimal hedgePrice;

    /** 对冲挂单总数量 */
    @Excel(name = "对冲挂单总数量")
    private BigDecimal hedgeVolume;

    /** 对冲挂单手续费率 */
    @Excel(name = "对冲挂单手续费率")
    private BigDecimal hedgeFeeRateMaker;

    /** 对冲吃单手续费率 */
    @Excel(name = "对冲吃单手续费率")
    private BigDecimal hedgeFeeRateTaker;

    /** 对冲手续费 */
    @Excel(name = "对冲手续费")
    private BigDecimal hedgeFee;

    /** 对冲成交均价 */
    @Excel(name = "对冲成交均价")
    private BigDecimal hedgeDealAvgPrice;

    /** 对冲已成交金额 */
    @Excel(name = "对冲已成交金额")
    private BigDecimal hedgeDealMoney;

    /** 对冲成交数量 */
    @Excel(name = "对冲成交数量")
    private BigDecimal hedgeDealVolume;

    /** 对冲订单状态 */
    @Excel(name = "对冲订单状态")
    private String hedgeStatus;

    /** 对冲结果数据 */
    @Excel(name = "对冲结果数据")
    private String hedgeRes;

    /** 对冲订单详情 */
    @Excel(name = "对冲订单详情")
    private String hedgeOrderDetail;

    /** 对手订单所在表 */
    @Excel(name = "对手订单所在表")
    private String opponentOrderTable;

    /** 对手订单所在表唯一ID */
    @Excel(name = "对手订单所在表唯一ID")
    private Long opponentOrderTableUniqueId;

    /** 对手买卖方向 */
    @Excel(name = "对手买卖方向")
    private String opponentSide;

    /** 对手成交数量 */
    @Excel(name = "对手成交数量")
    private BigDecimal opponentDealVolume;

    /** 对手已成交金额 */
    @Excel(name = "对手已成交金额")
    private BigDecimal opponentDealMoney;
    /**
     * 盈利
     */
    @Excel(name = "盈利")
    private BigDecimal profit;

    /** 对手成交均价 */
    @Excel(name = "对手成交均价")
    private BigDecimal opponentAvgPrice;

    /** 对手订单创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "对手订单创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date opponentCreateTime;

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public void setOpponentOrderSymbol(String opponentOrderSymbol)
    {
        this.opponentOrderSymbol = opponentOrderSymbol;
    }

    public String getOpponentOrderSymbol() 
    {
        return opponentOrderSymbol;
    }
    public void setHedgeOrderSymbol(String hedgeOrderSymbol) 
    {
        this.hedgeOrderSymbol = hedgeOrderSymbol;
    }

    public String getHedgeOrderSymbol() 
    {
        return hedgeOrderSymbol;
    }
    public void setHedgeOrderId(String hedgeOrderId) 
    {
        this.hedgeOrderId = hedgeOrderId;
    }

    public String getHedgeOrderId() 
    {
        return hedgeOrderId;
    }
    public void setHedgePlatform(String hedgePlatform) 
    {
        this.hedgePlatform = hedgePlatform;
    }

    public String getHedgePlatform() 
    {
        return hedgePlatform;
    }
    public void setHedgeUserId(String hedgeUserId) 
    {
        this.hedgeUserId = hedgeUserId;
    }

    public String getHedgeUserId() 
    {
        return hedgeUserId;
    }
    public void setHedgeSide(String hedgeSide) 
    {
        this.hedgeSide = hedgeSide;
    }

    public String getHedgeSide() 
    {
        return hedgeSide;
    }
    public void setHedgeUpdateTime(Date hedgeUpdateTime) 
    {
        this.hedgeUpdateTime = hedgeUpdateTime;
    }

    public Date getHedgeUpdateTime() 
    {
        return hedgeUpdateTime;
    }
    public void setHedgeCreateTime(Date hedgeCreateTime) 
    {
        this.hedgeCreateTime = hedgeCreateTime;
    }

    public Date getHedgeCreateTime() 
    {
        return hedgeCreateTime;
    }
    public void setHedgePrice(BigDecimal hedgePrice) 
    {
        this.hedgePrice = hedgePrice;
    }

    public BigDecimal getHedgePrice() 
    {
        return hedgePrice;
    }
    public void setHedgeVolume(BigDecimal hedgeVolume) 
    {
        this.hedgeVolume = hedgeVolume;
    }

    public BigDecimal getHedgeVolume() 
    {
        return hedgeVolume;
    }
    public void setHedgeFeeRateMaker(BigDecimal hedgeFeeRateMaker) 
    {
        this.hedgeFeeRateMaker = hedgeFeeRateMaker;
    }

    public BigDecimal getHedgeFeeRateMaker() 
    {
        return hedgeFeeRateMaker;
    }
    public void setHedgeFeeRateTaker(BigDecimal hedgeFeeRateTaker) 
    {
        this.hedgeFeeRateTaker = hedgeFeeRateTaker;
    }

    public BigDecimal getHedgeFeeRateTaker() 
    {
        return hedgeFeeRateTaker;
    }
    public void setHedgeFee(BigDecimal hedgeFee) 
    {
        this.hedgeFee = hedgeFee;
    }

    public BigDecimal getHedgeFee() 
    {
        return hedgeFee;
    }
    public void setHedgeDealAvgPrice(BigDecimal hedgeDealAvgPrice) 
    {
        this.hedgeDealAvgPrice = hedgeDealAvgPrice;
    }

    public BigDecimal getHedgeDealAvgPrice() 
    {
        return hedgeDealAvgPrice;
    }
    public void setHedgeDealMoney(BigDecimal hedgeDealMoney) 
    {
        this.hedgeDealMoney = hedgeDealMoney;
    }

    public BigDecimal getHedgeDealMoney() 
    {
        return hedgeDealMoney;
    }
    public void setHedgeDealVolume(BigDecimal hedgeDealVolume) 
    {
        this.hedgeDealVolume = hedgeDealVolume;
    }

    public BigDecimal getHedgeDealVolume() 
    {
        return hedgeDealVolume;
    }
    public void setHedgeStatus(String hedgeStatus) 
    {
        this.hedgeStatus = hedgeStatus;
    }

    public String getHedgeStatus() 
    {
        return hedgeStatus;
    }
    public void setHedgeRes(String hedgeRes) 
    {
        this.hedgeRes = hedgeRes;
    }

    public String getHedgeRes() 
    {
        return hedgeRes;
    }
    public void setHedgeOrderDetail(String hedgeOrderDetail) 
    {
        this.hedgeOrderDetail = hedgeOrderDetail;
    }

    public String getHedgeOrderDetail() 
    {
        return hedgeOrderDetail;
    }
    public void setOpponentOrderTable(String opponentOrderTable) 
    {
        this.opponentOrderTable = opponentOrderTable;
    }

    public String getOpponentOrderTable() 
    {
        return opponentOrderTable;
    }
    public void setOpponentOrderTableUniqueId(Long opponentOrderTableUniqueId) 
    {
        this.opponentOrderTableUniqueId = opponentOrderTableUniqueId;
    }

    public Long getOpponentOrderTableUniqueId() 
    {
        return opponentOrderTableUniqueId;
    }
    public void setOpponentSide(String opponentSide) 
    {
        this.opponentSide = opponentSide;
    }

    public String getOpponentSide() 
    {
        return opponentSide;
    }
    public void setOpponentDealVolume(BigDecimal opponentDealVolume) 
    {
        this.opponentDealVolume = opponentDealVolume;
    }

    public BigDecimal getOpponentDealVolume() 
    {
        return opponentDealVolume;
    }
    public void setOpponentDealMoney(BigDecimal opponentDealMoney) 
    {
        this.opponentDealMoney = opponentDealMoney;
    }

    public BigDecimal getOpponentDealMoney() 
    {
        return opponentDealMoney;
    }
    public void setOpponentAvgPrice(BigDecimal opponentAvgPrice) 
    {
        this.opponentAvgPrice = opponentAvgPrice;
    }

    public BigDecimal getOpponentAvgPrice() 
    {
        return opponentAvgPrice;
    }
    public void setOpponentCreateTime(Date opponentCreateTime) 
    {
        this.opponentCreateTime = opponentCreateTime;
    }

    public Date getOpponentCreateTime() 
    {
        return opponentCreateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("opponentOrderSymbol", getOpponentOrderSymbol())
            .append("hedgeOrderSymbol", getHedgeOrderSymbol())
            .append("hedgeOrderId", getHedgeOrderId())
            .append("hedgePlatform", getHedgePlatform())
            .append("hedgeUserId", getHedgeUserId())
            .append("hedgeSide", getHedgeSide())
            .append("hedgeUpdateTime", getHedgeUpdateTime())
            .append("hedgeCreateTime", getHedgeCreateTime())
            .append("hedgePrice", getHedgePrice())
            .append("hedgeVolume", getHedgeVolume())
            .append("hedgeFeeRateMaker", getHedgeFeeRateMaker())
            .append("hedgeFeeRateTaker", getHedgeFeeRateTaker())
            .append("hedgeFee", getHedgeFee())
            .append("hedgeDealAvgPrice", getHedgeDealAvgPrice())
            .append("hedgeDealMoney", getHedgeDealMoney())
            .append("hedgeDealVolume", getHedgeDealVolume())
            .append("hedgeStatus", getHedgeStatus())
            .append("hedgeRes", getHedgeRes())
            .append("hedgeOrderDetail", getHedgeOrderDetail())
            .append("opponentOrderTable", getOpponentOrderTable())
            .append("opponentOrderTableUniqueId", getOpponentOrderTableUniqueId())
            .append("opponentSide", getOpponentSide())
            .append("opponentDealVolume", getOpponentDealVolume())
            .append("opponentDealMoney", getOpponentDealMoney())
            .append("opponentAvgPrice", getOpponentAvgPrice())
            .append("opponentCreateTime", getOpponentCreateTime())
            .toString();
    }
}
