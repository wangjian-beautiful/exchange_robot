package com.ruoyi.hedge.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hedge.mapper.HedgeFailMapper;
import com.ruoyi.hedge.domain.HedgeFail;
import com.ruoyi.hedge.service.IHedgeFailService;
import com.ruoyi.common.core.text.Convert;

/**
 * 对冲失败Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
@Service
public class HedgeFailServiceImpl implements IHedgeFailService 
{
    @Autowired
    private HedgeFailMapper hedgeFailMapper;

    /**
     * 查询对冲失败
     * 
     * @param id 对冲失败主键
     * @return 对冲失败
     */
    @Override
    public HedgeFail selectHedgeFailById(Long id)
    {
        return hedgeFailMapper.selectHedgeFailById(id);
    }

    /**
     * 查询对冲失败列表
     * 
     * @param hedgeFail 对冲失败
     * @return 对冲失败
     */
    @Override
    public List<HedgeFail> selectHedgeFailList(HedgeFail hedgeFail)
    {
        return hedgeFailMapper.selectHedgeFailList(hedgeFail);
    }

    /**
     * 新增对冲失败
     * 
     * @param hedgeFail 对冲失败
     * @return 结果
     */
    @Override
    public int insertHedgeFail(HedgeFail hedgeFail)
    {
        hedgeFail.setCreateTime(DateUtils.getNowDate());
        return hedgeFailMapper.insertHedgeFail(hedgeFail);
    }

    /**
     * 修改对冲失败
     * 
     * @param hedgeFail 对冲失败
     * @return 结果
     */
    @Override
    public int updateHedgeFail(HedgeFail hedgeFail)
    {
        return hedgeFailMapper.updateHedgeFail(hedgeFail);
    }

    /**
     * 批量删除对冲失败
     * 
     * @param ids 需要删除的对冲失败主键
     * @return 结果
     */
    @Override
    public int deleteHedgeFailByIds(String ids)
    {
        return hedgeFailMapper.deleteHedgeFailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除对冲失败信息
     * 
     * @param id 对冲失败主键
     * @return 结果
     */
    @Override
    public int deleteHedgeFailById(Long id)
    {
        return hedgeFailMapper.deleteHedgeFailById(id);
    }
}
