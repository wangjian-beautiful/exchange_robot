package test;

import com.bjs.hedge.HedgeRobotApplication;
import com.bjs.hedge.common.enums.HedgePlaceOrderTypes;
import com.bjs.hedge.common.enums.TimeInForceStatus;
import com.bjs.hedge.common.enums.TradePlatform;
import com.bjs.hedge.crud.dao.exchange.CommonMapper;
import com.bjs.hedge.crud.model.ExHedgeOrder;
import com.bjs.hedge.crud.model.HedgeOrigin;
import com.bjs.hedge.crud.model.HedgeRobotConfig;
import com.bjs.hedge.crud.service.ExHedgeOrderService;
import com.bjs.hedge.crud.service.HedgeOriginService;
import com.bjs.hedge.crud.service.HedgeRobotConfigService;
import com.bjs.hedge.util.MathUtils;
import com.bjs.hedge.vo.ExTrade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest(classes = HedgeRobotApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MapperTest {
    @Autowired
    CommonMapper commonMapper;

    @Autowired
    HedgeRobotConfigService hedgeRobotConfigService;

    @Autowired
    ExHedgeOrderService exHedgeOrderService;

    @Autowired
    HedgeOriginService hedgeOriginService;


    @Test
    public void testHed(){
        HedgeOrigin origin = new HedgeOrigin();
        origin.setSide("ss");
        hedgeOriginService.save(origin);

    }
    @Test
    public void testMapper(){
        commonMapper.getConfigSymbol().forEach((symbol->{
            HedgeRobotConfig hedgeRobotConfig = hedgeRobotConfigService.selectConfigBySymbolBjs(symbol);
            if(hedgeRobotConfig == null){
                HedgeRobotConfig save = new HedgeRobotConfig();
                save.setSymbol(symbol);
                save.setSymbolBjs(symbol);
                save.setStatus("STOP");
                save.setHedgeOut(BigDecimal.ZERO);
                save.setTradePlatform(TradePlatform.BINANCE.name);
                save.setBinanceOrderType(HedgePlaceOrderTypes.LIMIT.name());
                save.setTimeInForceStatus(TimeInForceStatus.GTC.name());
                boolean save1 = hedgeRobotConfigService.save(save);
                System.out.println(save+"\t"+save.getSymbolBjs());
            }
        }));
    }
    @Test
    public void caul(){
        exHedgeOrderService.findAll().forEach(exHedgeOrder -> {
            String opponentOrderTable = exHedgeOrder.getOpponentOrderTable();
            Long opponentOrderTableUniqueId = exHedgeOrder.getOpponentOrderTableUniqueId();
            ExTrade exTrade = commonMapper.getExTrade(opponentOrderTable, opponentOrderTableUniqueId);
            ExHedgeOrder order = new ExHedgeOrder();
            order.setHedgeOrderId(exHedgeOrder.getHedgeOrderId());
            order.setOpponentDealMoney(MathUtils.mulScale12(exTrade.getVolume(),exTrade.getPrice()));
            boolean update = exHedgeOrderService.update(order);
            System.out.println(order.getHedgeOrderId()+":   "+update);
        });
    }
    @Test
    public void testMapper2(){
        String tableName = "ex_trade_btcusdt";
        Long id = 84353L;
        for (int i = 0; i < 1000; i++) {
            ExTrade exTrade = commonMapper.getExTrade(tableName, id);
            System.out.println(exTrade);
        }
    }
}
