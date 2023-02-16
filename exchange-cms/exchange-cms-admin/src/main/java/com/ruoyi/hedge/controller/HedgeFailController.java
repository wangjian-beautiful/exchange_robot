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
import com.ruoyi.hedge.domain.HedgeFail;
import com.ruoyi.hedge.service.IHedgeFailService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 对冲失败Controller
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
@Controller
@RequestMapping("/hedge/fail")
public class HedgeFailController extends BaseController
{
    private String prefix = "hedge/fail";

    @Autowired
    private IHedgeFailService hedgeFailService;

    @RequiresPermissions("hedge:fail:view")
    @GetMapping()
    public String fail()
    {
        return prefix + "/fail";
    }

    /**
     * 查询对冲失败列表
     */
    @RequiresPermissions("hedge:fail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HedgeFail hedgeFail)
    {
        startPage();
        List<HedgeFail> list = hedgeFailService.selectHedgeFailList(hedgeFail);
        return getDataTable(list);
    }

    /**
     * 导出对冲失败列表
     */
    @RequiresPermissions("hedge:fail:export")
    @Log(title = "对冲失败", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HedgeFail hedgeFail)
    {
        List<HedgeFail> list = hedgeFailService.selectHedgeFailList(hedgeFail);
        ExcelUtil<HedgeFail> util = new ExcelUtil<HedgeFail>(HedgeFail.class);
        return util.exportExcel(list, "对冲失败数据");
    }

    /**
     * 新增对冲失败
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存对冲失败
     */
    @RequiresPermissions("hedge:fail:add")
    @Log(title = "对冲失败", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HedgeFail hedgeFail)
    {
        return toAjax(hedgeFailService.insertHedgeFail(hedgeFail));
    }

    /**
     * 修改对冲失败
     */
    @RequiresPermissions("hedge:fail:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        HedgeFail hedgeFail = hedgeFailService.selectHedgeFailById(id);
        mmap.put("hedgeFail", hedgeFail);
        return prefix + "/edit";
    }

    /**
     * 修改保存对冲失败
     */
    @RequiresPermissions("hedge:fail:edit")
    @Log(title = "对冲失败", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HedgeFail hedgeFail)
    {
        return toAjax(hedgeFailService.updateHedgeFail(hedgeFail));
    }

    /**
     * 删除对冲失败
     */
    @RequiresPermissions("hedge:fail:remove")
    @Log(title = "对冲失败", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(hedgeFailService.deleteHedgeFailByIds(ids));
    }
}
