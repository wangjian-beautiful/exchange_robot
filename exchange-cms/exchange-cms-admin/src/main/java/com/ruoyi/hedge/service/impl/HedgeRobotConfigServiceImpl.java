package com.ruoyi.hedge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hedge.mapper.HedgeRobotConfigMapper;
import com.ruoyi.hedge.domain.HedgeRobotConfig;
import com.ruoyi.hedge.service.IHedgeRobotConfigService;
import com.ruoyi.common.core.text.Convert;

/**
 * 对冲机器人配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
@Service
public class HedgeRobotConfigServiceImpl implements IHedgeRobotConfigService 
{
    @Autowired
    private HedgeRobotConfigMapper hedgeRobotConfigMapper;

    /**
     * 查询对冲机器人配置
     * 
     * @param id 对冲机器人配置主键
     * @return 对冲机器人配置
     */
    @Override
    public HedgeRobotConfig selectHedgeRobotConfigById(Long id)
    {
        return hedgeRobotConfigMapper.selectHedgeRobotConfigById(id);
    }

    /**
     * 查询对冲机器人配置列表
     * 
     * @param hedgeRobotConfig 对冲机器人配置
     * @return 对冲机器人配置
     */
    @Override
    public List<HedgeRobotConfig> selectHedgeRobotConfigList(HedgeRobotConfig hedgeRobotConfig)
    {
        return hedgeRobotConfigMapper.selectHedgeRobotConfigList(hedgeRobotConfig);
    }

    /**
     * 新增对冲机器人配置
     * 
     * @param hedgeRobotConfig 对冲机器人配置
     * @return 结果
     */
    @Override
    public int insertHedgeRobotConfig(HedgeRobotConfig hedgeRobotConfig)
    {
        return hedgeRobotConfigMapper.insertHedgeRobotConfig(hedgeRobotConfig);
    }

    /**
     * 修改对冲机器人配置
     * 
     * @param hedgeRobotConfig 对冲机器人配置
     * @return 结果
     */
    @Override
    public int updateHedgeRobotConfig(HedgeRobotConfig hedgeRobotConfig)
    {
        return hedgeRobotConfigMapper.updateHedgeRobotConfig(hedgeRobotConfig);
    }

    /**
     * 批量删除对冲机器人配置
     * 
     * @param ids 需要删除的对冲机器人配置主键
     * @return 结果
     */
    @Override
    public int deleteHedgeRobotConfigByIds(String ids)
    {
        return hedgeRobotConfigMapper.deleteHedgeRobotConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除对冲机器人配置信息
     * 
     * @param id 对冲机器人配置主键
     * @return 结果
     */
    @Override
    public int deleteHedgeRobotConfigById(Long id)
    {
        return hedgeRobotConfigMapper.deleteHedgeRobotConfigById(id);
    }
}
