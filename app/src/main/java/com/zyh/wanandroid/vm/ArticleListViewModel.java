package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.HomePageBean;
import com.zyh.wanandroid.bean.HomePageDetail;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.http.HttpHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author 88421876
 * @date 2019/1/22
 */
public class ArticleListViewModel extends BaseViewModel<HomePageDetail> {

    public void getArticleList(int page, int id) {
        HttpHelper.getInstance().getArticleList(new Observer<ResultBean<HomePageBean>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean<HomePageBean> resultListBean) {
                ArticleListViewModel.this.hideLoading();
                if (resultListBean.getErrorCode() == ResultBean.SUCCESS) {
                    ArticleListViewModel.this.onSuccess(resultListBean.getData().getData());
                } else {
                    ArticleListViewModel.this.onError(resultListBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ArticleListViewModel.this.onError(e.getMessage());
                ArticleListViewModel.this.hideLoading();
            }

            @Override
            public void onComplete() {
                d.dispose();
                ArticleListViewModel.this.hideLoading();
            }
        }, page, id);
    }

    public void getCollectList(int page) {
        HttpHelper.getInstance().getCollectList(new Observer<ResultBean<HomePageBean>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean<HomePageBean> resultListBean) {
                ArticleListViewModel.this.hideLoading();
                if (resultListBean.getErrorCode() == ResultBean.SUCCESS) {
                    ArticleListViewModel.this.onSuccess(resultListBean.getData().getData());
                } else {
                    ArticleListViewModel.this.onError(resultListBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ArticleListViewModel.this.onError(e.getMessage());
                ArticleListViewModel.this.hideLoading();
            }

            @Override
            public void onComplete() {
                d.dispose();
                ArticleListViewModel.this.hideLoading();
            }
        }, page);
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
                    HomePageDetail homePageDetail = new HomePageDetail();
                    homePageDetail.setCollect(true);
                    ArticleListViewModel.this.onSuccess(homePageDetail);
                } else {
                    ArticleListViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ArticleListViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                ArticleListViewModel.this.complete();
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
                    HomePageDetail homePageDetail = new HomePageDetail();
                    homePageDetail.setCollect(false);
                    ArticleListViewModel.this.onSuccess(homePageDetail);
                } else {
                    ArticleListViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ArticleListViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                ArticleListViewModel.this.complete();
            }
        }, id);
    }


    public void unCollect(int id, int originId) {
        HttpHelper.getInstance().unCollect(new Observer<ResultBean>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean homePageBeanResultBean) {
                if (homePageBeanResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    HomePageDetail homePageDetail = new HomePageDetail();
                    homePageDetail.setCollect(false);
                    ArticleListViewModel.this.onSuccess(homePageDetail);
                } else {
                    ArticleListViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                ArticleListViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                ArticleListViewModel.this.complete();
            }
        }, id, originId);
    }

}
