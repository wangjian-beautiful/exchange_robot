package com.bjs.hedge.crud;

/**
 * 响应结果生成工具
 * @author wb-wj434262
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setErrorMsg(DEFAULT_SUCCESS_MESSAGE)
                .setSuccess(true);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return genSuccessResult().setContent(data);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setErrorMsg(message)
                .setSuccess(false);
    }
}
