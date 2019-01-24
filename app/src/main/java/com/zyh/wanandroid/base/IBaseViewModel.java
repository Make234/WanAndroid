package com.zyh.wanandroid.base;

/**
 * 弱引用接口
 * Created by 18044075 on 2018/5/30.
 */
public interface IBaseViewModel<V extends IBaseView> {

    /**
     * 绑定
     */
    void attachView(V view);

    /**
     * 解绑
     */
    void detachView();

    /**
     * 获取view
     *
     * @return V View
     */
    V getView();
}
