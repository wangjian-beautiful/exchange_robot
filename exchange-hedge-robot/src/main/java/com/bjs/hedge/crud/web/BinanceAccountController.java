package com.bjs.hedge.crud.web;

import com.alibaba.fastjson.JSON;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.framework.platform.binance.BinanceTradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Api(tags = "币安账号接口")
@RequestMapping("binance")
public class BinanceAccountController {

    @Autowired
    BinanceTradeService binanceTradeService;

    @RequestMapping("account")
    @ApiOperation(value = "获取账号信息")
    public JSON account(){
        return binanceTradeService.account();
    }

    @RequestMapping("myTrades")
    @ApiOperation("获取成交历史")
    public JSON myTrades(@ApiParam("币对") String symbol , @ApiParam("订单ID") String orderId){
        ExHedgeOrder hedgeOrder = new ExHedgeOrder();
        hedgeOrder.setHedgeOrderSymbol(symbol);
        hedgeOrder.setHedgeOrderId(orderId);
        return binanceTradeService.myTrades(hedgeOrder);
    }

    @RequestMapping("getOrder")
    @ApiOperation("")
    public JSON getOrder(@ApiParam("币对") String symbol , @ApiParam("订单ID") String orderId){
        ExHedgeOrder hedgeOrder = new ExHedgeOrder();
        hedgeOrder.setHedgeOrderSymbol(symbol);
        hedgeOrder.setHedgeOrderId(orderId);
        return binanceTradeService.getOrder(hedgeOrder,null);
    }
}
