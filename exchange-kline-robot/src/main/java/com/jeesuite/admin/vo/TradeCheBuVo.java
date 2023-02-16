package com.jeesuite.admin.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author Doctor
 * @Date 14:12 2022/5/27
 * @Description 撤补单VO
 **/
@Data
@Builder
@ToString
public class TradeCheBuVo {

    /**
     * @Author Doctor
     * @Date 14:14 2022/5/27
     * @Description 铺的卖单集合
     **/
    private List<TradeAddVo> sellTrades;

    /**
     * @Author Doctor
     * @Date 14:15 2022/5/27
     * @Description 铺的卖方冰山单
     **/
    private TradeAddVo sellTradeFake;
    /**
     * @Author Doctor
     * @Date 14:14 2022/5/27
     * @Description 铺的买单集合
     **/
    private List<TradeAddVo> buyTrades;

    /**
     * @Author Doctor
     * @Date 14:15 2022/5/27
     * @Description 铺的买方冰山单
     **/
    private TradeAddVo buyTradeFake;

    /**
     * @Author Doctor
     * @Date 14:15 2022/5/27
     * @Description 当前行情价
     **/
    private String curPrice;
}
