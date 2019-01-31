package com.zyh.wanandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.databinding.ActivitySplashBinding;
import com.zyh.wanandroid.utils.BaseOnClickListener;
import com.zyh.wanandroid.utils.LocalHandler;
import com.zyh.wanandroid.widgets.MultiModeView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author zyh
 */
public class SplashActivity extends BaseActivity implements LocalHandler.IHandle {
    private static final int START_TIMER = 1;

    private ActivitySplashBinding mBinding;
    private ScheduledThreadPoolExecutor mService;

    private int mTime = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        startTimer();
        mBinding.tvTime.setOnClickListener(new BaseOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                stopTimer();
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == START_TIMER) {
            mBinding.tvTime.setText(String.valueOf(mTime-- + "s"));
            if (mTime < 1) {
                stopTimer();
            }
        }
    }

    private void startTimer() {
        final LocalHandler handler = new LocalHandler(this);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        mService = new ScheduledThreadPoolExecutor(1, threadFactory);
        mService.scheduleAtFixedRate(() -> handler.sendEmptyMessage(START_TIMER), 0, 1, TimeUnit.SECONDS);
    }

    private void stopTimer() {
        if (mService != null) {
            mService.shutdownNow();
            mService = null;
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        finish();
    }
}
