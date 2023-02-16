package com.jeesuite.admin.task;

import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.dao.repository.TradeScheduleRepository;
import com.jeesuite.admin.model.OrderBean;
import com.jeesuite.admin.service.ServiceUtils;
import com.jeesuite.admin.service.TaskTradeService;
import com.jeesuite.admin.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jeesuite.admin.util.Constants;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AutoAnchorTask {
    @Autowired
    TradeScheduleRepository tradeScheduleRepository;

    @Autowired
    TaskTradeService tradeService;

    /**
     * 10秒查看看一次有没有拉盘
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 10000)
    private void checkLaPan() {
        if (Constants.MAP_TRADESCHEDULE != null && Constants.MAP_TRADESCHEDULE.size() > 0) {

            for (String key : Constants.MAP_TRADESCHEDULE.keySet()) {
                TradeScheduleEntity tradeSchedule = Constants.MAP_TRADESCHEDULE.get(key);
                String status = tradeSchedule.getStatus();
                if (("1".equalsIgnoreCase(status)) && ("2".equals(tradeSchedule.getFollowType()))) {//启用中，并且是波动率跟随机器人

                    BigDecimal[] anchorPrices = Constants.MAP_TRADESCHEDULE_PRICE_ANCHOR.get(tradeSchedule.getCurrency());
                    if(anchorPrices == null){
                        tradeService.setAnchorPrice(tradeSchedule);
                        log.info("{}  1 锚点为空，设置锚点", tradeSchedule.getCurrency());
                    }else{
                        BigDecimal jwxAnchorPrc = anchorPrices[0];
                        BigDecimal trailAnchorPrc = anchorPrices[1];
                        if((jwxAnchorPrc == null || jwxAnchorPrc.compareTo(BigDecimal.ZERO) <= 0 || jwxAnchorPrc.compareTo(new BigDecimal(0.0000001)) <= 0) ||
                                (trailAnchorPrc == null || trailAnchorPrc.compareTo(BigDecimal.ZERO) <= 0 || trailAnchorPrc.compareTo(new BigDecimal(0.0000001)) <= 0)){
                            tradeService.setAnchorPrice(tradeSchedule);
                            log.info("{}  2 锚点为空，设置锚点", tradeSchedule.getCurrency());
                        }
                    }
//                    if(tradeService.isLapan(tradeSchedule)){//是否准备拉盘
//                        log.info("{} 拉盘，锚点10秒钟重设锚点", tradeSchedule.getCurrency());
//                        tradeService.setAnchorPrice(tradeSchedule);
//                    }else{
//                        log.info("{} 没拉盘，锚点不动", tradeSchedule.getCurrency());
//                    }
                }
            }
        }
    }

    //1小时重设锚点
//    @Scheduled(initialDelay = 1000, fixedDelay = 1000*60*60)
    private void resetAnchorPrice() {
        if (Constants.MAP_TRADESCHEDULE != null && Constants.MAP_TRADESCHEDULE.size() > 0) {
            for (String key : Constants.MAP_TRADESCHEDULE.keySet()) {
                TradeScheduleEntity tradeSchedule = Constants.MAP_TRADESCHEDULE.get(key);
                String status = tradeSchedule.getStatus();
                if (("1".equalsIgnoreCase(status)) && ("2".equals(tradeSchedule.getFollowType()))) {//启用中，并且是波动率跟随机器人
                    tradeService.setAnchorPrice(tradeSchedule);
                    log.info("{}  resetAnchorPrice 重新设置锚点", tradeSchedule.getCurrency());
                }
            }
        }
    }
}
