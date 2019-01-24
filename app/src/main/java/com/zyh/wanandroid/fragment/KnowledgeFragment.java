package com.zyh.wanandroid.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.activity.ArticleListActivity;
import com.zyh.wanandroid.adapter.KnowledgeAdapter;
import com.zyh.wanandroid.base.BaseFragment;
import com.zyh.wanandroid.bean.Knowledge;
import com.zyh.wanandroid.databinding.FragmentKnowledgeBinding;
import com.zyh.wanandroid.utils.NetWorkUtils;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.KnowledgeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.zyh.wanandroid.activity.ArticleListActivity.ARTICLE_ID;
import static com.zyh.wanandroid.activity.ArticleListActivity.ARTICLE_LIST;
import static com.zyh.wanandroid.activity.ArticleListActivity.TITLE;

/**
 * @author zyh
 * @date 2019/1/16
 */
public class KnowledgeFragment extends BaseFragment<Knowledge> {
    FragmentKnowledgeBinding mBinding;
    KnowledgeViewModel viewModel;
    ArrayList<Knowledge> mList;
    KnowledgeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_knowledge, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (getContext() == null) {
            return;
        }
        mList = new ArrayList<>();
        adapter = new KnowledgeAdapter(mList);
        mBinding.listView.setAdapter(adapter);
        mBinding.listView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            //注意，此处position 传的不是索引位置，而是点击item的分类id
            Intent intent = new Intent(getContext(), ArticleListActivity.class);
            intent.putExtra(ARTICLE_ID, position);
            intent.putExtra(TITLE, getString(R.string.article_list));
            intent.putExtra(ArticleListActivity.TYPE, ARTICLE_LIST);
            startActivity(intent);
        });
        mBinding.multiModeView.setOnErrorClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new KnowledgeViewModel();
                viewModel.attachView(this);
            }
            viewModel.getKnowLedge();
        });

        mBinding.multiModeView.setOnNoDataClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new KnowledgeViewModel();
                viewModel.attachView(this);
            }
            viewModel.getKnowLedge();
        });

        mBinding.multiModeView.setOnNetWorkClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new KnowledgeViewModel();
                viewModel.attachView(this);
            }
            viewModel.getKnowLedge();
        });

        viewModel = new KnowledgeViewModel();
        viewModel.attachView(this);
        viewModel.getKnowLedge();
    }

    @Override
    public void onSuccess(Knowledge data) {

    }

    @Override
    public void onSuccess(List<Knowledge> data) {
        if (data.isEmpty()) {
            mBinding.multiModeView.showEmpty();
        }
        mList.addAll(data);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onError(String msg) {
        if (NetWorkUtils.isNetworkConnected()) {
            mBinding.multiModeView.showError();
        } else {
            mBinding.multiModeView.showNetWork();
        }
        ToastUtils.toastShort(msg);
    }

    @Override
    public void complete() {
        hideLoading();
    }

    @Override
    public void showLoading() {
        if (mBinding != null) {
            mBinding.multiModeView.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (mBinding != null) {
            mBinding.multiModeView.setVisibility(View.GONE);
        }
    }

}
