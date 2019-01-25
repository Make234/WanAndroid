package com.zyh.wanandroid.base;

import android.support.v4.app.Fragment;

import com.zyh.wanandroid.utils.ToastUtils;

import java.util.List;

/**
 * @author zyh
 * @date 2019/1/23
 */
public class BaseFragment<T> extends Fragment implements IBaseView<T> {

    @Override
    public void onSuccess(T data) {
        hideLoading();
    }

    @Override
    public void onSuccess(List<T> data) {
        hideLoading();
    }

    @Override
    public void onSuccess(T data, int type) {
        hideLoading();
    }

    @Override
    public void onSuccess(List<T> data, int type) {
        hideLoading();
    }

    @Override
    public void onError(String msg) {
        if (msg == null) {
            msg = "未知错误";
        }
        ToastUtils.toastShort(msg);
    }

    @Override
    public void complete() {
        hideLoading();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
