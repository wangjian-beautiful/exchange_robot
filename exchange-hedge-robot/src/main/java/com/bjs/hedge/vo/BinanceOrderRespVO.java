package com.bjs.hedge.vo;

import com.bjs.hedge.common.enums.HedgeSideTypes;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 币安订单返回VO
 */
@Data
public class BinanceOrderRespVO implements Serializable {

    //交易对
    private String   symbol;

    //系统的订单ID
    private String   orderId;

    //OCO订单的ID，不然就是-1
    private String   orderListId;

    //客户自己设置的ID
    private String   clientOrderId;

    //订单价格
    private BigDecimal price;

    //用户设置的原始订单数量
    private BigDecimal   origQty;

    //交易的订单数量
    private BigDecimal   executedQty;

    //累计交易的金额
    private BigDecimal   cummulativeQuoteQty;

    //订单状态
    private String status;

    //订单的时效方式
    private String timeInForce;

    //订单类型， 比如市价单，现价单等
    private String type;

    //订单方向，买还是卖
    private HedgeSideTypes side;

    //止损价格
    private BigDecimal   stopPrice;

    //冰山数量
    private BigDecimal   icebergQty;

    //订单时间
    private Long   time;

    //最后更新时间
    private Long   updateTime;

    //订单是否出现在orderbook中
    private String   isWorking;

    //原始的交易金额
    private BigDecimal   origQuoteOrderQty;

    //交易的时间戳
    private String   transactTime;

    //下单填了参数才会返回
    private String   strategyId;

    //下单填了参数才会返回
    private String   strategyType;

    //订单中交易的信息
    private String   fills;

   public String  generaSmsContext(){
       String side = HedgeSideTypes.BUY.equals(this.side)?"买入":"卖出";
       String template="对冲订单：%s\n订单方向:%s\n下单价格:%f\n下单数量：%f\n已成交%f\n累计成交金额%f\n请关注";
       return String.format(template,orderId,side,price,origQty,executedQty,cummulativeQuoteQty);
   }

}
