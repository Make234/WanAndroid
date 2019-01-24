package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.ProjectTitle;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.bean.ResultListBean;
import com.zyh.wanandroid.http.HttpHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 88421876
 * @date 2019/1/22
 */
public class ProjectViewModel extends BaseViewModel<ProjectTitle> {

    public void getProjectTitle() {
        HttpHelper.getInstance().getProjectTitle(new Observer<ResultListBean<ProjectTitle>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultListBean<ProjectTitle> registerLoginResultBean) {
                hideLoading();
                if (registerLoginResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    ProjectViewModel.this.onSuccess(registerLoginResultBean.getData());
                } else {
                    ProjectViewModel.this.onError(registerLoginResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ProjectViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
            }
        });
    }

}
