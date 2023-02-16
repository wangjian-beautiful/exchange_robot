package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bjs.hedge.HedgeRobotApplication;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.framework.platform.binance.BinanceTradeService;
import com.bjs.hedge.vo.BinanceOrderRespVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = HedgeRobotApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BinanceServiceTest {

    @Autowired
    BinanceTradeService binanceTradeService;

    /**
     *        {
     * 		"symbol":"BTCUSDT",
     * 		"cummulativeQuoteQty":"19.14031000",
     * 		"side":"BUY",
     * 		"orderListId":-1,
     * 		"executedQty":"0.00100000",
     * 		"orderId":5982879,
     * 		"origQty":"0.00100000",
     * 		"clientOrderId":"4ra1KE00K1VCy4JPrJSSae",
     * 		"updateTime":1663842004322,
     * 		"type":"LIMIT",
     * 		"icebergQty":"0.00000000",
     * 		"stopPrice":"0.00000000",
     * 		"price":"30530.00000000",
     * 		"origQuoteOrderQty":"0.00000000",
     * 		"time":1663842004322,
     * 		"timeInForce":"GTC",
     * 		"isWorking":true,
     * 		"status":"FILLED"
     *    }
     */

    @Test
    public void testMyTrade(){
        //卖出全部订单
        ExHedgeOrder exHedgeOrder = new ExHedgeOrder();
        exHedgeOrder.setHedgeOrderSymbol("btcusdt".toUpperCase());
        JSON orders = binanceTradeService.getOrders(exHedgeOrder, null);
        System.out.println(JSONObject.toJSONString(orders,true));
        List<BinanceOrderRespVO> list = JSONArray.parseArray(orders.toJSONString(), BinanceOrderRespVO.class);
        list.forEach(vo->{
           if(vo.getSide().equals(HedgeSideTypes.BUY)){
               ExHedgeOrder order = new ExHedgeOrder();
               order.setHedgeOrderSymbol(vo.getSymbol());
               order.setHedgePrice(vo.getPrice());
               order.setHedgeVolume(vo.getExecutedQty());
               order.setHedgeSide(HedgeSideTypes.SELL.name());
               JSON json = binanceTradeService.newOrder(order, null);
               System.out.println("下单"+((json == null) ?"失败":"成功"));
           }
        });
    }


    @Test
    public void testGetOrder(){
        //卖出全部订单
        ExHedgeOrder exHedgeOrder = new ExHedgeOrder();
        exHedgeOrder.setHedgeOrderSymbol("btcusdt".toUpperCase());
        exHedgeOrder.setHedgeOrderId(String.valueOf(6384482L));
        JSON order = binanceTradeService.getOrder(exHedgeOrder, null);

        System.out.println(JSON.toJSONString(order,true));
    }

}
