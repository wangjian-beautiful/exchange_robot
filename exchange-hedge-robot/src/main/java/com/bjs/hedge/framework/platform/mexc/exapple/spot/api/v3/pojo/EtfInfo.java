package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EtfInfo {
    private String symbol;
    private String netValue;
    private String feeRate;
    private long timestamp;
}
