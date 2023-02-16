package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.ExOrderBtcusdt;
import com.bjs.hedge.crud.service.ExOrderBtcusdtService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author AUTHOR
 * @date  2022/09/07
 */
@RestController
@RequestMapping("/ex/order/btcusdt")
public class ExOrderBtcusdtController extends AbstractController<ExOrderBtcusdt> {
    @Resource
    private ExOrderBtcusdtService exOrderBtcusdtService;

    private final static Logger logger = LoggerFactory.getLogger(ExOrderBtcusdtController.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

