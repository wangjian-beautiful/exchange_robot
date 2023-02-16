package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v2.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WithdrawAddress {

    private String coinName;
    private String chain;
    private String address;
    private String addressTab;
    private String note;
}
