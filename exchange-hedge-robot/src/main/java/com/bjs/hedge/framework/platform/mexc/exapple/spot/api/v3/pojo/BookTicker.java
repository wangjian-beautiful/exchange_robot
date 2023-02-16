package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookTicker {
    private String symbol;
    private String bidPrice;
    private String bidQty;
    private String askPrice;
    private String askQty;
}
