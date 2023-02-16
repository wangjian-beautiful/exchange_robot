package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.HedgeOrigin;
import com.bjs.hedge.crud.service.HedgeOriginService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author AUTHOR
 * @date  2022/10/19
 */
@RestController
@RequestMapping("/hedge/origin")
public class HedgeOriginController extends AbstractController<HedgeOrigin> {
    @Resource
    private HedgeOriginService hedgeOriginService;

    private final static Logger logger = LoggerFactory.getLogger(HedgeOriginController.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

