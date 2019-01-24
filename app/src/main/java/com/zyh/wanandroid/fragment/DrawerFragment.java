package com.zyh.wanandroid.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.activity.ArticleListActivity;
import com.zyh.wanandroid.activity.RegisterLoginActivity;
import com.zyh.wanandroid.base.BaseFragment;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.databinding.FragmentDrawerBinding;
import com.zyh.wanandroid.utils.SharedPreferencesUtil;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.DrawerViewModel;

import static com.zyh.wanandroid.activity.ArticleListActivity.COLLECT_LIST;
import static com.zyh.wanandroid.activity.ArticleListActivity.TITLE;

/**
 * @author 88421876
 * @date 2019/1/21
 */
public class DrawerFragment extends BaseFragment<ResultBean> implements View.OnClickListener {
    FragmentDrawerBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_drawer, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        mBinding.imgAvatar.setOnClickListener(this);
        mBinding.itemOutLogin.setOnClickListener(this);
        mBinding.itemCollection.setOnClickListener(this);
        mBinding.loadingProgress.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(MyApplication.getInstance(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
    }

    private void initUser() {
        SharedPreferencesUtil sharedPreferencesUtil = SharedPreferencesUtil.getInstance();
        String userImgUrl = sharedPreferencesUtil.getUserImgUrl();
        if (userImgUrl != null && sharedPreferencesUtil.getUser() != null) {
            if (!userImgUrl.isEmpty()) {
                Glide.with(MyApplication.getInstance()).load(userImgUrl).into(mBinding.imgAvatar);
            }
            mBinding.tvName.setText(sharedPreferencesUtil.getUser().getUsername());
        } else {
            mBinding.imgAvatar.setImageDrawable(ContextCompat.getDrawable(MyApplication.getInstance(), R.drawable.img_avatar));
            mBinding.tvName.setText(getString(R.string.login));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initUser();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.img_avatar:
                intent = new Intent(getContext(), RegisterLoginActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.item_out_login:
                showLoading();
                DrawerViewModel viewModel = new DrawerViewModel();
                viewModel.attachView(this);
                viewModel.loginOut();
                break;
            case R.id.item_collection:
                intent = new Intent(getContext(), ArticleListActivity.class);
                intent.putExtra(ArticleListActivity.TYPE, COLLECT_LIST);
                intent.putExtra(TITLE, R.string.collect_list);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSuccess(ResultBean data) {
        hideLoading();
        SharedPreferencesUtil.getInstance().clearUser();
        initUser();
        ToastUtils.toastShort("退出成功");
    }


    @Override
    public void onError(String msg) {
        hideLoading();
        ToastUtils.toastShort(msg);
    }


    @Override
    public void showLoading() {
        super.showLoading();
        mBinding.loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mBinding.loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mBinding != null) {
                initUser();
            }
        }
    }
}
