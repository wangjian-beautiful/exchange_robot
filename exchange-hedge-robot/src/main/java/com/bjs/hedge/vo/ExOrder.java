package com.bjs.hedge.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ExOrder implements Serializable {
    private Long id;

    
    private Integer userId;

    @Length(max=4)
    
    private String side;
    
    private String side_msg;

    
    private BigDecimal price;

    
    private BigDecimal volume;
    
    private BigDecimal remainVolume;

    
    private Double feeRateMaker;

    
    private Double feeRateTaker;

    
    private BigDecimal fee;

    
    private Double feeCoinRate;

    
    private BigDecimal dealVolume;

    private BigDecimal dealMoney;

    
    private BigDecimal avgPrice;

    
    private Byte status;
    
    private String status_msg;

    
    private Byte type;

    
    private Date ctime;

    
    private Date mtime;

    
    private Byte source;
    /**
     * 订单类型1:常规订单，2 杠杆订单
     */
    private Byte orderType;

    private String tableName;

    private List<ExTrade> tradeList;

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

    /**
     * 冻结数量
     */
    private BigDecimal freezeVolume;


}