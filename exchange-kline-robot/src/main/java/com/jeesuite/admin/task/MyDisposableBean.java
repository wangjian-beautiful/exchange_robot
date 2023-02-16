package com.jeesuite.admin.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Auther: Doctor
 * @Date: 2022/8/8 11:20
 * @Description:
 */
@Component
public class MyDisposableBean implements DisposableBean {
    @Resource
    private AutoTradeTask task;
    @Override
    public void destroy() throws Exception {
        task.destroy();
    }
}
