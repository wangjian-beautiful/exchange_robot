package com.bjs.hedge.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


/**
 *
 * @description RabbitMq配置
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    public static final String QUEUE_HEDGE_ORDER = "QUEUE_HEDGE_ORDER";
    public static final String ROUTING_KEY_HEDGE_ORDER = "bjs.hedge.order#";
    public static final String EXCHANGE_TOPICS_BJS_HEDGE_ORDER = "EXCHANGE_TOPICS_BJS_HEDGE_ORDER";

    @Bean
    public MessageConverter getJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 声明交换机
    @Bean(EXCHANGE_TOPICS_BJS_HEDGE_ORDER)
    public Exchange EXCHANGE_TOPICS_BJS_HEDGE_ORDER() {
        //DURABLE(true) 持久化，消息队列重启后交换机仍然存在
        return ExchangeBuilder.directExchange(EXCHANGE_TOPICS_BJS_HEDGE_ORDER)
                .durable(true).build();
    }

    // 声明队列
    @Bean(QUEUE_HEDGE_ORDER)
    public Queue QUEUE_HEDGE_ORDER(){
        Queue queue = new Queue(QUEUE_HEDGE_ORDER);
        return queue;
    }

    // 队列绑定交换机
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(QUEUE_HEDGE_ORDER) Queue queue,
                                              @Qualifier(EXCHANGE_TOPICS_BJS_HEDGE_ORDER) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_HEDGE_ORDER).noargs();
    }


}
