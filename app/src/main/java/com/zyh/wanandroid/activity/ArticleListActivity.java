package com.zyh.wanandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.adapter.HomePageAdapter;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.bean.HomePageDetail;
import com.zyh.wanandroid.databinding.ActivityArticleListBinding;
import com.zyh.wanandroid.utils.BaseOnClickListener;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.ArticleListViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyh
 */
public class ArticleListActivity extends BaseActivity<HomePageDetail> {
    ActivityArticleListBinding mBinding;
    int mArticleId;
    public static final String ARTICLE_ID = "article_id";
    int page = 0;
    private List<HomePageDetail> mList;
    HomePageAdapter adapter;
    ArticleListViewModel viewModel;
    private HomePageDetail homePageDetail;
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final int ARTICLE_LIST = 1;
    public static final int COLLECT_LIST = 2;
    private int type;
    private String mTitle;

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
        adapter = new HomePageAdapter(mList, type == COLLECT_LIST);
        mBinding.listView.setAdapter(adapter);
        mBinding.listView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.loadingProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        mBinding.refreshLayout.setOnLoadmoreListener(refreshLayout -> {
            if (type == ARTICLE_LIST) {
                viewModel.getArticleList(page, mArticleId);
            } else if (type == COLLECT_LIST) {
                viewModel.getCollectList(page);
            }
        });
        mBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            if (type == ARTICLE_LIST) {
                viewModel.getArticleList(page, mArticleId);
            } else if (type == COLLECT_LIST) {
                viewModel.getCollectList(page);
            }
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            HomePageDetail homePageDetail = mList.get(position);
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(WebActivity.URL, homePageDetail.getLink());
            intent.putExtra(WebActivity.TITLE, "详情");
            startActivity(intent);
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            homePageDetail = mList.get(position);
            if (homePageDetail.getCollect()) {
                if (type == COLLECT_LIST) {
                    viewModel.unCollect(mList.get(position).getId(),mList.get(position).getOriginId());
                } else {
                    viewModel.unCollect(mList.get(position).getId());
                }
            } else {
                viewModel.collect(mList.get(position).getId());
            }
        });

        viewModel = new ArticleListViewModel();
        viewModel.attachView(this);
        if (type == ARTICLE_LIST) {
            viewModel.getArticleList(page, mArticleId);
        } else if (type == COLLECT_LIST) {
            viewModel.getCollectList(page);
        }

    }

    private void initParameter() {
        mTitle = getIntent().getStringExtra(TITLE);
        mArticleId = getIntent().getIntExtra(ARTICLE_ID, 0);
        type = getIntent().getIntExtra(TYPE, 0);
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
            if (homePageDetail != null) {
                homePageDetail.setCollect(data.getCollect());
            }

            if (data.getCollect()) {
                ToastUtils.toastShort("收藏成功");
            } else {
                if (type == COLLECT_LIST) {
                    mList.remove(homePageDetail);
                }
                ToastUtils.toastShort("取消收藏成功");
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(List<HomePageDetail> data) {
        hideLoading();
        if (mBinding == null || adapter == null || data == null) {
            if (mBinding != null) {
                mBinding.refreshLayout.finishRefresh(false);
                mBinding.refreshLayout.finishLoadmore(false);
            }
            return;
        }
        if (page == 0) {
            mList.clear();
        }
        int pageSize = 20;
        if (data.size() < pageSize) {
            adapter.setFooterView(View.inflate(this, R.layout.coupon_footer, null));
            mBinding.refreshLayout.setEnableLoadmore(false);
        } else {
            mBinding.refreshLayout.setEnableLoadmore(true);
            adapter.removeAllFooterView();
        }
        page++;
        mList.addAll(data);
        adapter.notifyDataSetChanged();
        mBinding.refreshLayout.finishRefresh(true);
        mBinding.refreshLayout.finishLoadmore(true);
    }

    @Override
    public void onError(String msg) {
        hideLoading();
        ToastUtils.toastShort(msg);
        mBinding.refreshLayout.finishRefresh(false);
        mBinding.refreshLayout.finishLoadmore(false);
    }

    @Override
    public void complete() {

    }

    @Override
    public void showLoading() {
        mBinding.loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mBinding.loadingProgress.setVisibility(View.GONE);
    }
}
