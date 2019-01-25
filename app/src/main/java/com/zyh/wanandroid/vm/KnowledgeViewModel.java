package com.zyh.wanandroid.vm;

import com.zyh.wanandroid.base.BaseViewModel;
import com.zyh.wanandroid.bean.Knowledge;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.bean.ResultListBean;
import com.zyh.wanandroid.http.HttpHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class KnowledgeViewModel extends BaseViewModel<Knowledge> {

    public void getKnowLedge() {
        HttpHelper.getInstance().getKnowLedge(new Observer<ResultListBean<Knowledge>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onNext(ResultListBean<Knowledge> homePageBeanResultBean) {
                if (homePageBeanResultBean.getErrorCode() == ResultBean.SUCCESS) {
                    KnowledgeViewModel.this.onSuccess(homePageBeanResultBean.getData());
                } else {
                    KnowledgeViewModel.this.onError(homePageBeanResultBean.getErrorMsg());
                }
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                KnowledgeViewModel.this.onError(e.getMessage());
            }

            @Override
            public void onComplete() {
                d.dispose();
                KnowledgeViewModel.this.complete();
            }
        });
    }
}
