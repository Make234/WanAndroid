package com.zyh.wanandroid.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.bean.Project;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zyh
 * @date 2019/1/23
 */
public class ProjectListAdapter extends BaseQuickAdapter<Project.DatasBean, BaseViewHolder> {
    public ProjectListAdapter(List<Project.DatasBean> mList) {
        super(R.layout.item_project_list, mList);
    }

    @Override
    protected void convert(BaseViewHolder helper, Project.DatasBean item) {
        ((TextView) helper.getView(R.id.tv_title)).setText(item.getTitle());
        ((TextView) helper.getView(R.id.tv_author)).setText(item.getAuthor());
        ((TextView) helper.getView(R.id.tv_time)).setText(getTime(item.getPublishTime()));
        ((TextView) helper.getView(R.id.tv_desc)).setText(item.getDesc());
        if (item.getTags() != null && !item.getTags().isEmpty()) {
            helper.setText(R.id.tv_type, item.getTags().get(0).getName());
        } else {
            helper.setVisible(R.id.tv_type, false);
        }
        ImageView ivCollection = helper.getView(R.id.iv_collection);
        if (item.isCollect()) {
            ivCollection.setImageDrawable(ContextCompat.getDrawable(MyApplication.getInstance(), R.drawable.ic_collection));
        } else {
            ivCollection.setImageDrawable(ContextCompat.getDrawable(MyApplication.getInstance(), R.drawable.ic_collection_gray));
        }
        helper.addOnClickListener(R.id.iv_collection);
        String envelopePic = item.getEnvelopePic();
        if (envelopePic != null && !envelopePic.isEmpty()) {
            Glide.with(MyApplication.getInstance()).load(envelopePic).into((ImageView) helper.getView(R.id.image));
        }
    }

    private String getTime(long publishTime) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTime(new Date(System.currentTimeMillis()));
        Calendar oldDate = Calendar.getInstance();
        oldDate.setTime(new Date(publishTime));
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
