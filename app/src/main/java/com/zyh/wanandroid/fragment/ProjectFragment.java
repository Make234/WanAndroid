package com.zyh.wanandroid.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.adapter.ProjectPagerAdapter;
import com.zyh.wanandroid.base.BaseFragment;
import com.zyh.wanandroid.bean.ProjectTitle;
import com.zyh.wanandroid.databinding.FragmentProjectBinding;
import com.zyh.wanandroid.vm.ProjectViewModel;
import com.zyh.wanandroid.widgets.MultiModeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyh
 * @date 2019/1/16
 */
public class ProjectFragment extends BaseFragment<ProjectTitle> {
    FragmentProjectBinding mBinding;
    ArrayList<ProjectListFragment> mFragmentList;
    ArrayList<ProjectTitle> mTitleList;
    ProjectViewModel viewModel;
    ProjectPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();

        pagerAdapter = new ProjectPagerAdapter(getFragmentManager(), mFragmentList, mTitleList);
        mBinding.viewPager.setAdapter(pagerAdapter);
        mBinding.tabs.setupWithViewPager(mBinding.viewPager);
        mBinding.multiModeView.setOnErrorClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new ProjectViewModel();
                viewModel.attachView(this);
            }
            viewModel.getProjectTitle();
        });

        mBinding.multiModeView.setOnNoDataClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new ProjectViewModel();
                viewModel.attachView(this);
            }
            viewModel.getProjectTitle();
        });

        mBinding.multiModeView.setOnNetWorkClick(view -> {
            mBinding.multiModeView.showLoading();
            if (viewModel == null) {
                viewModel = new ProjectViewModel();
                viewModel.attachView(this);
            }
            viewModel.getProjectTitle();
        });
        viewModel = new ProjectViewModel();
        viewModel.attachView(this);
        viewModel.getProjectTitle();
    }

    @Override
    public void onSuccess(List<ProjectTitle> data) {
        super.onSuccess(data);
        if (mTitleList == null) {
            mTitleList = new ArrayList<>();
        }
        if (data.isEmpty()) {
            mBinding.multiModeView.showEmpty();
            return;
        }
        mTitleList.clear();
        mTitleList.addAll(data);
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        mFragmentList.clear();
        for (ProjectTitle projectTitle : mTitleList) {
            ProjectListFragment fragment = ProjectListFragment.newInstance(projectTitle.getId());
            mFragmentList.add(fragment);
        }
        if (pagerAdapter != null) {
            pagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public MultiModeView getMultiModeView() {
        return mBinding.multiModeView;
    }
}
