package com.zyh.wanandroid.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.bean.Navigation;

import java.util.List;

/**
 * @author 88421876
 * @date 2019/1/17
 */
public class NavigationRightAdapter extends BaseQuickAdapter<Navigation.ArticlesBean, BaseViewHolder> {

    public NavigationRightAdapter(List<Navigation.ArticlesBean> mList) {
        super(R.layout.item_navigation_right, mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Navigation.ArticlesBean item) {
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle());
    }
}
