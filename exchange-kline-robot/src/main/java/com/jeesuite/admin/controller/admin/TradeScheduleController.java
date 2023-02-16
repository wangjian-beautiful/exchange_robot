package com.jeesuite.admin.controller.admin;

import com.jeesuite.admin.common.EnumConst;
import com.jeesuite.admin.dao.entity.TradeScheduleEntity;
import com.jeesuite.admin.dao.entity.UserEntity;
import com.jeesuite.admin.dao.mapper.UserEntityMapper;
import com.jeesuite.admin.model.LoginUserInfo;
import com.jeesuite.admin.model.SelectOption;
import com.jeesuite.admin.model.WrapperResponseEntity;
import com.jeesuite.admin.service.TradeScheduleService;
import com.jeesuite.admin.util.Constants;
import com.jeesuite.admin.util.SecurityUtil;
import com.jeesuite.springweb.model.WrapperResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:行情配置
 * author:User
 * date:2019/5/4
 */
@Controller
@RequestMapping("/tradeSchedule")
public class TradeScheduleController {

    @Autowired
    private TradeScheduleService tradeScheduleService;
    @Autowired
    private UserEntityMapper userMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ApiOperation(value = "获取列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<WrapperResponseEntity> getTradeScheduleList() {
        // 获取登录用户信息
        LoginUserInfo loginUserInfo = SecurityUtil.getLoginUserInfo();
        UserEntity userEntity = userMapper.selectByPrimaryKey(loginUserInfo.getId());
        // 获取登录用户配置币种
        String gantEnvs = userEntity.getGantEnvs();
        List<TradeScheduleEntity> list = this.tradeScheduleService.findListInCurrency(gantEnvs, null, null);
        // 获取列表
        return new ResponseEntity<>(new WrapperResponseEntity(list), HttpStatus.OK);
    }

    @ApiOperation(value = "编辑初始化")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<WrapperResponseEntity> edit(@PathVariable("id") Long id) {
        TradeScheduleEntity entity = new TradeScheduleEntity();
        if (id != null && id > 0) {
            entity = this.tradeScheduleService.selectByPrimaryKey(id);
            BigDecimal[] prs = Constants.MAP_MAX_MIN_PRICES.get(entity.getCurrency());
            if (prs != null && prs.length == 2) {
                entity.setMaxPrice(prs[0]);
                entity.setMinPrice(prs[1]);
            }
        }
        return new ResponseEntity<>(new WrapperResponseEntity(entity), HttpStatus.OK);
    }

    @ApiOperation(value = "新增更新")
    @RequestMapping(value = "operate", method = RequestMethod.POST)
    public ResponseEntity<WrapperResponseEntity> operate(@RequestBody TradeScheduleEntity param) {
        if (param.getId() == null) {
            this.tradeScheduleService.addInfo(param);
        } else {
            this.tradeScheduleService.updateInfo(param);
            BigDecimal[] prs = Constants.MAP_MAX_MIN_PRICES.get(param.getCurrency());
            if (prs != null && prs.length == 2) {
                prs[0] = param.getMaxPrice();
                prs[1] = param.getMinPrice();
                Constants.MAP_MAX_MIN_PRICES.put(param.getCurrency(), prs);
            }

//            if("0".equals(param.getStatus()) ){
            if(Constants.MAP_TRADESCHEDULE.containsKey(param.getCurrency() ) ){
                //2022年09月22日16:47:57 因为需要启动多个机器人，所以修改之后，需要通知多个服务
                rabbitTemplate.convertAndSend("kline.fanout.exchange","",param.getCurrency());
                //Constants.MAP_TRADESCHEDULE.get(param.getCurrency() ).stop();
            }
//            }
        }
        return new ResponseEntity<>(new WrapperResponseEntity(param), HttpStatus.OK);
    }


    @ApiOperation(value = "删除")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<WrapperResponseEntity> delete(@PathVariable("id") Integer id) {
        int count = 0;
        if (id != null && id > 0) {

            TradeScheduleEntity tradeScheduleEntity = tradeScheduleService.selectByPrimaryKey(Long.valueOf(id));
            count = this.tradeScheduleService.deleteInfo(id);

            if (count > 0 &&tradeScheduleEntity != null) {
                if(Constants.MAP_TRADESCHEDULE.containsKey(tradeScheduleEntity.getCurrency() ) ){
                    //2022年09月22日16:47:57 因为需要启动多个机器人，所以修改之后，需要通知多个服务
                    rabbitTemplate.convertAndSend("kline.fanout.exchange","",tradeScheduleEntity.getCurrency() );
                    //Constants.MAP_TRADESCHEDULE.get(param.getCurrency() ).stop();
                }
            }
        }
        return new ResponseEntity<>(new WrapperResponseEntity(count > 0), HttpStatus.OK);
    }

    @ApiOperation(value = "获取分类下拉数据")
    @RequestMapping(value = "getSelect", method = RequestMethod.GET)
    @ResponseBody
    public WrapperResponse<Map<String, Object>> getCategorySelect(@RequestParam("id") Long id) {
        // 获取状态集合
        List<SelectOption> statusList = new ArrayList<>();
        statusList.add(new SelectOption(EnumConst.ROBOT_SATATUS_NO_ORDER.VALUE, EnumConst.ROBOT_SATATUS_NO_ORDER.NAME));
        statusList.add(new SelectOption(EnumConst.ROBOT_SATATUS_ORDER.VALUE, EnumConst.ROBOT_SATATUS_ORDER.NAME));
        // 跟随类型集合
        List<SelectOption> followTypeList = new ArrayList<>();
        followTypeList.add(new SelectOption(EnumConst.ROBOT_FOLLOW_TYPE_PRICE.VALUE, EnumConst.ROBOT_FOLLOW_TYPE_PRICE.NAME));
        followTypeList.add(new SelectOption(EnumConst.ROBOT_FOLLOW_TYPE_WAVE.VALUE, EnumConst.ROBOT_FOLLOW_TYPE_WAVE.NAME));
        // 跟随交易所集合
        List<SelectOption> followMarketList = new ArrayList<>();
        followMarketList.add(new SelectOption(EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.VALUE, EnumConst.ROBOT_FOLLOW_MARKET_BINANCE.NAME));
        followMarketList.add(new SelectOption(EnumConst.ROBOT_FOLLOW_MARKET_MEXC.VALUE, EnumConst.ROBOT_FOLLOW_MARKET_MEXC.NAME));
//        followMarketList.add(new SelectOption(EnumConst.ROBOT_FOLLOW_MARKET_COMBINATION.VALUE, EnumConst.ROBOT_FOLLOW_MARKET_COMBINATION.NAME));
        // 机器人类型集合
        List<SelectOption> typeList = new ArrayList<>();
        typeList.add(new SelectOption(EnumConst.ROBOT_KLINE.VALUE, EnumConst.ROBOT_KLINE.NAME));
        typeList.add(new SelectOption(EnumConst.ROBOT_TRADE.VALUE, EnumConst.ROBOT_TRADE.NAME));

        Map<String, Object> map = new HashMap<>();
        if (id != null && id > 0) {
            TradeScheduleEntity entity = this.tradeScheduleService.selectByPrimaryKey(id);
            if (entity == null) {
                return new WrapperResponse<>(map);
            }

            map.put("startTime", entity.getStartTime());
//            map.put("priceInterval", entity.getPriceInterval());
            String status = entity.getStatus();
            for (SelectOption option : statusList) {
                if (option.getValue().equals(status)) {
                    option.setSelected(true);
                    break;
                }
            }
            String followType = entity.getFollowType();
            for (SelectOption option : followTypeList) {
                if (option.getValue().equals(followType)) {
                    option.setSelected(true);
                    break;
                }
            }
            String followMarket = entity.getFollowMarket();
            for (SelectOption option : followMarketList) {
                if (option.getValue().equals(followMarket)) {
                    option.setSelected(true);
                    break;
                }
            }
            String type = entity.getType();
            for (SelectOption option : typeList) {
                if (option.getValue().equals(type)) {
                    option.setSelected(true);
                    break;
                }
            }
        }
        map.put("status", statusList);
        map.put("followType", followTypeList);
        map.put("followMarket", followMarketList);
        map.put("type", typeList);

        return new WrapperResponse(map);
    }
}
