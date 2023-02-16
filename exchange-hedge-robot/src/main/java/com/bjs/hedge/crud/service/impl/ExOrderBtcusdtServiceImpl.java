package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.crud.dao.exchange.ExOrderBtcusdtMapper;
import com.bjs.hedge.crud.model.ExOrderBtcusdt;
import com.bjs.hedge.crud.service.ExOrderBtcusdtService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author AUTHOR
 * @date  2022/09/07
 */
@Service
public class ExOrderBtcusdtServiceImpl extends AbstractService<ExOrderBtcusdt> implements ExOrderBtcusdtService {
    @Resource
    private ExOrderBtcusdtMapper exOrderBtcusdtMapper;

}
