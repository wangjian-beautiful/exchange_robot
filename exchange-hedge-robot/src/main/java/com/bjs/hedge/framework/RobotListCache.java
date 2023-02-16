package com.bjs.hedge.framework;

import com.bjs.hedge.common.Constants;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.crud.model.ConfigKvStore;
import com.bjs.hedge.crud.service.ConfigKvStoreService;
import com.bjs.hedge.util.spring.SpringUtils;
import com.bjs.hedge.vo.ExTrade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class RobotListCache {

    private static List<String> robotUidList = new ArrayList<>();


    public static List<String> getRobotUidList(){
        if(CollectionUtils.isEmpty(robotUidList)){
            ConfigKvStore configKvStore = new ConfigKvStore();
            configKvStore.setConfigKey(Constants.CONFIG_KV_STORE_ROBOT_WHITE_LIST);
            String value = SpringUtils.getBean(ConfigKvStoreService.class).selectOne(configKvStore).getValue();
            if (StringUtils.isNotBlank(value)){
                robotUidList = Arrays.asList(value.split(","));
            }else{
                log.error("RobotUidList value is null");
            }
        }
        return robotUidList;
    }

    public static String getUserTrendSide(ExTrade exTrade){
        if(exTrade == null){
            log.info("getUserTrendSide exTrade is null");

        }
        if(exTrade.getBidUserId() ==null){
            log.info("getUserTrendSide exTrade.getBidUserId() is null");
        }
        //如果机器人是买单，用户就是卖单
        if(getRobotUidList().contains(String.valueOf(exTrade.getBidUserId()))){
            return HedgeSideTypes.SELL.name();
        }
        return HedgeSideTypes.BUY.name();
    }
}
