package com.ruoyi.kline.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.kline.mapper.TradeScheduleMapper;
import com.ruoyi.kline.domain.TradeSchedule;
import com.ruoyi.kline.service.ITradeScheduleService;
import com.ruoyi.common.core.text.Convert;

/**
 * K线机器人配置Service业务层处理
 * 
 * @author yolo
 * @date 2022-10-09
 */
@Service
public class TradeScheduleServiceImpl implements ITradeScheduleService 
{
    @Autowired
    private TradeScheduleMapper tradeScheduleMapper;

    /**
     * 查询K线机器人配置
     * 
     * @param id K线机器人配置主键
     * @return K线机器人配置
     */
    @Override
    public TradeSchedule selectTradeScheduleById(Long id)
    {
        return tradeScheduleMapper.selectTradeScheduleById(id);
    }

    /**
     * 查询K线机器人配置列表
     * 
     * @param tradeSchedule K线机器人配置
     * @return K线机器人配置
     */
    @Override
    public List<TradeSchedule> selectTradeScheduleList(TradeSchedule tradeSchedule)
    {
        return tradeScheduleMapper.selectTradeScheduleList(tradeSchedule);
    }

    /**
     * 新增K线机器人配置
     * 
     * @param tradeSchedule K线机器人配置
     * @return 结果
     */
    @Override
    public int insertTradeSchedule(TradeSchedule tradeSchedule)
    {
        tradeSchedule.setCreateTime(DateUtils.getNowDate());
        return tradeScheduleMapper.insertTradeSchedule(tradeSchedule);
    }

    /**
     * 修改K线机器人配置
     * 
     * @param tradeSchedule K线机器人配置
     * @return 结果
     */
    @Override
    public int updateTradeSchedule(TradeSchedule tradeSchedule)
    {
        tradeSchedule.setUpdateTime(DateUtils.getNowDate());
        return tradeScheduleMapper.updateTradeSchedule(tradeSchedule);
    }

    /**
     * 批量删除K线机器人配置
     * 
     * @param ids 需要删除的K线机器人配置主键
     * @return 结果
     */
    @Override
    public int deleteTradeScheduleByIds(String ids)
    {
        return tradeScheduleMapper.deleteTradeScheduleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除K线机器人配置信息
     * 
     * @param id K线机器人配置主键
     * @return 结果
     */
    @Override
    public int deleteTradeScheduleById(Long id)
    {
        return tradeScheduleMapper.deleteTradeScheduleById(id);
    }
}
