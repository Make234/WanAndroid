package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.http.HttpHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zyh
 * @date 2019/1/22
 */
public class DrawerViewModel extends BaseViewModel<ResultBean> {

    public void loginOut() {
        HttpHelper.getInstance().loginOut(new Observer<ResultBean>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean resultBean) {
                DrawerViewModel.this.hideLoading();
                if (resultBean.getErrorCode() == ResultBean.SUCCESS) {
                    DrawerViewModel.this.onSuccess(resultBean);
                } else {
                    DrawerViewModel.this.onError(resultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                DrawerViewModel.this.onError(e.getMessage());
                DrawerViewModel.this.hideLoading();
            }

            @Override
            public void onComplete() {
                d.dispose();
                DrawerViewModel.this.hideLoading();
            }
        });
    }
}
