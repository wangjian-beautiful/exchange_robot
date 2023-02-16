package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepositAddress {
    private String coin;
    private String network;
    private String address;
    private String tag;
}

