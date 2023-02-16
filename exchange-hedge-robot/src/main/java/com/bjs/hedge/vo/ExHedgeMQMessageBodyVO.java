package com.bjs.hedge.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExHedgeMQMessageBodyVO implements Serializable {

    /**
     * 成交订单所在表
     */
    private String exTradeTable;

    /**
     * 对手订单所在表唯一ID
     */
    private Long exTradeTableUniqueId;

    /**
     * 下单币对
     */
    private String symbol;

    /**
     * 下单币对
     */
    private String symbolBjs ;

}