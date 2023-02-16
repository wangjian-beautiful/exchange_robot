package test;


import com.alibaba.fastjson.JSON;
import com.bjs.hedge.HedgeRobotApplication;
import com.bjs.hedge.config.mq.RabbitMqConfig;
import com.bjs.hedge.crud.dao.exchange.CommonMapper;
import com.bjs.hedge.vo.ExHedgeMQMessageBodyVO;
import com.bjs.hedge.vo.ExTrade;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = HedgeRobotApplication.class)
public class RabbitTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    CommonMapper commonMapper;


    @Test
    public void sendHistrys(){
        String exchangeTopicsBjsHedgeOrder = RabbitMqConfig.EXCHANGE_TOPICS_BJS_HEDGE_ORDER;
        String routingKeyHedgeOrder = RabbitMqConfig.ROUTING_KEY_HEDGE_ORDER;
        String tableName = "ex_trade_btcusdt";
        List<ExTrade> allExTrade = commonMapper.getAllExTrade(tableName);
        int count =1;
        for (ExTrade exTrade : allExTrade) {
            if (count >5){
                break;
            }
            ExHedgeMQMessageBodyVO mess = new ExHedgeMQMessageBodyVO();
            mess.setExTradeTable(tableName);
            mess.setExTradeTableUniqueId(exTrade.getId());
            mess.setSymbol("btcusdt");
            CorrelationData correlationData = new CorrelationData();
            rabbitTemplate.convertAndSend(exchangeTopicsBjsHedgeOrder,routingKeyHedgeOrder,mess,correlationData);
            System.out.println(JSON.toJSONString(correlationData,true));
            count++;
        }

    }
}
