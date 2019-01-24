package com.zyh.wanandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Message;
import android.os.Bundle;
import android.view.View;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.utils.BaseOnClickListener;
import com.zyh.wanandroid.utils.LocalHandler;
import com.zyh.wanandroid.databinding.ActivitySplashBinding;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author zyh
 */
public class SplashActivity extends BaseActivity implements LocalHandler.IHandle {
    private ActivitySplashBinding binding;
    private static final int START_TIMER = 1;
    private int time = 3;
    private ScheduledThreadPoolExecutor service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        startTimer();
        binding.tvTime.setOnClickListener(new BaseOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                stopTimer();
            }
        });
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == START_TIMER) {
            binding.tvTime.setText(String.valueOf(time-- + "s"));
            if (time < 1) {
                stopTimer();
            }
        }
    }

    private void startTimer() {
        final LocalHandler handler = new LocalHandler(this);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        service = new ScheduledThreadPoolExecutor(1, threadFactory);
        service.scheduleAtFixedRate(() -> handler.sendEmptyMessage(START_TIMER), 0, 1, TimeUnit.SECONDS);
    }

    private void stopTimer() {
        if (service != null) {
            service.shutdownNow();
            service = null;
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        finish();
    }
}
