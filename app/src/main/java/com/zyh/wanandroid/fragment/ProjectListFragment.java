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
import com.zyh.wanandroid.adapter.ProjectListAdapter;
import com.zyh.wanandroid.base.BaseFragment;
import com.zyh.wanandroid.bean.Project;
import com.zyh.wanandroid.databinding.FragmentProjectListBinding;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.ProjectListViewModel;
import com.zyh.wanandroid.widgets.MultiModeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyh
 * @date 2019/1/23
 */
public class ProjectListFragment extends BaseFragment {
    public static final String PROJECT_ID = "projectId";
    FragmentProjectListBinding mBinding;
    ProjectListViewModel viewModel;
    int projectId;
    int page = 1;
    List<Project.DatasBean> mList;
    ProjectListAdapter adapter;
    Project.DatasBean pageDetail;

    static ProjectListFragment newInstance(int projectId) {
        ProjectListFragment f = new ProjectListFragment();
        Bundle b = new Bundle();
        b.putInt(PROJECT_ID, projectId);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        if (getArguments() == null) {
            return;
        }
        mList = new ArrayList<>();
        adapter = new ProjectListAdapter(mList);
        mBinding.listView.setAdapter(adapter);
        mBinding.listView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.refreshLayout.setOnLoadmoreListener(refreshLayout -> viewModel.getProject(page, projectId));
        mBinding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            viewModel.getProject(page, projectId);
        });
        mBinding.multiModeView.setOnNoDataClick(view -> {
            showLoading();
            page = 1;
            viewModel.getProject(page, projectId);
        });
        mBinding.multiModeView.setOnNetWorkClick(view -> {
            showLoading();
            page = 1;
            viewModel.getProject(page, projectId);
        });
        mBinding.multiModeView.setOnErrorClick(view -> {
            showLoading();
            page = 1;
            viewModel.getProject(page, projectId);
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (mList != null && mList.size() > position) {
                Project.DatasBean banner = mList.get(position);
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(WebActivity.URL, banner.getLink());
                intent.putExtra(WebActivity.TITLE, banner.getTitle());
                startActivity(intent);
            }
        });

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            pageDetail = mList.get(position);

            if (pageDetail.isCollect()) {
                viewModel.unCollect(pageDetail.getId());
            } else {
                viewModel.collect(pageDetail.getId());
            }
        });
        projectId = getArguments().getInt(PROJECT_ID);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel = new ProjectListViewModel();
        viewModel.attachView(this);
        showLoading();
        page = 1;
        viewModel.getProject(page, projectId);
    }

    @Override
    public void onSuccess(Object data, int type) {
        super.onSuccess(data);
        hideLoading();
        if (mBinding == null || adapter == null || data == null) {
            if (mBinding != null) {
                mBinding.refreshLayout.finishRefresh(false);
                mBinding.refreshLayout.finishLoadmore(false);
            }
            return;
        }

        if (type == DATA1) {
            Project project = (Project) data;
            initProject(project);
        } else if (type == DATA2) {
            Project.DatasBean detail = (Project.DatasBean) data;
            initCollect(detail);
        }
    }

    private void initCollect(Project.DatasBean detail) {
        if (pageDetail != null) {
            pageDetail.setCollect(detail.isCollect());
        }
        adapter.notifyDataSetChanged();
        if (detail.isCollect()) {
            ToastUtils.toastShort("收藏成功");
        } else {
            ToastUtils.toastShort("取消收藏成功");
        }
    }

    private void initProject(Project project) {
        if (project.getDatas() == null) {
            if (mBinding != null) {
                mBinding.refreshLayout.finishRefresh(false);
                mBinding.refreshLayout.finishLoadmore(false);
            }
            return;
        }
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (page == 1) {
            mList.clear();
        }
        if (page == 1 && project.getDatas().isEmpty()) {
            mBinding.multiModeView.setVisibility(View.VISIBLE);
            mBinding.refreshLayout.finishRefresh(true);
            mBinding.refreshLayout.finishLoadmore(true);
            mBinding.multiModeView.showEmpty();
            return;
        }
        int pageSize = 15;
        if (project.getDatas().size() < pageSize) {
            adapter.setFooterView(View.inflate(getActivity(), R.layout.coupon_footer, null));
            mBinding.refreshLayout.setEnableLoadmore(false);
        } else {
            mBinding.refreshLayout.setEnableLoadmore(true);
            adapter.removeAllFooterView();
        }
        page++;
        mList.addAll(project.getDatas());
        adapter.notifyDataSetChanged();
        mBinding.refreshLayout.finishRefresh(true);
        mBinding.refreshLayout.finishLoadmore(true);
    }

    @Override
    public void showLoading() {
        super.showLoading();
        mBinding.refreshLayout.finishRefresh(false);
        mBinding.refreshLayout.finishLoadmore(false);
    }

    @Override
    public MultiModeView getMultiModeView() {
        return mBinding.multiModeView;
    }
}
