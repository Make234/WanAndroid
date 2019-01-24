package com.zyh.wanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zyh.wanandroid.bean.ProjectTitle;
import com.zyh.wanandroid.fragment.ProjectListFragment;

import java.util.ArrayList;

/**
 * @author 88421876
 * @date 2019/1/23
 */
public class ProjectPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<ProjectListFragment> mFragmentList;
    private ArrayList<ProjectTitle> mTitleList;

    public ProjectPagerAdapter(FragmentManager fragmentManager, ArrayList<ProjectListFragment> mFragmentList, ArrayList<ProjectTitle> mTitleList) {
        super(fragmentManager);
        this.mFragmentList = mFragmentList;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return mTitleList.size();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position).getName();
    }

}
