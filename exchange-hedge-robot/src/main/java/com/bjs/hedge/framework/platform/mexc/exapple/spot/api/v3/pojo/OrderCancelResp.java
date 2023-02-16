package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderCancelResp {
    private String symbol;
    private String origClientOrderId;
    private String orderId;
    private Long orderListId;
    private String price;
    private String origQty;
    private String type;
    private String side;
}
