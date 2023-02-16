package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.crud.service.HedgeRobotConfigService;


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
@RequestMapping("/hedge/robot/config")
public class HedgeRobotConfigController extends AbstractController<HedgeRobotConfig> {
    @Resource
    private HedgeRobotConfigService hedgeRobotConfigService;

    private final static Logger logger = LoggerFactory.getLogger(HedgeRobotConfigController.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

