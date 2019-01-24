package com.zyh.wanandroid.base;

import java.util.List;

/**
 * @author zyh
 * @date 2019/1/17
 */
public interface IBaseView<T> {

    int DATA1 = 1;
    int DATA2 = 2;
    /**
     * 成功回调
     *
     * @param data 请求数据
     */
    void onSuccess(T data);

    /**
     * 成功回调
     *
     * @param data 请求数据
     */
    void onSuccess(List<T> data);

    /**
     * 成功回调
     *
     * @param type 数据类型
     * @param data 请求数据
     */
    void onSuccess(T data, int type);

    /**
     * 成功回调
     *
     * @param type 数据类型
     * @param data 请求数据
     */
    void onSuccess(List<T> data, int type);

    /**
     * 失败回调
     *
     * @param msg 报错信息
     */
    void onError(String msg);

    /**
     * 完成
     */
    void complete();

    /**
     * 显示loading框
     */
    void showLoading();

    /**
     * 隐藏loading框
     */
    void hideLoading();


}
