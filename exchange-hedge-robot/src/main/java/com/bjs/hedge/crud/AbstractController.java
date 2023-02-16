package com.bjs.hedge.crud;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;

/**
 * 基于通用Service接口的实现 Controller
 *
 * @author wb-wj434262
 */
public abstract class AbstractController<T> {

    @Autowired
    protected Service<T> service;
    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    /**
     * 注入子类logger
     *
     * @return
     */
    protected abstract Logger logger();

    /**
     * 获取泛型class
     */
    public AbstractController() {
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
        modelClass = (Class<T>)pt.getActualTypeArguments()[0];
    }

    @RequestMapping(RequestMappingPath.ADD)
    public Result add(T model) {
        if (Objects.isNull(model)) {
            logger().error("{}\tadd\t 请求对象为空", this.getClass().getName());
            return ResultGenerator.genFailResult("请求对象为空");
        }
        service.save(model);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(RequestMappingPath.DELETE)
    public Result delete(@RequestParam("id")Object id) {
        service.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(RequestMappingPath.UPDATE)
    public Result update(T model) {
        service.update(model);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping(RequestMappingPath.DETAIL)
    public Result detail(@RequestParam("id") Object id) {
        T tripOdpsOssJob = service.findById(id);
        return ResultGenerator.genSuccessResult(tripOdpsOssJob);
    }

    @RequestMapping(RequestMappingPath.LIST)
    public Result list(@RequestParam(defaultValue = "0") Integer currentPage, @RequestParam(defaultValue = "20") Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<T> list = service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        JSONObject content = new JSONObject();
        content.put("data", pageInfo.getList());
        content.put("currentPage", pageInfo.getPageNum());
        content.put("totalCount", pageInfo.getTotal());
        return ResultGenerator.genSuccessResult(content);
    }

    public static class RequestMappingPath {
        /**
         * CRUD 请求path
         */
        public final static String ADD = "/add";
        public final static String DELETE = "/delete";
        public final static String UPDATE = "/update";
        public final static String DETAIL = "/detail";
        public final static String LIST = "/list";
    }
}

