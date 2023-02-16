package com.bjs.hedge.crud.service.impl;

import com.bjs.hedge.crud.dao.exchange.AccountMapper;
import com.bjs.hedge.crud.model.Account;
import com.bjs.hedge.crud.service.AccountService;
import com.bjs.hedge.crud.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author AUTHOR
 * @date  2022/10/12
 */
@Service
public class AccountServiceImpl extends AbstractService<Account> implements AccountService {
    @Resource
    private AccountMapper accountMapper;

}
