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
 * 交易订单
 *
 * @author kew
 * @create 2018-08-19 下午5:31
 **/
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long scheduleId;

    private String channel;

    private String serverUrl;

    private String accessKey;

    private String securityKey;

    private String orderNo;

    private String currencyCode;

    /***
     * true 买
     * false 卖
     */
    private boolean orderType;

    /***
     * 市价
     * 限价
     */
    private String limitType;

    @Column(scale = 8, precision = 12)
    private BigDecimal count;

    @Column(scale = 8, precision = 12)
    private BigDecimal price;

    @Column(scale = 8, precision = 12)
    private BigDecimal amount;

    @Column(scale = 8, precision = 12)
    private BigDecimal successAmount;

    @Column(scale = 8, precision = 12)
    private BigDecimal successCount;

    @Column(scale = 8, precision = 12)
    private BigDecimal fee;

    private String status;

    private String description;


    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

}
