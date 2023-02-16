package com.jeesuite.admin.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: Doctor
 * @Date: 2022/3/15 18:31
 * @Description: 币币交易下单vo
 */
@Data
@Builder
public class TradeAddVo {

    private String price;

    private String num;

    /**
     * @Author Doctor 
     * @Date 18:34 2022/3/15
     * @Description 类型 1-买 0-卖
     * @return 
     **/
    private Integer type;

    /**
     * @Author Doctor
     * @Date 13:44 2022/5/27
     * @Description 是否为冰山单 1-冰山 0-非冰山
     **/
    private Integer fake;
}
