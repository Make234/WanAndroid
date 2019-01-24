package com.zyh.wanandroid.base;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author zyh
 */
public abstract class BaseViewModel<T> implements IBaseViewModel, IBaseView<T> {
    private WeakReference<IBaseView> mVWeakReference;

    @Override
    public void attachView(IBaseView view) {
        mVWeakReference = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (mVWeakReference != null) {
            mVWeakReference.clear();
            mVWeakReference = null;
        }
    }

    @Override
    public IBaseView getView() {
        if (mVWeakReference != null) {
            return mVWeakReference.get();
        }
        return null;
    }

    @Override
    public void onError(String msg) {
        if (isNotEmpty()) {
            getView().onError(msg);
        }
    }

    @Override
    public void complete() {
        if (isNotEmpty()) {
            getView().complete();
        }
    }

    @Override
    public void showLoading() {
        if (isNotEmpty()) {
            getView().showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (isNotEmpty()) {
            getView().hideLoading();
        }
    }

    private boolean isNotEmpty() {
        return getView() != null;
    }

    @Override
    public void onSuccess(T data) {
        if (isNotEmpty()) {
            getView().onSuccess(data);
        }
    }

    @Override
    public void onSuccess(T data, int type) {
        if (isNotEmpty()) {
            getView().onSuccess(data,type);
        }
    }

    @Override
    public void onSuccess(List<T> data, int type) {
        if (isNotEmpty()) {
            getView().onSuccess(data,type);
        }
    }

    @Override
    public void onSuccess(List<T> data) {
        if (isNotEmpty()) {
            getView().onSuccess(data);
        }
    }
}
