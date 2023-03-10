package com.jeesuite.admin;

import com.jeesuite.springboot.starter.mybatis.EnableJeesuiteMybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.Formatter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Controller
@MapperScan(basePackages = "com.jeesuite.admin.dao.mapper")
@ComponentScan(value = {"com.jeesuite.admin.*"})
@EnableJeesuiteMybatis
@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@EnableJpaAuditing
public class KlineRobotApplication { 

    public static void main(String[] args) {
        SpringApplication.run(KlineRobotApplication.class, args);
    }

    @RequestMapping("/")
    public String login() {
        return "login";
    }

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(100);
        executor.setThreadNamePrefix("Scheduler-");
        executor.setAwaitTerminationSeconds(60);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }


    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public String print(LocalDateTime localDateTime, Locale locale) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return formatter.format(localDateTime);
            }

            @Override
            public LocalDateTime parse(String text, Locale locale) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        //1.??????CORS????????????
        CorsConfiguration config = new CorsConfiguration();
        //?????????????????????
        config.addAllowedOrigin("*");
        //????????????Cookie??????
        config.setAllowCredentials(true);
        //?????????????????????(????????????)
        config.addAllowedMethod("*");
        //?????????????????????(????????????)
        config.addAllowedHeader("*");
        //????????????????????????????????????????????????????????????????????????????????????
//        config.addExposedHeader("*");

        //2.??????????????????

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.????????????CorsFilter.
        return new CorsFilter(configSource);
    }
}
