package com.zyh.wanandroid.bean;

import java.util.ArrayList;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class ResultBean<T> {
    public static final int SUCCESS = 0;
    private T data;
    private int errorCode;
    private String errorMsg;

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
