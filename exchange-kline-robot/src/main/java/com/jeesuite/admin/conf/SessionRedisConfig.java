package com.jeesuite.admin.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

/**
 * @Auther: Doctor
 * @Date: 2022/9/22 18:20
 * @Description:
 */
@Configuration
public class SessionRedisConfig {
    @Bean
    public RedisHttpSessionConfiguration redisHttpSessionConfiguration(){
        RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
        redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(7200);
        return redisHttpSessionConfiguration;
    }
}
