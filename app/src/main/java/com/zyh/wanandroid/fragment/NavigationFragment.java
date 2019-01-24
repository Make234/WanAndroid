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
import com.zyh.wanandroid.activity.WebActivity;
import com.zyh.wanandroid.adapter.NavigationLeftAdapter;
import com.zyh.wanandroid.adapter.NavigationRightAdapter;
import com.zyh.wanandroid.base.BaseFragment;
import com.zyh.wanandroid.bean.Navigation;
import com.zyh.wanandroid.databinding.FragmentNavigationBinding;
import com.zyh.wanandroid.utils.NetWorkUtils;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.NavigationViewModel;
import com.zyh.wanandroid.widgets.FlowLayoutManager;
import com.zyh.wanandroid.widgets.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

/**
 * @author zyh
 * @date 2019/1/16
 */
public class NavigationFragment extends BaseFragment<Navigation> {
    NavigationViewModel viewModel;
    FragmentNavigationBinding mBinding;
    ArrayList<Navigation> mLeftList;
    ArrayList<Navigation.ArticlesBean> mRightList;
    NavigationLeftAdapter mLeftAdapter;
    NavigationRightAdapter mRightAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_navigation, container, false);
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
        mLeftList = new ArrayList<>();
        mLeftAdapter = new NavigationLeftAdapter(mLeftList);
        mBinding.rlLeft.setAdapter(mLeftAdapter);
        mBinding.rlLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        mLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            mLeftAdapter.setIndex(position);
            mLeftAdapter.notifyDataSetChanged();
            mRightList.clear();
            mRightList.addAll(mLeftList.get(position).getArticles());
            mRightAdapter.notifyDataSetChanged();
        });

        mRightList = new ArrayList<>();
        mRightAdapter = new NavigationRightAdapter(mRightList);
        mBinding.rlRight.setAdapter(mRightAdapter);
        mBinding.rlRight.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        mBinding.rlRight.setLayoutManager(new FlowLayoutManager());
        mRightAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (mRightList == null || mRightList.size() < position) {
                return;
            }
            Navigation.ArticlesBean articlesBean = mRightList.get(position);
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra(WebActivity.URL, articlesBean.getLink());
            intent.putExtra(WebActivity.TITLE, articlesBean.getTitle());
            startActivity(intent);
        });
        mBinding.multiModeView.setOnNetWorkClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new NavigationViewModel();
                viewModel.attachView(this);
            }
            viewModel.getKnowLedge();
        });

        mBinding.multiModeView.setOnNoDataClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new NavigationViewModel();
                viewModel.attachView(this);
            }
            viewModel.getKnowLedge();
        });

        mBinding.multiModeView.setOnErrorClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new NavigationViewModel();
                viewModel.attachView(this);
            }
            viewModel.getKnowLedge();
        });
        viewModel = new NavigationViewModel();
        viewModel.attachView(this);
        viewModel.getKnowLedge();
    }

    @Override
    public void onSuccess(Navigation data) {
        hideLoading();
    }

    @Override
    public void onSuccess(List<Navigation> data) {
        hideLoading();
        if (data == null || data.isEmpty()) {
            mBinding.multiModeView.showEmpty();
            return;
        }
        mLeftList.clear();
        mLeftList.addAll(data);
        mLeftAdapter.notifyDataSetChanged();

        mRightList.clear();
        mRightList.addAll(mLeftList.get(0).getArticles());
        mRightAdapter.notifyDataSetChanged();
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
        mBinding.multiModeView.showLoading();
    }

    @Override
    public void hideLoading() {
        mBinding.multiModeView.setVisibility(View.GONE);
    }
}
