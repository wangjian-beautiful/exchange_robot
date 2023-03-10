package com.bjs.hedge.framework;

import com.alibaba.fastjson.JSON;
import com.bjs.hedge.common.Constants;
import com.bjs.hedge.common.enums.HedgeSideTypes;
import com.bjs.hedge.common.enums.HedgeHandlerResp;
import com.bjs.hedge.common.enums.HedgeOriginStatus;
import com.bjs.hedge.common.enums.HedgeRobotStatus;
import com.bjs.hedge.config.mq.RabbitMqConfig;
import com.bjs.hedge.crud.dao.exchange.CommonMapper;
import com.bjs.hedge.crud.model.*;
import com.bjs.hedge.crud.service.*;
import com.bjs.hedge.util.CommonUtil;
import com.bjs.hedge.util.MathUtils;
import com.bjs.hedge.util.StringUtils;
import com.bjs.hedge.vo.ExHedgeMQMessageBodyVO;
import com.bjs.hedge.vo.ExTrade;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.bjs.hedge.common.Constants.REDIS_TRY_LOCK_LEASE_SECONDS;
import static com.bjs.hedge.common.Constants.REDIS_TRY_LOCK_WAIT_SECONDS;

@Component
@Slf4j
public class WorkStarter implements RabbitTemplate.ReturnCallback {

    @Autowired
    HedgeService hedgeService;

    @Autowired
    JedisCluster jedisCluster;
    @Autowired
    HedgeRobotConfigService hedgeRobotConfigService;

    @Autowired
    CommonMapper commonMapper;

    @Autowired
    HedgeFailService hedgeFailService;
    @Autowired
    Redisson redisson;

    @Autowired
    SmsService smsService;

    @Autowired
    HedgeOriginService hedgeOriginService;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_HEDGE_ORDER)
    public void receiverMqHedgeMessage(ExHedgeMQMessageBodyVO messageBodyVO, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("??????????????? QUEUE_HEDGE_ORDER:{}", messageBodyVO);
            HedgeRobotConfig robotConfig = hedgeRobotConfigService.selectConfigBySymbolBjs(messageBodyVO.getSymbol());
            //???????????????????????????
            if (!checkRobotConfig(robotConfig)) {
                channel.basicNack(deliveryTag, false, false);
                log.info("??????????????? ???????????? table???{} id:{}",messageBodyVO.getExTradeTable(),messageBodyVO.getExTradeTableUniqueId());
                return;
            }
            HedgeOrigin origin = saveHedgeOriginAndCalcBetting(messageBodyVO, robotConfig);
            //??????????????????
            if (!checkHedgeOut(robotConfig,origin)) {
                channel.basicNack(deliveryTag, false, false);
                log.info("????????????????????? ???????????? table???{} id:{}",messageBodyVO.getExTradeTable(),messageBodyVO.getExTradeTableUniqueId());
                return;
            }
            //????????????
            HedgeHandlerResp hedgeHandlerResp = hedgeService.hedgeHandler(origin, robotConfig);
            //??????????????????
            switch (hedgeHandlerResp) {
                case HANDLED: //???????????????
                    log.info("hedgeHandlerResp  HedgeHandler is exists");
                case NOT_LOCK://???????????????
                case FILTER_RULES://?????????????????????
                     channel.basicAck(deliveryTag, false);
                    break;
                case SUCCESS: //????????????
                    channel.basicAck(deliveryTag, false);
                    updateHedgeOriginStatus(origin);
                    break;
                case FAIL:
                    handleErrorConsumer(messageBodyVO, channel, message, null);
                    break;
                case NEW_ORDER_SUCCESS_SAVE_FAIL://??????????????????????????????????????????
                    log.error("??????????????????????????????????????????");
                    updateHedgeOriginStatus(origin);
                    sendErrorMessage(messageBodyVO, new RuntimeException("??????????????????????????????????????????"), false);
                    channel.basicAck(deliveryTag, false);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            handleErrorConsumer(messageBodyVO, channel, message, e);
        }
    }

    private boolean updateHedgeOriginStatus(HedgeOrigin origin){
        HedgeOrigin hedgeOrigin = new HedgeOrigin();
        hedgeOrigin.setStatus(HedgeOriginStatus.HANDLED.name());
        hedgeOrigin.setId(origin.getId());
        hedgeOrigin.setUpdateTime(new Date());
        return hedgeOriginService.update(hedgeOrigin);
    }


    private HedgeOrigin saveHedgeOriginAndCalcBetting(ExHedgeMQMessageBodyVO messageBodyVO, HedgeRobotConfig hedgeRobotConfig) throws Exception {
        String tableName = messageBodyVO.getExTradeTable();
        Long tableUniqueId = messageBodyVO.getExTradeTableUniqueId();
        RLock rlock = redisson.getLock(CommonUtil.getRedisHedgeOriginLockKey(tableName, tableUniqueId));
        try {
            if(rlock.tryLock(REDIS_TRY_LOCK_WAIT_SECONDS,REDIS_TRY_LOCK_LEASE_SECONDS, TimeUnit.SECONDS)){
                HedgeOrigin param = new HedgeOrigin();
                param.setOriginTable(tableName);
                param.setOriginId(tableUniqueId);
                HedgeOrigin hedgeOriginOld = hedgeOriginService.selectOne(param);
                if(hedgeOriginOld!= null){
                    return  hedgeOriginOld;
                }
                ExTrade exTrade = commonMapper.getExTrade(tableName, tableUniqueId);
                String side = RobotListCache.getUserTrendSide(exTrade);
                HedgeOrigin origin = new HedgeOrigin();
                origin.setSide(side);
                origin.setSymbol(hedgeRobotConfig.getSymbol());
                origin.setOriginTable(tableName);
                origin.setOriginId(tableUniqueId);
                origin.setPrice(exTrade.getPrice());
                origin.setVolume(exTrade.getVolume());
                BigDecimal betting = MathUtils.toScaleNum(exTrade.getVolume(),8);
                origin.setBetting(HedgeSideTypes.BUY.name().equals(side)?betting:MathUtils.sub(BigDecimal.ZERO,betting));
                origin.setStatus(HedgeOriginStatus.NOT_HANDLE.name());
                origin.setCreateTime(new Date());
                origin.setUpdateTime(new Date());
                hedgeOriginService.save(origin);
                return origin;
            }
        }catch (Exception e){
            log.error("saveHedgeOriginAndCalcBetting error",e);
            throw e;
        }finally {
            if(rlock.isLocked()){
                rlock.unlock();
            }
        }
        return null;
    }


    private boolean checkHedgeOut(HedgeRobotConfig robotConfig,HedgeOrigin hedgeOrigin){
        BigDecimal hedgeOut = robotConfig.getHedgeOut();
        if(hedgeOut == null || hedgeOut.equals(BigDecimal.ZERO)){
            return true;
        }
        BigDecimal betting = hedgeOriginService.sumBetting(hedgeOrigin.getSymbol());
        if(betting != null){
            HedgeRobotConfig update = new HedgeRobotConfig();update.setId(robotConfig.getId());
            update.setBetting(betting);
            hedgeRobotConfigService.update(update);
            betting =betting.abs();
        }
        return betting != null && betting.compareTo(hedgeOut) > 0;
    }


    private boolean checkRobotConfig(HedgeRobotConfig hedgeRobotConfig) {
        if (null == hedgeRobotConfig) {
            log.error("hedge robot is not exists");
            return false;
        }
        //???????????????????????????????????????
        HedgeRobotConfig defaultRobotConfig = hedgeRobotConfigService.selectConfigBySymbol(Constants.SYMBOL_UNIFIED_DEFAULT_NAME);
        if (HedgeRobotStatus.STOP.name().equals(defaultRobotConfig.getStatus())) {
            return false;
        }
        //???????????????????????????
        return HedgeRobotStatus.START.name().equals(hedgeRobotConfig.getStatus());
    }

    /**
     * ????????????????????????
     *
     * @param msg
     * @param channel
     * @param message
     * @param e
     * @throws IOException
     */
    public void handleErrorConsumer(ExHedgeMQMessageBodyVO msg, Channel channel, Message message, Exception e) throws IOException {
        log.error("?????????????????? ?????? QUEUE_HEDGE_ORDER???{} ,{}", msg, e);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//      String ip = IpUtils.getLocalHostAddress();
        String tableName = msg.getExTradeTable();
        Long tableUniqueId = msg.getExTradeTableUniqueId();
        String redisKey = Constants.MQ_MESSAGE_UNIQUE_KEY_PREFIX + tableName + tableUniqueId;
        RLock lock = redisson.getLock(redisKey + "_lock");
        try {
            if (lock.tryLock(REDIS_TRY_LOCK_WAIT_SECONDS,REDIS_TRY_LOCK_LEASE_SECONDS, TimeUnit.SECONDS)) {
                Integer errorNum = Integer.valueOf(StringUtils.getString(jedisCluster.get(redisKey), "0"));
                // ???????????????????????? ??????
                if (errorNum >= Constants.MQ_HEDGE_MESSAGE_CONSUM_RETRY - 1) {
                    channel.basicNack(deliveryTag, false, false);
                    jedisCluster.del(redisKey);
                    // ?????????????????? ???????????????
                    sendErrorMessage(msg, e, true);
                    return;
                }
                //??????????????????
                channel.basicNack(deliveryTag, false, true);
                jedisCluster.set(redisKey, String.valueOf(errorNum + 1));
            }
        } catch (Exception ex) {
            log.error("handleErrorConsumer fail: ", ex);
        } finally {
            if(lock.isLocked()){
                lock.unlock();
            }
        }
    }

    void sendErrorMessage(ExHedgeMQMessageBodyVO msg, Exception e, boolean isSendSms) {
        try {
            String symbol = msg.getSymbol();
            String exTradeTable = msg.getExTradeTable();
            Long exTradeTableUniqueId = msg.getExTradeTableUniqueId();
            ExTrade exTrade = commonMapper.getExTrade(exTradeTable, exTradeTableUniqueId);
            if (null == exTrade){
                //??????????????????????????? ????????????????????????
                exTrade = new ExTrade();
            }
            HedgeFail hedgeFail = new HedgeFail();
            hedgeFail.setSymbol(symbol);
            hedgeFail.setPrice(exTrade.getPrice());
            hedgeFail.setVolume(exTrade.getVolume());
            hedgeFail.setMqMessage(JSON.toJSONString(msg));
            hedgeFail.setCreateTime(new Date());
            if (null != e) {
                hedgeFail.setError(e.getMessage() + "\t" + e.toString());
            }
            if (isSendSms) {
                smsService.sendSMs(msg.getSymbol(),generatorSmsContext(exTrade,e));
            }
            hedgeFailService.save(hedgeFail);
        } catch (Exception ex) {
            log.error("WorkStarter sendErrorMessage fail :", ex);
        }
    }


    private String generatorSmsContext(ExTrade exTrade, Exception e) {
        String smsTemplate = "??????????????????????????????%f\n????????????:%f\n????????????:%s";
        return  String.format(smsTemplate, exTrade.getPrice(), exTrade.getVolume(), (e != null) ? e.getMessage() : "");
    }

    /**
     * @return
     * @Description ?????????????????????????????????
     * @Date 2019-05-14 16:22
     * @Param [message, replyCode, replyText, exchange, routingKey]
     **/
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("??????????????????id:{}", message.getMessageProperties().getCorrelationId());
        log.info("???????????? message : ", message);
        log.info("???????????? message : ", replyCode);
        log.info("?????????" + replyText);
        log.info("???????????????????????? exchange : ", exchange);
        log.info("???????????????????????? routing : ", routingKey);
    }


}
