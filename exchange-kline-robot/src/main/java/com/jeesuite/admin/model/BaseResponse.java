package com.jeesuite.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponse<T> {

    private boolean success = true;

    private String errorCode;

    private String errorMsg;

    private T data;

    public static BaseResponse Success() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        return baseResponse;
    }


    public static BaseResponse error(String errorCode, String errorMsg) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(false);
        baseResponse.setErrorCode(errorCode);
        baseResponse.setErrorMsg(errorMsg);
        return baseResponse;
    }
}
