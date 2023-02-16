package com.jeesuite.admin.mq.listener;

import com.jeesuite.admin.util.Constants;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: Doctor
 * @Date: 2022/9/22 16:44
 * @Description:
 */
@Component
public class KlineFanoutCustomer {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,  //创建临时队列
                    exchange = @Exchange(value = "kline.fanout.exchange",type = "fanout")  //绑定的交换机
            )
    })
    public void receive(String message){
        System.out.println("message="+message);
        if(Constants.MAP_TRADESCHEDULE.containsKey(message) ){
            System.out.println(String.format("%s exists, stop",message));
            Constants.MAP_TRADESCHEDULE.get(message ).stop();
        }
    }
}
