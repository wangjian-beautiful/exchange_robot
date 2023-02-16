package com.ruoyi.hedge.service;

import java.util.List;
import com.ruoyi.hedge.domain.HedgeRobotConfig;

/**
 * 对冲机器人配置Service接口
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
public interface IHedgeRobotConfigService 
{
    /**
     * 查询对冲机器人配置
     * 
     * @param id 对冲机器人配置主键
     * @return 对冲机器人配置
     */
    public HedgeRobotConfig selectHedgeRobotConfigById(Long id);

    /**
     * 查询对冲机器人配置列表
     * 
     * @param hedgeRobotConfig 对冲机器人配置
     * @return 对冲机器人配置集合
     */
    public List<HedgeRobotConfig> selectHedgeRobotConfigList(HedgeRobotConfig hedgeRobotConfig);

    /**
     * 新增对冲机器人配置
     * 
     * @param hedgeRobotConfig 对冲机器人配置
     * @return 结果
     */
    public int insertHedgeRobotConfig(HedgeRobotConfig hedgeRobotConfig);

    /**
     * 修改对冲机器人配置
     * 
     * @param hedgeRobotConfig 对冲机器人配置
     * @return 结果
     */
    public int updateHedgeRobotConfig(HedgeRobotConfig hedgeRobotConfig);

    /**
     * 批量删除对冲机器人配置
     * 
     * @param ids 需要删除的对冲机器人配置主键集合
     * @return 结果
     */
    public int deleteHedgeRobotConfigByIds(String ids);

    /**
     * 删除对冲机器人配置信息
     * 
     * @param id 对冲机器人配置主键
     * @return 结果
     */
    public int deleteHedgeRobotConfigById(Long id);
}
