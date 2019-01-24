package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.Navigation;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.bean.ResultListBean;
import com.zyh.wanandroid.http.HttpHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 88421876
 * @date 2019/1/17
 */
public class NavigationViewModel extends BaseViewModel<Navigation> {

    public void getKnowLedge() {
        HttpHelper.getInstance().getNavigation(new Observer<ResultListBean<Navigation>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultListBean<Navigation> navigationResultListBean) {
                if (navigationResultListBean.getErrorCode() == ResultBean.SUCCESS) {
                    NavigationViewModel.this.onSuccess(navigationResultListBean.getData());
                } else {
                    NavigationViewModel.this.onError(navigationResultListBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                NavigationViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                NavigationViewModel.this.complete();
            }
        });
    }
}
