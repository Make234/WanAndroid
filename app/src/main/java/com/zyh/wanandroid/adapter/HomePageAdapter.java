package com.zyh.wanandroid.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.bean.HomePageDetail;
import com.zyh.wanandroid.utils.SharedPreferencesUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class HomePageAdapter extends BaseQuickAdapter<HomePageDetail, BaseViewHolder> {

    private boolean mIsCollect;

    public HomePageAdapter(List<HomePageDetail> mList, boolean isCollect) {
        super(R.layout.item_homepage, mList);
        this.mIsCollect = isCollect;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageDetail item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, getTime(item.getPublishTime()));
        if (item.getTags() != null && !item.getTags().isEmpty()) {
            helper.setText(R.id.tv_type, item.getTags().get(0).getName());
        } else {
            helper.setVisible(R.id.tv_type, false);
        }
        ImageView ivCollection = helper.getView(R.id.iv_collection);
        if (item.getCollect() || mIsCollect && SharedPreferencesUtil.getInstance().getUser() != null) {
            item.setCollect(true);
            ivCollection.setImageDrawable(ContextCompat.getDrawable(MyApplication.getInstance(), R.drawable.ic_collection));
        } else {
            ivCollection.setImageDrawable(ContextCompat.getDrawable(MyApplication.getInstance(), R.drawable.ic_collection_gray));
        }
        helper.addOnClickListener(R.id.iv_collection);
    }

    private String getTime(long oldTime) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTime(new Date(System.currentTimeMillis()));
        Calendar oldDate = Calendar.getInstance();
        oldDate.setTime(new Date(oldTime));
        int newYear = newDate.get(Calendar.YEAR);
        int oldYear = oldDate.get(Calendar.YEAR);
        int newMonth = newDate.get(Calendar.MONTH);
        int oldMonth = oldDate.get(Calendar.MONTH);
        int maxMonth = 12;
        if (newYear > oldYear && newMonth - oldMonth > maxMonth) {
            return newYear - oldYear + "年前";
        }
        int newDay = newDate.get(Calendar.DAY_OF_YEAR);
        int oldDay = oldDate.get(Calendar.DAY_OF_YEAR);

        int maxDay = 31;
        if (newMonth > oldMonth && newDay - oldDay > maxDay) {
            return newMonth - oldMonth + "月前";
        }

        if (newDay > oldDay) {
            return newDay - oldDay + "天前";
        }

        int newHour = newDate.get(Calendar.HOUR_OF_DAY);
        int oldHour = oldDate.get(Calendar.HOUR_OF_DAY);
        if (newHour > oldHour) {
            return newHour - oldHour + "小时前";
        }

        int newMinute = newDate.get(Calendar.MINUTE);
        int oldMinute = oldDate.get(Calendar.MINUTE);
        if (newMinute > oldMinute) {
            return newMinute - oldMinute + "分钟前";
        }
        return "刚刚";
    }

}
