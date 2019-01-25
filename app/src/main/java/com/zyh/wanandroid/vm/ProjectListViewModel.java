package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.Project;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.http.HttpHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zyh
 * @date 2019/1/22
 */
public class ProjectListViewModel extends BaseViewModel {

    public void getProject(int page, int id) {
        HttpHelper.getInstance().getProject(new Observer<ResultBean<Project>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean<Project> registerLoginResultBean) {
                ProjectListViewModel.this.hideLoading();
                if (registerLoginResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    ProjectListViewModel.this.onSuccess(registerLoginResultBean.getData(),DATA1);
                } else {
                    ProjectListViewModel.this.onError(registerLoginResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ProjectListViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
            }
        }, page, id);
    }

    public void collect(int id) {
        HttpHelper.getInstance().collect(new Observer<ResultBean>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean homePageBeanResultBean) {
                if (homePageBeanResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    Project.DatasBean datasBean = new Project.DatasBean();
                    datasBean.setCollect(true);
                    ProjectListViewModel.this.onSuccess(datasBean,DATA2);
                } else {
                    ProjectListViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ProjectListViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                ProjectListViewModel.this.complete();
            }
        }, id);
    }


    public void unCollect(int id) {
        HttpHelper.getInstance().unCollect(new Observer<ResultBean>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean homePageBeanResultBean) {
                if (homePageBeanResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    Project.DatasBean datasBean = new Project.DatasBean();
                    datasBean.setCollect(false);
                    ProjectListViewModel.this.onSuccess(datasBean,DATA2);
                } else {
                    ProjectListViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ProjectListViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                ProjectListViewModel.this.complete();
            }
        }, id);
    }

}
