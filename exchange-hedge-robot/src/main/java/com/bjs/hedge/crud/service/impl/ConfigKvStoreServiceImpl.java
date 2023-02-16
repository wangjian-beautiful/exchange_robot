package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.crud.dao.exchange.ConfigKvStoreMapper;
import com.bjs.hedge.crud.model.ConfigKvStore;
import com.bjs.hedge.crud.service.ConfigKvStoreService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author AUTHOR
 * @date  2022/10/12
 */
@Service
public class ConfigKvStoreServiceImpl extends AbstractService<ConfigKvStore> implements ConfigKvStoreService {
    @Resource
    private ConfigKvStoreMapper configKvStoreMapper;

}
