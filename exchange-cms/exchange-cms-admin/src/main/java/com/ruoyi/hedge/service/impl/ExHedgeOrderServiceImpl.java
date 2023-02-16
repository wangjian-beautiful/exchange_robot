package com.ruoyi.hedge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hedge.mapper.ExHedgeOrderMapper;
import com.ruoyi.hedge.domain.ExHedgeOrder;
import com.ruoyi.hedge.service.IExHedgeOrderService;
import com.ruoyi.common.core.text.Convert;

/**
 * 对冲订单Service业务层处理
 * 
 * @author yolo
 * @date 2022-10-11
 */
@Service
public class ExHedgeOrderServiceImpl implements IExHedgeOrderService 
{
    @Autowired
    private ExHedgeOrderMapper exHedgeOrderMapper;

    /**
     * 查询对冲订单
     * 
     * @param hedgeOrderId 对冲订单主键
     * @return 对冲订单
     */
    @Override
    public ExHedgeOrder selectExHedgeOrderByHedgeOrderId(String hedgeOrderId)
    {
        return exHedgeOrderMapper.selectExHedgeOrderByHedgeOrderId(hedgeOrderId);
    }

    /**
     * 查询对冲订单列表
     * 
     * @param exHedgeOrder 对冲订单
     * @return 对冲订单
     */
    @Override
    public List<ExHedgeOrder> selectExHedgeOrderList(ExHedgeOrder exHedgeOrder)
    {
        return exHedgeOrderMapper.selectExHedgeOrderList(exHedgeOrder);
    }

    /**
     * 新增对冲订单
     * 
     * @param exHedgeOrder 对冲订单
     * @return 结果
     */
    @Override
    public int insertExHedgeOrder(ExHedgeOrder exHedgeOrder)
    {
        return exHedgeOrderMapper.insertExHedgeOrder(exHedgeOrder);
    }

    /**
     * 修改对冲订单
     * 
     * @param exHedgeOrder 对冲订单
     * @return 结果
     */
    @Override
    public int updateExHedgeOrder(ExHedgeOrder exHedgeOrder)
    {
        return exHedgeOrderMapper.updateExHedgeOrder(exHedgeOrder);
    }

    /**
     * 批量删除对冲订单
     * 
     * @param hedgeOrderIds 需要删除的对冲订单主键
     * @return 结果
     */
    @Override
    public int deleteExHedgeOrderByHedgeOrderIds(String hedgeOrderIds)
    {
        return exHedgeOrderMapper.deleteExHedgeOrderByHedgeOrderIds(Convert.toStrArray(hedgeOrderIds));
    }

    /**
     * 删除对冲订单信息
     * 
     * @param hedgeOrderId 对冲订单主键
     * @return 结果
     */
    @Override
    public int deleteExHedgeOrderByHedgeOrderId(String hedgeOrderId)
    {
        return exHedgeOrderMapper.deleteExHedgeOrderByHedgeOrderId(hedgeOrderId);
    }
}
