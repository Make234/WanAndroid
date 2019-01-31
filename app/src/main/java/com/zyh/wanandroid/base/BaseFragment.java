package com.zyh.wanandroid.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.zyh.wanandroid.utils.NetWorkUtils;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.widgets.MultiModeView;

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
        if (getMultiModeView() != null) {
            if (NetWorkUtils.isNetworkConnected()) {
                getMultiModeView().showError();
            } else {
                getMultiModeView().showNetWork();
            }
        }
        ToastUtils.toastShort(msg);
    }

    @Override
    public void complete() {
        hideLoading();
    }

    @Override
    public void showLoading() {
        if (getMultiModeView() != null) {
            getMultiModeView().showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (getMultiModeView() != null) {
            getMultiModeView().setVisibility(View.GONE);
        }
    }

    @Override
    public void showNetWorkError() {
        if (getMultiModeView() != null) {
            getMultiModeView().showNetWork();
        }
    }

    @Override
    public void showError() {
        if (getMultiModeView() != null) {
            getMultiModeView().showError();
        }
    }

    @Override
    public void showEmpty() {
        if (getMultiModeView() != null) {
            getMultiModeView().showEmpty();
        }
    }

    @Override
    public MultiModeView getMultiModeView() {
        return null;
    }
}
