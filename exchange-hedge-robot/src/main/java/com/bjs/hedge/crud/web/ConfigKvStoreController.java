package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.ConfigKvStore;
import com.bjs.hedge.crud.service.ConfigKvStoreService;


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
@RequestMapping("/config/kv/store")
public class ConfigKvStoreController extends AbstractController<ConfigKvStore> {
    @Resource
    private ConfigKvStoreService configKvStoreService;

    private final static Logger logger = LoggerFactory.getLogger(ConfigKvStoreController.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

