package com.jeesuite.admin.ws.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author baixiaozheng
 * @desc ${DESCRIPTION}
 * @date 2018/9/7 下午2:48
 */
@Data
public class MarketVo implements Serializable {


    /**
     * 成交量
     */
    private BigDecimal amount;

    /**
     * 开盘价
     */
    private BigDecimal open;

    /**
     * 收盘价,当K线为最晚的一根时，是最新成交价
     */
    private BigDecimal close;

    /**
     * 最高价
     */
    private BigDecimal high;

    /**
     * 时间
     */
    private long timestamp;

    /**
     * 涨跌
     */
    private BigDecimal change;
    private BigDecimal count;

    /**
     * 最低价
     */
    private BigDecimal low;

    /**
     * 成交额, 即 sum(每一笔成交价 * 该笔的成交量)
     */
    private BigDecimal vol;

    private String name;

    private String img;

}
