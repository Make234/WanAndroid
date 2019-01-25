package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.RegisterLogin;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.http.HttpHelper;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zyh
 * @date 2019/1/22
 */
public class RegisterLoginViewModel extends BaseViewModel<RegisterLogin> {

    public void login(HashMap<String, String> map) {
        HttpHelper.getInstance().login(new Observer<ResultBean<RegisterLogin>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean<RegisterLogin> registerLoginResultBean) {
                RegisterLoginViewModel.this.hideLoading();
                if (registerLoginResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    RegisterLoginViewModel.this.onSuccess(registerLoginResultBean.getData());
                } else {
                    RegisterLoginViewModel.this.onError(registerLoginResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                RegisterLoginViewModel.this.onError(e.getMessage());
                RegisterLoginViewModel.this.hideLoading();
            }

            @Override
            public void onComplete() {
                d.dispose();
                RegisterLoginViewModel.this.hideLoading();
            }
        }, map);
    }


    public void register(HashMap<String, String> map) {
        HttpHelper.getInstance().register(new Observer<ResultBean<RegisterLogin>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean<RegisterLogin> registerLoginResultBean) {
                RegisterLoginViewModel.this.hideLoading();
                if (registerLoginResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    RegisterLoginViewModel.this.onSuccess(registerLoginResultBean.getData());
                } else {
                    RegisterLoginViewModel.this.onError(registerLoginResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                RegisterLoginViewModel.this.onError(e.getMessage());
                RegisterLoginViewModel.this.hideLoading();
            }

            @Override
            public void onComplete() {
                d.dispose();
                RegisterLoginViewModel.this.complete();
                RegisterLoginViewModel.this.hideLoading();
            }
        }, map);
    }


}
