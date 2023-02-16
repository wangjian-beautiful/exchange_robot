package com.bjs.hedge.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

/**
 * Spring  异步调度线程池配置
 * @author
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncSchedulingConfig implements AsyncConfigurer, SchedulingConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(AsyncSchedulingConfig.class);

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("AsyncSchedulingConfig-");
        scheduler.setAwaitTerminationSeconds(120);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setErrorHandler(t -> logger.error("AsyncSchedulingConfig Scheduler error", t));
        scheduler.setRejectedExecutionHandler((r, e) -> logger.error("Executor rejected task size {}",e.getQueue().size()));
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler());
    }

    @Override
    public Executor getAsyncExecutor() {
        return taskScheduler();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, params) -> {
            logger.error("异步任务异常：方法：{} 参数：{}", method.getName(), JSON.toJSONString(params));
            logger.error(throwable.getMessage(), throwable);
        };
    }

}
