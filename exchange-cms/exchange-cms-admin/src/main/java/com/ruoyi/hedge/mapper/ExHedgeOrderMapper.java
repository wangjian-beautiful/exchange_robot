package com.ruoyi.hedge.mapper;

import java.util.List;
import com.ruoyi.hedge.domain.ExHedgeOrder;

/**
 * 对冲订单Mapper接口
 * 
 * @author yolo
 * @date 2022-10-11
 */
public interface ExHedgeOrderMapper 
{
    /**
     * 查询对冲订单
     * 
     * @param hedgeOrderId 对冲订单主键
     * @return 对冲订单
     */
    public ExHedgeOrder selectExHedgeOrderByHedgeOrderId(String hedgeOrderId);

    /**
     * 查询对冲订单列表
     * 
     * @param exHedgeOrder 对冲订单
     * @return 对冲订单集合
     */
    public List<ExHedgeOrder> selectExHedgeOrderList(ExHedgeOrder exHedgeOrder);

    /**
     * 新增对冲订单
     * 
     * @param exHedgeOrder 对冲订单
     * @return 结果
     */
    public int insertExHedgeOrder(ExHedgeOrder exHedgeOrder);

    /**
     * 修改对冲订单
     * 
     * @param exHedgeOrder 对冲订单
     * @return 结果
     */
    public int updateExHedgeOrder(ExHedgeOrder exHedgeOrder);

    /**
     * 删除对冲订单
     * 
     * @param hedgeOrderId 对冲订单主键
     * @return 结果
     */
    public int deleteExHedgeOrderByHedgeOrderId(String hedgeOrderId);

    /**
     * 批量删除对冲订单
     * 
     * @param hedgeOrderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteExHedgeOrderByHedgeOrderIds(String[] hedgeOrderIds);
}
