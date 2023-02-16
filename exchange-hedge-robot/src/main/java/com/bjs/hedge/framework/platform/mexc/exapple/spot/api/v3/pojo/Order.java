package com.bjs.hedge.framework.platform.mexc.exapple.spot.api.v3.pojo;

import com.bjs.hedge.common.enums.HedgeSideTypes;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Order {
    private String symbol;
    private String orderId;
    private Long orderListId;
    private String clientOrderId;
    private String price;
    private String origQty;
    private String executedQty;
    private String cummulativeQuoteQty;
    private String status;
    private String timeInForce;
    private String type;
    private String side;
    private String stopPrice;
    private String icebergQty;
    private Long time;
    private Long updateTime;
    private Boolean isWorking;
    private String origQuoteOrderQty;

    public String  generaSmsContext(){
        String side = HedgeSideTypes.BUY.equals(this.side)?"买入":"卖出";
        String template="对冲订单：%s\n订单方向:%s\n下单价格:%s\n下单数量：%s\n已成交%s\n累计成交金额%s\n请关注";
        return String.format(template,orderId,side,price,origQty,executedQty,cummulativeQuoteQty);
    }
}
