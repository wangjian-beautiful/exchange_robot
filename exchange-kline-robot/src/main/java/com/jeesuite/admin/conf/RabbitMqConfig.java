package com.jeesuite.admin.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


@Configuration
@Slf4j
public class RabbitMqConfig {


    @Value("${rabbitmq_host}")
    private String rabbitmqHost;

    @Value("${rabbitmq_port}")
    private int rabbitmqPort;

    @Value("${rabbitmq_username}")
    private String rabbitmqUsername;

    @Value("${rabbitmq_password}")
    private String rabbitmqPassword;

//    @Value("${kline_queuename_prefix}${server_index:0}")
//    private String klineQueueName;

    //@Value("${connectionTimeout}")
    private int connectionTimeout = 1000;

    //@Value("${virtualHost}")
    private String virtualHost = "/";

    @Bean(name = "connectionFactoryJW")
    @Primary
    public CachingConnectionFactory getCachingConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitmqHost);
        factory.setPort(rabbitmqPort);
        if(rabbitmqPort == 5671){
            try {
                factory.getRabbitConnectionFactory().useSslProtocol();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (KeyManagementException e) {
                throw new RuntimeException(e);
            }
        }
        factory.setUsername(rabbitmqUsername);
        factory.setPassword(rabbitmqPassword);
        factory.setConnectionTimeout(1000);
        factory.setVirtualHost(virtualHost);
        return factory;
    }

    @Bean(name = "jackson2JsonMessageConverter")
    public Jackson2JsonMessageConverter getJackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate getRabbitTemplates(@Qualifier("connectionFactoryJW") ConnectionFactory connectionFactory,
                                            @Qualifier("jackson2JsonMessageConverter") Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        rabbitTemplate.setReplyTimeout(2000);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }
    @Bean
    public RabbitAdmin getRabbitAdmin(@Qualifier("connectionFactoryJW") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

//    @Bean(name = "klineQueue")
//    public Queue getKlineQueue() {
//        String queueName = this.klineQueueName;
//        boolean durable = true;
//        boolean autoDelete = false;
//        boolean exclusive = false;
//        return new Queue(queueName, durable, autoDelete, exclusive);
//    }

    @Bean("klineFanoutExchange")
    FanoutExchange klineFanoutExchange() {
        return new FanoutExchange("kline.fanout.exchange");
    }

//    @Bean
//    Binding bindingFanout(@Qualifier("klineQueue") Queue queue, @Qualifier("klineFanoutExchange") FanoutExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange);
//    }
}
