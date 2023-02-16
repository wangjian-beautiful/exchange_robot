package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v2.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class WithdrawApply {

    private String currency;
    private String wallet_type;
    private String amount;
    private String chain;
    private String address;
    private String remark;
}
