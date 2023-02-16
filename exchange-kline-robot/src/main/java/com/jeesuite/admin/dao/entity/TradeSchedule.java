package com.jeesuite.admin.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 交易策略
 *
 * @author kew
 * @create 2018-08-19 下午5:08
 **/
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TradeSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private String channel;

    private String password;

    private String serverUrl;

    private String accessKey;

    private String securityKey;

    private Long duration;

    private Long startTime;

    private Long endTime;

//    @Column(scale = 8, precision = 12)
//    private BigDecimal minAmount;

//    @Column(scale = 8, precision = 12)
//    private BigDecimal maxAmount;

    private String currency;

    //跟随交易对
    private String followMarket;//跟随交易所类型1火币 2ZB 3组合计算
    private String followType;//跟随类型 1价格跟随 2波动率跟随
    private String currencyTrail;//跟随的交易对-可单一交易对，也可多个交易对乘法计算
//    private Double priceTrailScale;//跟随的交易对 价格比例（%）- 价格跟随和非组合计算生效
//    private Double countTrailScale;//跟随的交易对 数量比例（%）- 价格跟随和非组合计算生效
    private Double fluctuationRatio;//波动率-波动率跟随有效

    private String status;//1启用 0禁用

    private String type;

    //20190507
    private Integer fuid;//机器人用户ID-在业务系统中的用户ID <br/>trade刷量机器人时配置
//    private Integer priceInterval;//交易对盘口密度间隔，例如5 表示密度单位是0.00001 <br/>trade刷量机器人时配置
//    private Double priceMaxSum;//交易对单一盘口最大量，例如100.00 <br/>trade刷量机器人时配置
    private Integer priceCount;//交易对盘口纵深数量，例如20 <br/>trade刷量机器人时配置
    //20190514
    private Integer refreshTime;

    @Column(scale = 8, precision = 12)
    private BigDecimal minPrice;

    @Column(scale = 8, precision = 12)
    private BigDecimal maxPrice;

    /**
     * 是否查询订单  0不查询订单  1查询订单
     */
    private Integer query;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

}
