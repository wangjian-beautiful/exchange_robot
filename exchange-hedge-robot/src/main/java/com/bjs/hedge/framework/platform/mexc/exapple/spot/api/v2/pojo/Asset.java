package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v2.pojo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class Asset {

    private BigDecimal frozen;
    private BigDecimal available;
}
