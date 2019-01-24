package com.zyh.wanandroid.bean;

import java.util.ArrayList;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class ResultListBean<T> {
    public static final int SUCCESS = 0;
    private ArrayList<T> data;
    private int errorCode;
    private String errorMsg;

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public ArrayList<T> getData() {
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
