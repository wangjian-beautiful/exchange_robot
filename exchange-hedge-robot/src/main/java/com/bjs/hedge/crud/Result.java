package com.bjs.hedge.crud;

import com.alibaba.fastjson.JSON;

/**
 * 乐高统一API响应结果封装
 * @author wb-wj434262
 */
public class Result<T> {
    private int code;
    private String errorMsg;
    private Boolean success;
    private T content;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }


    public Result setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }


    public Result setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getContent() {
        return content;
    }

    public Result setContent(T content) {
        this.content = content;
        return this;
    }



    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
