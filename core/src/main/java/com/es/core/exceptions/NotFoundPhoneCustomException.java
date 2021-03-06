package com.es.core.exceptions;

public class NotFoundPhoneCustomException extends RuntimeException {

    private String errCode;

    private String errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public NotFoundPhoneCustomException() {
        this.errCode = "404";
        this.errMsg = "Not found such phone";
    }
}
