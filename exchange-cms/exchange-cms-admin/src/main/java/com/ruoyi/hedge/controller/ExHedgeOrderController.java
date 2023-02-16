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
import com.ruoyi.hedge.domain.ExHedgeOrder;
import com.ruoyi.hedge.service.IExHedgeOrderService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 对冲订单Controller
 * 
 * @author yolo
 * @date 2022-10-11
 */
@Controller
@RequestMapping("/hedge/order")
public class ExHedgeOrderController extends BaseController
{
    private String prefix = "hedge/order";

    @Autowired
    private IExHedgeOrderService exHedgeOrderService;

    @RequiresPermissions("hedge:order:view")
    @GetMapping()
    public String order()
    {
        return prefix + "/order";
    }

    /**
     * 查询对冲订单列表
     */
    @RequiresPermissions("hedge:order:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExHedgeOrder exHedgeOrder)
    {
        startPage();
        List<ExHedgeOrder> list = exHedgeOrderService.selectExHedgeOrderList(exHedgeOrder);
        return getDataTable(list);
    }

    /**
     * 导出对冲订单列表
     */
    @RequiresPermissions("hedge:order:export")
    @Log(title = "对冲订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExHedgeOrder exHedgeOrder)
    {
        List<ExHedgeOrder> list = exHedgeOrderService.selectExHedgeOrderList(exHedgeOrder);
        ExcelUtil<ExHedgeOrder> util = new ExcelUtil<ExHedgeOrder>(ExHedgeOrder.class);
        return util.exportExcel(list, "对冲订单数据");
    }

    /**
     * 新增对冲订单
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存对冲订单
     */
    @RequiresPermissions("hedge:order:add")
    @Log(title = "对冲订单", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExHedgeOrder exHedgeOrder)
    {
        return toAjax(exHedgeOrderService.insertExHedgeOrder(exHedgeOrder));
    }

    /**
     * 修改对冲订单
     */
    @RequiresPermissions("hedge:order:edit")
    @GetMapping("/edit/{hedgeOrderId}")
    public String edit(@PathVariable("hedgeOrderId") String hedgeOrderId, ModelMap mmap)
    {
        ExHedgeOrder exHedgeOrder = exHedgeOrderService.selectExHedgeOrderByHedgeOrderId(hedgeOrderId);
        mmap.put("exHedgeOrder", exHedgeOrder);
        return prefix + "/edit";
    }

    /**
     * 修改保存对冲订单
     */
    @RequiresPermissions("hedge:order:edit")
    @Log(title = "对冲订单", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExHedgeOrder exHedgeOrder)
    {
        return toAjax(exHedgeOrderService.updateExHedgeOrder(exHedgeOrder));
    }

    /**
     * 删除对冲订单
     */
    @RequiresPermissions("hedge:order:remove")
    @Log(title = "对冲订单", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(exHedgeOrderService.deleteExHedgeOrderByHedgeOrderIds(ids));
    }
}
