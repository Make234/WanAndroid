package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.Banner;
import com.zyh.wanandroid.bean.HomePageBean;
import com.zyh.wanandroid.bean.HomePageDetail;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.bean.ResultListBean;
import com.zyh.wanandroid.http.HttpHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class HomePageViewModel extends BaseViewModel {

    public void getHomePageList(int page) {
        HttpHelper.getInstance().getHomePageList(new Observer<ResultBean<HomePageBean>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultBean<HomePageBean> homePageBeanResultBean) {
                if (homePageBeanResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    HomePageViewModel.this.onSuccess(homePageBeanResultBean.getData().getData(), DATA2);
                } else {
                    HomePageViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                HomePageViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                HomePageViewModel.this.complete();
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
                    HomePageViewModel.this.onSuccess(homePageDetail);
                } else {
                    HomePageViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                HomePageViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                HomePageViewModel.this.complete();
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
                    HomePageViewModel.this.onSuccess(homePageDetail);
                } else {
                    HomePageViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                HomePageViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                HomePageViewModel.this.complete();
            }
        }, id);
    }

    public void getBannerUrls() {
        HttpHelper.getInstance().getBannerUrls(new Observer<ResultListBean<Banner>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultListBean<Banner> bannerResultBean) {
                if (bannerResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    HomePageViewModel.this.onSuccess(bannerResultBean.getData(), DATA1);
                } else {
                    HomePageViewModel.this.onError(bannerResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                HomePageViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                HomePageViewModel.this.complete();
            }
        });
    }

}
