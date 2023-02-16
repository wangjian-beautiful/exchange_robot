package com.ruoyi.hedge.service;

import java.util.List;
import com.ruoyi.hedge.domain.HedgeFail;

/**
 * 对冲失败Service接口
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
public interface IHedgeFailService 
{
    /**
     * 查询对冲失败
     * 
     * @param id 对冲失败主键
     * @return 对冲失败
     */
    public HedgeFail selectHedgeFailById(Long id);

    /**
     * 查询对冲失败列表
     * 
     * @param hedgeFail 对冲失败
     * @return 对冲失败集合
     */
    public List<HedgeFail> selectHedgeFailList(HedgeFail hedgeFail);

    /**
     * 新增对冲失败
     * 
     * @param hedgeFail 对冲失败
     * @return 结果
     */
    public int insertHedgeFail(HedgeFail hedgeFail);

    /**
     * 修改对冲失败
     * 
     * @param hedgeFail 对冲失败
     * @return 结果
     */
    public int updateHedgeFail(HedgeFail hedgeFail);

    /**
     * 批量删除对冲失败
     * 
     * @param ids 需要删除的对冲失败主键集合
     * @return 结果
     */
    public int deleteHedgeFailByIds(String ids);

    /**
     * 删除对冲失败信息
     * 
     * @param id 对冲失败主键
     * @return 结果
     */
    public int deleteHedgeFailById(Long id);
}
