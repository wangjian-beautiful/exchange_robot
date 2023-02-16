package com.bjs.hedge.crud.web;

import com.alibaba.fastjson.JSON;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.framework.platform.binance.BinanceTradeService;
import com.bjs.hedge.framework.platform.mexc.MexcTradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Api(tags = "抹茶账号接口")
@RequestMapping("mexc")
@Slf4j
public class MexcAccountController {

    @Autowired
    MexcTradeService  mexcTradeService;

    @RequestMapping("account")
    @ApiOperation(value = "获取账号信息")
    public JSON account(){
        JSON json = null;
        try {
            log.info("MexcAccountController account");
            System.out.println("MexcAccountController account");
            json =  mexcTradeService.account();
        }catch (Exception e){
            e.printStackTrace();
            log.error("MexcAccountController account",e);
            throw e;
        }
        return json;
    }

    @RequestMapping("myTrades")
    @ApiOperation("获取成交历史")
    public JSON myTrades(@ApiParam("币对") String symbol , @ApiParam("订单ID") String orderId){
        ExHedgeOrder hedgeOrder = new ExHedgeOrder();
        hedgeOrder.setHedgeOrderSymbol(symbol);
        hedgeOrder.setHedgeOrderId(orderId);
        return mexcTradeService.myTrades(hedgeOrder);
    }
}
