package com.zyh.wanandroid.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.bean.Navigation;

import java.util.List;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class NavigationLeftAdapter extends BaseQuickAdapter<Navigation, BaseViewHolder> {
    private int index = 0;

    public NavigationLeftAdapter(List<Navigation> mList) {
        super(R.layout.item_navigation_left, mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Navigation item) {
        int position = helper.getAdapterPosition();
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getName());
        if (index == position) {
            helper.getView(R.id.tv_title).setBackgroundColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.colorPrimary));
        } else {
            helper.getView(R.id.tv_title).setBackgroundColor(ContextCompat.getColor(MyApplication.getInstance(), R.color.white));
        }
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
