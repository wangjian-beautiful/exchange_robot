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
import com.ruoyi.hedge.domain.HedgeRobotConfig;
import com.ruoyi.hedge.service.IHedgeRobotConfigService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 对冲机器人配置Controller
 * 
 * @author ruoyi
 * @date 2022-10-14
 */
@Controller
@RequestMapping("/hedge/config")
public class HedgeRobotConfigController extends BaseController
{
    private String prefix = "hedge/config";

    @Autowired
    private IHedgeRobotConfigService hedgeRobotConfigService;

    @RequiresPermissions("hedge:config:view")
    @GetMapping()
    public String config()
    {
        return prefix + "/config";
    }

    /**
     * 查询对冲机器人配置列表
     */
    @RequiresPermissions("hedge:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(HedgeRobotConfig hedgeRobotConfig)
    {
        startPage();
        List<HedgeRobotConfig> list = hedgeRobotConfigService.selectHedgeRobotConfigList(hedgeRobotConfig);
        return getDataTable(list);
    }

    /**
     * 导出对冲机器人配置列表
     */
    @RequiresPermissions("hedge:config:export")
    @Log(title = "对冲机器人配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(HedgeRobotConfig hedgeRobotConfig)
    {
        List<HedgeRobotConfig> list = hedgeRobotConfigService.selectHedgeRobotConfigList(hedgeRobotConfig);
        ExcelUtil<HedgeRobotConfig> util = new ExcelUtil<HedgeRobotConfig>(HedgeRobotConfig.class);
        return util.exportExcel(list, "对冲机器人配置数据");
    }

    /**
     * 新增对冲机器人配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存对冲机器人配置
     */
    @RequiresPermissions("hedge:config:add")
    @Log(title = "对冲机器人配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(HedgeRobotConfig hedgeRobotConfig)
    {
        return toAjax(hedgeRobotConfigService.insertHedgeRobotConfig(hedgeRobotConfig));
    }

    /**
     * 修改对冲机器人配置
     */
    @RequiresPermissions("hedge:config:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        HedgeRobotConfig hedgeRobotConfig = hedgeRobotConfigService.selectHedgeRobotConfigById(id);
        mmap.put("hedgeRobotConfig", hedgeRobotConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存对冲机器人配置
     */
    @RequiresPermissions("hedge:config:edit")
    @Log(title = "对冲机器人配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(HedgeRobotConfig hedgeRobotConfig)
    {
        return toAjax(hedgeRobotConfigService.updateHedgeRobotConfig(hedgeRobotConfig));
    }

    /**
     * 删除对冲机器人配置
     */
    @RequiresPermissions("hedge:config:remove")
    @Log(title = "对冲机器人配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(hedgeRobotConfigService.deleteHedgeRobotConfigByIds(ids));
    }
}
