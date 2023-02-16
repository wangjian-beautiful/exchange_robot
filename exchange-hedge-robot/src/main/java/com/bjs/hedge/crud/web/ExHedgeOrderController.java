package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.service.ExHedgeOrderService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author AUTHOR
 * @date  2022/09/29
 */
@RestController
@RequestMapping("/ex/hedge/order")
public class ExHedgeOrderController extends AbstractController<ExHedgeOrder> {
    @Resource
    private ExHedgeOrderService exHedgeOrderService;

    private final static Logger logger = LoggerFactory.getLogger(ExHedgeOrderController.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

