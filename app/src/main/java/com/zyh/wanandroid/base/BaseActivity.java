package com.zyh.wanandroid.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.widgets.MultiModeView;

import java.util.List;

/**
 * @author zyh
 * @date 2019/1/16
 */
@SuppressLint("Registered")
public abstract class BaseActivity<T> extends AppCompatActivity implements IBaseView<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

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
        hideLoading();
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
