package test;

import com.alibaba.fastjson.JSON;
import com.bjs.hedge.HedgeRobotApplication;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.common.enums.TradePlatform;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.HedgeOrigin;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.crud.service.HedgeOriginService;
import com.bjs.hedge.crud.service.HedgeRobotConfigService;
import com.bjs.hedge.framework.HedgeService;
import com.bjs.hedge.framework.HedgeTradeRules;
import com.bjs.hedge.framework.platform.binance.BinanceTradeService;
import com.bjs.hedge.util.MathUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = HedgeRobotApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HedgeRulesTest {

    @Autowired
    HedgeOriginService hedgeOriginService;
    @Autowired
    HedgeRobotConfigService hedgeRobotConfigService;

    @Autowired
    HedgeService hedgeService;

    @Autowired
    BinanceTradeService binanceTradeService;
    @Test
    public void testRules(){
        Map<String,HedgeRobotConfig> configs = new HashMap<>();
        List<HedgeOrigin> select = hedgeOriginService.select(null);
        select.forEach(origin -> {
            HedgeRobotConfig config;
            if(configs.containsKey(origin.getSymbol())){
                config = configs.get(origin.getSymbol());
            }else{
                config = hedgeRobotConfigService.selectConfigBySymbol(origin.getSymbol());
                configs.put(origin.getSymbol() ,config);
            }
//            ExHedgeOrder exHedgeOrder = hedgeService.generatorExHedgeOrder(origin, config);
            ExHedgeOrder exHedgeOrder = new ExHedgeOrder();
            exHedgeOrder.setHedgeVolume(MathUtils.toScaleNum(origin.getVolume(),8));
            exHedgeOrder.setHedgePrice(MathUtils.toScaleNum(origin.getPrice(),8));
            exHedgeOrder.setHedgeSide(HedgeSideTypes.BUY.name());
            exHedgeOrder.setHedgeOrderSymbol(config.getSymbol());
            boolean b = HedgeTradeRules.checkRules(exHedgeOrder, config);
            if(b){
                System.out.println(b);
//                JSON json = binanceTradeService.newOrder(exHedgeOrder, config);
//                System.out.println(json.toString());
            }

        });
    }



}
