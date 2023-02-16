package com.bjs.hedge.crud.web;
import com.bjs.hedge.crud.AbstractController;
import com.bjs.hedge.crud.model.Account;
import com.bjs.hedge.crud.service.AccountService;


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
@RequestMapping("/account")
public class AccountController extends AbstractController<Account> {
    @Resource
    private AccountService accountService;

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Override
    protected Logger logger() {
        return logger;
    }
}

