package com.jeesuite.admin.dao.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class ExOrder implements Serializable {
    private Long id;

    @NotEmpty
    private Integer userId;

    @Length(max=4)
    @NotEmpty
    private String side;
    
    private String side_msg;

    @NotEmpty
    private BigDecimal price;

    @NotEmpty
    private BigDecimal volume;
    
    private BigDecimal remainVolume;

    @NotEmpty
    private Double feeRateMaker;

    @NotEmpty
    private Double feeRateTaker;

    @NotEmpty
    private BigDecimal fee;

    @NotEmpty
    private Double feeCoinRate;

    @NotEmpty
    private BigDecimal dealVolume;

    private BigDecimal dealMoney;

    @NotEmpty
    private BigDecimal avgPrice;

    @NotEmpty
    private Byte status;
    
    private String status_msg;

    @NotEmpty
    private Byte type;

    @NotEmpty
    private Date ctime;

    @NotEmpty
    private Date mtime;

    @NotEmpty
    private Byte source;
    /**
     * 订单类型1:常规订单，2 杠杆订单
     */
    private Byte orderType;

    private String tableName;


    private static final long serialVersionUID = 1L;
    // �����ֶ�ҳ��չʾʹ��
    private String pageAmount;
    private String pageVolume;
    private BigDecimal dealAmount;
 /** 交易加价需求 额外字段*/

    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回状态码
     */
    private String code;
    /**
     * 交易币对
     */
    private String symbol;
    /**
     * 原成交价格
     */
    private BigDecimal priceSource;
    /**
     * 原成交数量
     */
    private BigDecimal volumeSource;
    /**
     * 实时加价比例(创建时刻从配置中读取), 0.5表示加价 50%出售
     */
    private BigDecimal sellOverchargeRate;

    /**
     * 实时加价资产配比(创建时刻从配置中读取) 0.6表明 正常资产40%, 另外60%需要加价卖出
     */
    private BigDecimal sellRisenAssetsRate;
    /**
     * 成交记录表ID, 等于ex_trde_表id
     */
    private Long tradeId;

    /** 币对采用计价货币支付手续费, 市价单业务字段 */
    /**
     * 通知撮合数量
     */
    private BigDecimal matchingVolume;

    @Override
    public String toString() {
        return "ExOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", side='" + side + '\'' +
                ", side_msg='" + side_msg + '\'' +
                ", price=" + price +
                ", volume=" + volume +
                ", remainVolume=" + remainVolume +
                ", feeRateMaker=" + feeRateMaker +
                ", feeRateTaker=" + feeRateTaker +
                ", fee=" + fee +
                ", feeCoinRate=" + feeCoinRate +
                ", dealVolume=" + dealVolume +
                ", dealMoney=" + dealMoney +
                ", avgPrice=" + avgPrice +
                ", status=" + status +
                ", status_msg='" + status_msg + '\'' +
                ", type=" + type +
                ", ctime=" + ctime +
                ", mtime=" + mtime +
                ", source=" + source +
                ", orderType=" + orderType +
                ", tableName='" + tableName + '\'' +
                ", pageAmount='" + pageAmount + '\'' +
                ", pageVolume='" + pageVolume + '\'' +
                ", dealAmount=" + dealAmount +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", symbol='" + symbol + '\'' +
                ", priceSource=" + priceSource +
                ", volumeSource=" + volumeSource +
                ", sellOverchargeRate=" + sellOverchargeRate +
                ", sellRisenAssetsRate=" + sellRisenAssetsRate +
                ", tradeId=" + tradeId +
                ", matchingVolume=" + matchingVolume +
                ", freezeVolume=" + freezeVolume +
                '}';
    }

    /**
     * 冻结数量
     */
    private BigDecimal freezeVolume;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPriceSource() {
        return priceSource;
    }

    public void setPriceSource(BigDecimal priceSource) {
        this.priceSource = priceSource;
    }

    public BigDecimal getVolumeSource() {
        return volumeSource;
    }

    public void setVolumeSource(BigDecimal volumeSource) {
        this.volumeSource = volumeSource;
    }

    public BigDecimal getSellOverchargeRate() {
        return sellOverchargeRate;
    }

    public void setSellOverchargeRate(BigDecimal sellOverchargeRate) {
        this.sellOverchargeRate = sellOverchargeRate;
    }

    public BigDecimal getSellRisenAssetsRate() {
        return sellRisenAssetsRate;
    }

    public void setSellRisenAssetsRate(BigDecimal sellRisenAssetsRate) {
        this.sellRisenAssetsRate = sellRisenAssetsRate;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public String getPageAmount() {
        return pageAmount;
    }

    public void setPageAmount(String pageAmount) {
        this.pageAmount = pageAmount;
    }

    public String getPageVolume() {
        return pageVolume;
    }

    public void setPageVolume(String pageVolume) {
        this.pageVolume = pageVolume;
    }
    
	public String getSide_msg() {
		return side_msg;
	}

	public void setSide_msg(String side_msg) {
		this.side_msg = side_msg;
	}

	public BigDecimal getRemainVolume() {
		return remainVolume;
	}

	public void setRemainVolume(BigDecimal remainVolume) {
		this.remainVolume = remainVolume;
	}

	public String getStatus_msg() {
		return status_msg;
	}

	public void setStatus_msg(String status_msg) {
		this.status_msg = status_msg;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side == null ? null : side.trim();
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

    public Double getFeeRateMaker() {
        return feeRateMaker;
    }

    public void setFeeRateMaker(Double feeRateMaker) {
        this.feeRateMaker = feeRateMaker;
    }

    public Double getFeeRateTaker() {
        return feeRateTaker;
    }

    public void setFeeRateTaker(Double feeRateTaker) {
        this.feeRateTaker = feeRateTaker;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Double getFeeCoinRate() {
        return feeCoinRate;
    }

    public void setFeeCoinRate(Double feeCoinRate) {
        this.feeCoinRate = feeCoinRate;
    }

    public BigDecimal getDealVolume() {
        return dealVolume;
    }

    public void setDealVolume(BigDecimal dealVolume) {
        this.dealVolume = dealVolume;
    }

    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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

    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public BigDecimal getMatchingVolume() {
        return matchingVolume;
    }

    public void setMatchingVolume(BigDecimal matchingVolume) {
        this.matchingVolume = matchingVolume;
    }

    public BigDecimal getFreezeVolume() {
        return freezeVolume;
    }

    public void setFreezeVolume(BigDecimal freezeVolume) {
        this.freezeVolume = freezeVolume;
    }

    public static class Builder {
        private ExOrder obj;

        public Builder() {
            this.obj = new ExOrder();
        }

        public Builder id(Long id) {
            obj.id = id;
            return this;
        }

        public Builder userId(Integer userId) {
            obj.userId = userId;
            return this;
        }

        public Builder side(String side) {
            obj.side = side;
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

        public Builder feeRateMaker(Double feeRateMaker) {
            obj.feeRateMaker = feeRateMaker;
            return this;
        }

        public Builder feeRateTaker(Double feeRateTaker) {
            obj.feeRateTaker = feeRateTaker;
            return this;
        }

        public Builder fee(BigDecimal fee) {
            obj.fee = fee;
            return this;
        }

        public Builder feeCoinRate(Double feeCoinRate) {
            obj.feeCoinRate = feeCoinRate;
            return this;
        }

        public Builder dealVolume(BigDecimal dealVolume) {
            obj.dealVolume = dealVolume;
            return this;
        }

        public Builder dealMoney(BigDecimal dealMoney) {
            obj.dealMoney = dealMoney;
            return this;
        }

        public Builder avgPrice(BigDecimal avgPrice) {
            obj.avgPrice = avgPrice;
            return this;
        }

        public Builder status(Byte status) {
            obj.status = status;
            return this;
        }

        public Builder type(Byte type) {
            obj.type = type;
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

        public Builder source(Byte source) {
            obj.source = source;
            return this;
        }

        public Builder orderType(Byte orderType) {
            obj.orderType = orderType;
            return this;
        }

        public ExOrder build() {
            return this.obj;
        }
    }
}