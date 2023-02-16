package com.ruoyi.hedge.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hedge.domain.ExHedgeTrade;
import com.ruoyi.hedge.service.IExHedgeTradeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 成交明细Controller
 * 
 * @author yolo
 * @date 2022-10-13
 */
@Controller
@RequestMapping("/hedge/trade")
public class ExHedgeTradeController extends BaseController
{
    private String prefix = "hedge/trade";

    @Autowired
    private IExHedgeTradeService exHedgeTradeService;

    @RequiresPermissions("hedge:trade:view")
    @GetMapping()
    public String trade()
    {
        return prefix + "/trade";
    }

    /**
     * 查询成交明细列表
     */
    @RequiresPermissions("hedge:trade:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExHedgeTrade exHedgeTrade)
    {
        startPage();
        List<ExHedgeTrade> list = exHedgeTradeService.selectExHedgeTradeList(exHedgeTrade);
        return getDataTable(list);
    }

    /**
     * 导出成交明细列表
     */
    @RequiresPermissions("hedge:trade:export")
    @Log(title = "成交明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExHedgeTrade exHedgeTrade)
    {
        List<ExHedgeTrade> list = exHedgeTradeService.selectExHedgeTradeList(exHedgeTrade);
        ExcelUtil<ExHedgeTrade> util = new ExcelUtil<ExHedgeTrade>(ExHedgeTrade.class);
        return util.exportExcel(list, "成交明细数据");
    }

    /**
     * 新增成交明细
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存成交明细
     */
    @RequiresPermissions("hedge:trade:add")
    @Log(title = "成交明细", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExHedgeTrade exHedgeTrade)
    {
        return toAjax(exHedgeTradeService.insertExHedgeTrade(exHedgeTrade));
    }

    /**
     * 修改成交明细
     */
    @RequiresPermissions("hedge:trade:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        ExHedgeTrade exHedgeTrade = exHedgeTradeService.selectExHedgeTradeById(id);
        mmap.put("exHedgeTrade", exHedgeTrade);
        return prefix + "/edit";
    }

    /**
     * 修改保存成交明细
     */
    @RequiresPermissions("hedge:trade:edit")
    @Log(title = "成交明细", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExHedgeTrade exHedgeTrade)
    {
        return toAjax(exHedgeTradeService.updateExHedgeTrade(exHedgeTrade));
    }

    /**
     * 删除成交明细
     */
    @RequiresPermissions("hedge:trade:remove")
    @Log(title = "成交明细", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(exHedgeTradeService.deleteExHedgeTradeByIds(ids));
    }
}
