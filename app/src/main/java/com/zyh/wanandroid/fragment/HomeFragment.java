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
import com.zyh.wanandroid.adapter.HomePageAdapter;
import com.zyh.wanandroid.base.BaseFragment;
import com.zyh.wanandroid.bean.Banner;
import com.zyh.wanandroid.bean.HomePageDetail;
import com.zyh.wanandroid.databinding.FragmentHomeBinding;
import com.zyh.wanandroid.utils.NetWorkUtils;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.HomePageViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyh
 * @date 2019/1/16
 */
public class HomeFragment extends BaseFragment {
    FragmentHomeBinding dataBinding;
    HomePageViewModel viewModel;
    int page = 0;
    private List<HomePageDetail> mList;
    HomePageAdapter adapter;
    private HomePageDetail homePageDetail;
    private List<Banner> mBannerList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        return dataBinding.getRoot();
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
        adapter = new HomePageAdapter(mList, false);
        dataBinding.listView.setAdapter(adapter);
        dataBinding.listView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataBinding.refreshLayout.setOnLoadmoreListener(refreshLayout -> viewModel.getHomePageList(page));
        dataBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            viewModel.getHomePageList(page);
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            HomePageDetail homePageDetail = mList.get(position);
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra(WebActivity.URL, homePageDetail.getLink());
            intent.putExtra(WebActivity.TITLE, "详情");
            startActivity(intent);
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            homePageDetail = mList.get(position);
            if (homePageDetail.getCollect()) {
                viewModel.unCollect(mList.get(position).getId());
            } else {
                viewModel.collect(mList.get(position).getId());
            }
        });
        dataBinding.banner.setOnItemClickListener(position -> {
            if (mBannerList != null && mBannerList.size() > position) {
                Banner banner = mBannerList.get(position);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(WebActivity.URL, banner.getUrl());
                intent.putExtra(WebActivity.TITLE, banner.getTitle());
                startActivity(intent);
            }
        });
        dataBinding.multiModeView.setOnErrorClick(view -> {
            dataBinding.multiModeView.showLoading();
            page = 0;
            viewModel.getHomePageList(page);
            viewModel.getBannerUrls();
        });

        dataBinding.multiModeView.setOnNetWorkClick(view -> {
            dataBinding.multiModeView.showLoading();
            page = 0;
            viewModel.getHomePageList(page);
            viewModel.getBannerUrls();
        });

        dataBinding.multiModeView.setOnNoDataClick(view -> {
            dataBinding.multiModeView.showLoading();
            page = 0;
            viewModel.getHomePageList(page);
            viewModel.getBannerUrls();
        });
        showLoading();
        viewModel = new HomePageViewModel();
        viewModel.attachView(this);
        viewModel.getBannerUrls();
        viewModel.getHomePageList(page);
    }

    @Override
    public void onError(String msg) {
        ToastUtils.toastShort(msg);
        if (NetWorkUtils.isNetworkConnected()) {
            dataBinding.multiModeView.showError();
        } else {
            dataBinding.multiModeView.showNetWork();
        }
    }

    @Override
    public void onSuccess(Object data) {
        HomePageDetail detail = (HomePageDetail) data;
        if (data != null) {
            if (homePageDetail != null) {
                homePageDetail.setCollect(detail.getCollect());
            }
            adapter.notifyDataSetChanged();
            if (detail.getCollect()) {
                ToastUtils.toastShort("收藏成功");
            } else {
                ToastUtils.toastShort("取消收藏成功");
            }
        }

    }

    @Override
    public void onSuccess(List data, int type) {
        hideLoading();
        if (dataBinding == null || adapter == null || data == null) {
            if (dataBinding != null) {
                dataBinding.refreshLayout.finishRefresh(false);
                dataBinding.refreshLayout.finishLoadmore(false);
            }

            return;
        }
        if (type == DATA1) {
            mBannerList = data;
            ArrayList<String> imgUrl = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                String imagePath = ((Banner) data.get(i)).getImagePath();
                imgUrl.add(imagePath);
            }
            dataBinding.banner.setImagesUrl(imgUrl);

        } else if (type == DATA2) {
            if (page == 0) {
                mList.clear();
            }

            if (page == 0 && data.isEmpty()) {
                dataBinding.multiModeView.showEmpty();
                return;
            }
            int pageSize = 20;
            if (data.size() < pageSize) {
                adapter.setFooterView(View.inflate(getActivity(), R.layout.coupon_footer, null));
                dataBinding.refreshLayout.setEnableLoadmore(false);
            } else {
                dataBinding.refreshLayout.setEnableLoadmore(true);
                adapter.removeAllFooterView();
            }
            page++;
            mList.addAll(data);
            adapter.notifyDataSetChanged();
            dataBinding.refreshLayout.finishRefresh(true);
            dataBinding.refreshLayout.finishLoadmore(true);
        }
    }

    @Override
    public void showLoading() {
        if (dataBinding != null) {
            dataBinding.refreshLayout.setVisibility(View.GONE);
            dataBinding.multiModeView.showLoading();
        }
    }

    @Override
    public void hideLoading() {
        if (dataBinding != null) {
            dataBinding.refreshLayout.setVisibility(View.VISIBLE);
            dataBinding.multiModeView.setVisibility(View.GONE);
        }
    }

    @Override
    public void complete() {

    }
}
