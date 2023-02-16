package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Depth {

    private Long lastUpdateId;
    private String[][] bids;
    private String[][] asks;
}
