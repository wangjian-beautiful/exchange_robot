package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.HedgeFail;
import com.bjs.hedge.crud.service.HedgeFailService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author AUTHOR
 * @date  2022/09/22
 */
@RestController
@RequestMapping("/hedge/fail")
public class HedgeFailController extends AbstractController<HedgeFail> {
    @Resource
    private HedgeFailService hedgeFailService;

    private final static Logger logger = LoggerFactory.getLogger(HedgeFailController.class);

    @Override
    protected Logger logger() {
        return logger;
    }


}

