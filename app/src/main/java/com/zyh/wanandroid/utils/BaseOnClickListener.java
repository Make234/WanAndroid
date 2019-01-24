package com.zyh.wanandroid.utils;

import android.view.View;

import java.util.Calendar;

/**
 * @author zyh
 */
public abstract class BaseOnClickListener implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    /**
     * 防止多次点击的点击回调
     *
     * @param v View
     */
    protected abstract void onNoDoubleClick(View v);
}