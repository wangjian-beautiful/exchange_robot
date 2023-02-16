package com.ruoyi.kline.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import com.ruoyi.kline.domain.TradeSchedule;
import com.ruoyi.kline.service.ITradeScheduleService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * K线机器人配置Controller
 *
 * @author yolo
 * @date 2022-10-09
 */
@Controller
@RequestMapping("/klineConfig/schedule")
public class TradeScheduleController extends BaseController {
    private String prefix = "klineConfig/schedule";

    @Autowired
    private ITradeScheduleService tradeScheduleService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequiresPermissions("klineConfig:schedule:view")
    @GetMapping()
    public String schedule() {
        return prefix + "/schedule";
    }

    /**
     * 查询K线机器人配置列表
     */
    @RequiresPermissions("klineConfig:schedule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TradeSchedule tradeSchedule) {
        startPage();
        List<TradeSchedule> list = tradeScheduleService.selectTradeScheduleList(tradeSchedule);
        return getDataTable(list);
    }

    /**
     * 导出K线机器人配置列表
     */
    @RequiresPermissions("klineConfig:schedule:export")
    @Log(title = "K线机器人配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TradeSchedule tradeSchedule) {
        List<TradeSchedule> list = tradeScheduleService.selectTradeScheduleList(tradeSchedule);
        ExcelUtil<TradeSchedule> util = new ExcelUtil<TradeSchedule>(TradeSchedule.class);
        return util.exportExcel(list, "K线机器人配置数据");
    }

    /**
     * 新增K线机器人配置
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存K线机器人配置
     */
    @RequiresPermissions("klineConfig:schedule:add")
    @Log(title = "K线机器人配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TradeSchedule tradeSchedule) {
        return toAjax(tradeScheduleService.insertTradeSchedule(tradeSchedule));
    }

    /**
     * 修改K线机器人配置
     */
    @RequiresPermissions("klineConfig:schedule:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        TradeSchedule tradeSchedule = tradeScheduleService.selectTradeScheduleById(id);
        mmap.put("tradeSchedule", tradeSchedule);
        return prefix + "/edit";
    }

    /**
     * 修改保存K线机器人配置
     */
    @RequiresPermissions("klineConfig:schedule:edit")
    @Log(title = "K线机器人配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TradeSchedule tradeSchedule) {
        int i = tradeScheduleService.updateTradeSchedule(tradeSchedule);
        if (i > 0) {
            logger.info("start send  exchange kline.fanout.exchange {}",tradeSchedule.getCurrency());
            rabbitTemplate.convertAndSend("kline.fanout.exchange", "", tradeSchedule.getCurrency());
            logger.info("end send exchange kline.fanout.exchange {}",tradeSchedule.getCurrency());
        }
        return toAjax(tradeScheduleService.updateTradeSchedule(tradeSchedule));
    }

    /**
     * 删除K线机器人配置
     */
    @RequiresPermissions("klineConfig:schedule:remove")
    @Log(title = "K线机器人配置", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(tradeScheduleService.deleteTradeScheduleByIds(ids));
    }
}
