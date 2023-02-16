package com.bjs.hedge;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;


@ComponentScan(value = {"com.bjs.hedge.*"})
@SpringBootApplication
@EnableWebMvc
public class HedgeRobotApplication {
    public static void main(String[] args) {

        SpringApplication.run(HedgeRobotApplication.class, args);


    }

}
