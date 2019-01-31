package com.zyh.wanandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.adapter.HomePageAdapter;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.bean.HomePageDetail;
import com.zyh.wanandroid.databinding.ActivityArticleListBinding;
import com.zyh.wanandroid.utils.BaseOnClickListener;
import com.zyh.wanandroid.utils.NetWorkUtils;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.ArticleListViewModel;
import com.zyh.wanandroid.widgets.MultiModeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyh
 */
public class ArticleListActivity extends BaseActivity<HomePageDetail> {

    public static final String ARTICLE_ID = "article_id";
    public static final String TYPE = "mType";
    public static final String TITLE = "title";
    public static final int ARTICLE_LIST = 1;
    public static final int COLLECT_LIST = 2;
    public static final int SEARCH_LIST = 3;

    private ActivityArticleListBinding mBinding;
    private HomePageAdapter mAdapter;
    private ArticleListViewModel mViewModel;
    private HomePageDetail mHomePageDetail;

    private int mArticleId;
    private int mType;
    private int mPage = 0;

    private String mSearchKey;
    private String mTitle;

    private List<HomePageDetail> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_list);
        initParameter();
        initToolbar();
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new HomePageAdapter(mList, mType == COLLECT_LIST);
        mBinding.listView.setAdapter(mAdapter);
        mBinding.listView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.refreshLayout.setOnLoadmoreListener(refreshLayout -> getData());
        mBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            mPage = 0;
            getData();
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            HomePageDetail homePageDetail = mList.get(position);
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(WebActivity.URL, homePageDetail.getLink());
            intent.putExtra(WebActivity.TITLE, "详情");
            startActivity(intent);
        });
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mHomePageDetail = mList.get(position);
            if (mHomePageDetail.getCollect()) {
                if (mType == COLLECT_LIST) {
                    mViewModel.unCollect(mList.get(position).getId(), mList.get(position).getOriginId());
                } else {
                    mViewModel.unCollect(mList.get(position).getId());
                }
            } else {
                mViewModel.collect(mList.get(position).getId());
            }
        });

        mViewModel = new ArticleListViewModel();
        mViewModel.attachView(this);
        if (mType == ARTICLE_LIST) {
            mViewModel.getArticleList(mPage, mArticleId);
        } else if (mType == COLLECT_LIST) {
            mViewModel.getCollectList(mPage);
        } else if (mType == SEARCH_LIST) {
            mViewModel.getSearchList(mPage, mSearchKey);
        }
    }

    private void getData() {
        if (mType == ARTICLE_LIST) {
            mViewModel.getArticleList(mPage, mArticleId);
        } else if (mType == COLLECT_LIST) {
            mViewModel.getCollectList(mPage);
        } else if (mType == SEARCH_LIST) {
            mViewModel.getSearchList(mPage, mSearchKey);
        }
    }

    private void initParameter() {
        mTitle = getIntent().getStringExtra(TITLE);
        mArticleId = getIntent().getIntExtra(ARTICLE_ID, 0);
        mType = getIntent().getIntExtra(TYPE, 0);
        mSearchKey = getIntent().getStringExtra("key");
    }

    private void initToolbar() {
        mBinding.toolbar.getLeftImageView().setOnClickListener(new BaseOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                finish();
            }
        });
        mBinding.toolbar.getCenterTextView().setText(mTitle);
    }

    @Override
    public void onSuccess(HomePageDetail data) {
        hideLoading();
        if (data != null) {
            if (mHomePageDetail != null) {
                mHomePageDetail.setCollect(data.getCollect());
            }

            if (data.getCollect()) {
                ToastUtils.toastShort("收藏成功");
            } else {
                if (mType == COLLECT_LIST) {
                    mList.remove(mHomePageDetail);
                }
                ToastUtils.toastShort("取消收藏成功");
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(List<HomePageDetail> data) {
        hideLoading();
        if (mBinding == null || mAdapter == null || data == null) {
            if (mBinding != null) {
                mBinding.refreshLayout.finishRefresh(false);
                mBinding.refreshLayout.finishLoadmore(false);
            }
            return;
        }
        if (mPage == 0) {
            mList.clear();
        }
        if (mPage == 0 && data.isEmpty()) {
            showEmpty();
        }
        int pageSize = 20;
        if (data.size() < pageSize) {
            mAdapter.setFooterView(View.inflate(this, R.layout.coupon_footer, null));
            mBinding.refreshLayout.setEnableLoadmore(false);
        } else {
            mBinding.refreshLayout.setEnableLoadmore(true);
            mAdapter.removeAllFooterView();
        }
        mPage++;
        mList.addAll(data);
        mAdapter.notifyDataSetChanged();
        mBinding.refreshLayout.finishRefresh(true);
        mBinding.refreshLayout.finishLoadmore(true);
    }

    @Override
    public void onError(String msg) {
        ToastUtils.toastShort(msg);
        mBinding.refreshLayout.finishRefresh(false);
        mBinding.refreshLayout.finishLoadmore(false);
        if (NetWorkUtils.isNetworkConnected()) {
            showError();
        } else {
            showNetWorkError();
        }
    }

    @Override
    public MultiModeView getMultiModeView() {
        return mBinding.multiModeView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mViewModel != null) {
            mViewModel.detachView();
        }
    }
}
