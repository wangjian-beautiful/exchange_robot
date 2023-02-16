package com.ruoyi.hedge.service;

import java.util.List;
import com.ruoyi.hedge.domain.ExHedgeTrade;

/**
 * 成交明细Service接口
 * 
 * @author yolo
 * @date 2022-10-13
 */
public interface IExHedgeTradeService 
{
    /**
     * 查询成交明细
     * 
     * @param id 成交明细主键
     * @return 成交明细
     */
    public ExHedgeTrade selectExHedgeTradeById(Long id);

    /**
     * 查询成交明细列表
     * 
     * @param exHedgeTrade 成交明细
     * @return 成交明细集合
     */
    public List<ExHedgeTrade> selectExHedgeTradeList(ExHedgeTrade exHedgeTrade);

    /**
     * 新增成交明细
     * 
     * @param exHedgeTrade 成交明细
     * @return 结果
     */
    public int insertExHedgeTrade(ExHedgeTrade exHedgeTrade);

    /**
     * 修改成交明细
     * 
     * @param exHedgeTrade 成交明细
     * @return 结果
     */
    public int updateExHedgeTrade(ExHedgeTrade exHedgeTrade);

    /**
     * 批量删除成交明细
     * 
     * @param ids 需要删除的成交明细主键集合
     * @return 结果
     */
    public int deleteExHedgeTradeByIds(String ids);

    /**
     * 删除成交明细信息
     * 
     * @param id 成交明细主键
     * @return 结果
     */
    public int deleteExHedgeTradeById(Long id);
}
