package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.crud.dao.robot.HedgeFailMapper;
import com.bjs.hedge.crud.model.HedgeFail;
import com.bjs.hedge.crud.service.HedgeFailService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author AUTHOR
 * @date  2022/09/22
 */
@Service
public class HedgeFailServiceImpl extends AbstractService<HedgeFail> implements HedgeFailService {
    @Resource
    private HedgeFailMapper hedgeFailMapper;

}
