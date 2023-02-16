package com.ruoyi.kline.mapper;

import java.util.List;
import com.ruoyi.kline.domain.TradeSchedule;

/**
 * K线机器人配置Mapper接口
 * 
 * @author yolo
 * @date 2022-10-09
 */
public interface TradeScheduleMapper 
{
    /**
     * 查询K线机器人配置
     * 
     * @param id K线机器人配置主键
     * @return K线机器人配置
     */
    public TradeSchedule selectTradeScheduleById(Long id);

    /**
     * 查询K线机器人配置列表
     * 
     * @param tradeSchedule K线机器人配置
     * @return K线机器人配置集合
     */
    public List<TradeSchedule> selectTradeScheduleList(TradeSchedule tradeSchedule);

    /**
     * 新增K线机器人配置
     * 
     * @param tradeSchedule K线机器人配置
     * @return 结果
     */
    public int insertTradeSchedule(TradeSchedule tradeSchedule);

    /**
     * 修改K线机器人配置
     * 
     * @param tradeSchedule K线机器人配置
     * @return 结果
     */
    public int updateTradeSchedule(TradeSchedule tradeSchedule);

    /**
     * 删除K线机器人配置
     * 
     * @param id K线机器人配置主键
     * @return 结果
     */
    public int deleteTradeScheduleById(Long id);

    /**
     * 批量删除K线机器人配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTradeScheduleByIds(String[] ids);
}
