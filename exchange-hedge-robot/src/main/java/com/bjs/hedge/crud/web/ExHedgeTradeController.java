package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.ExHedgeTrade;
import com.bjs.hedge.crud.service.ExHedgeTradeService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author AUTHOR
 * @date  2022/10/12
 */
@RestController
@RequestMapping("/ex/hedge/trade")
public class ExHedgeTradeController extends AbstractController<ExHedgeTrade> {
    @Resource
    private ExHedgeTradeService exHedgeTradeService;

    private final static Logger logger = LoggerFactory.getLogger(ExHedgeTradeController.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

