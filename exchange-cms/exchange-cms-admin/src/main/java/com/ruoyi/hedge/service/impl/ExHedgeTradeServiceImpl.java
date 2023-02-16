package com.ruoyi.hedge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hedge.mapper.ExHedgeTradeMapper;
import com.ruoyi.hedge.domain.ExHedgeTrade;
import com.ruoyi.hedge.service.IExHedgeTradeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 成交明细Service业务层处理
 * 
 * @author yolo
 * @date 2022-10-13
 */
@Service
public class ExHedgeTradeServiceImpl implements IExHedgeTradeService 
{
    @Autowired
    private ExHedgeTradeMapper exHedgeTradeMapper;

    /**
     * 查询成交明细
     * 
     * @param id 成交明细主键
     * @return 成交明细
     */
    @Override
    public ExHedgeTrade selectExHedgeTradeById(Long id)
    {
        return exHedgeTradeMapper.selectExHedgeTradeById(id);
    }

    /**
     * 查询成交明细列表
     * 
     * @param exHedgeTrade 成交明细
     * @return 成交明细
     */
    @Override
    public List<ExHedgeTrade> selectExHedgeTradeList(ExHedgeTrade exHedgeTrade)
    {
        return exHedgeTradeMapper.selectExHedgeTradeList(exHedgeTrade);
    }

    /**
     * 新增成交明细
     * 
     * @param exHedgeTrade 成交明细
     * @return 结果
     */
    @Override
    public int insertExHedgeTrade(ExHedgeTrade exHedgeTrade)
    {
        return exHedgeTradeMapper.insertExHedgeTrade(exHedgeTrade);
    }

    /**
     * 修改成交明细
     * 
     * @param exHedgeTrade 成交明细
     * @return 结果
     */
    @Override
    public int updateExHedgeTrade(ExHedgeTrade exHedgeTrade)
    {
        return exHedgeTradeMapper.updateExHedgeTrade(exHedgeTrade);
    }

    /**
     * 批量删除成交明细
     * 
     * @param ids 需要删除的成交明细主键
     * @return 结果
     */
    @Override
    public int deleteExHedgeTradeByIds(String ids)
    {
        return exHedgeTradeMapper.deleteExHedgeTradeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除成交明细信息
     * 
     * @param id 成交明细主键
     * @return 结果
     */
    @Override
    public int deleteExHedgeTradeById(Long id)
    {
        return exHedgeTradeMapper.deleteExHedgeTradeById(id);
    }
}
