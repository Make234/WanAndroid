package com.zyh.wanandroid.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.databinding.ActivityWebBinding;
import com.zyh.wanandroid.utils.BaseOnClickListener;
import com.zyh.wanandroid.widgets.MultiModeView;

/**
 * @author zyh
 */
public class WebActivity extends BaseActivity {
    public static final String URL = "url";
    public static final String TITLE = "title";

    private ActivityWebBinding mBinding;

    private String mUrl;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        initParameter();
        initTitle();
        initView();
    }

    private void initTitle() {
        mBinding.toolbar.getLeftImageView().setOnClickListener(new BaseOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mBinding.webView.canGoBack()) {
                    mBinding.webView.goBack();
                } else {
                    finish();
                }
            }
        });
        mBinding.toolbar.getCenterTextView().setText(mTitle);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        mBinding.webView.loadUrl(mUrl);
        mBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                int maxProgress = 90;
                if (newProgress > maxProgress) {
                    mBinding.progressView.setVisibility(View.GONE);
                }
            }
        });

        mBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings = mBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private void initParameter() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mUrl = intent.getStringExtra(URL);
        mTitle = intent.getStringExtra(TITLE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果不做任何处理，浏览网页，点击系统“Back”键，整个Browser会调用finish()而结束自身，
        // 如果希望浏览的网 页回退而不是推出浏览器，需要在当前Activity中处理并消费掉该Back事件。
        if (keyCode == KeyEvent.KEYCODE_BACK && mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //释放资源
        if (mBinding != null) {
            final ViewGroup viewGroup = (ViewGroup) mBinding.webView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mBinding.webView);
            }
            mBinding.webView.destroy();
        }
        super.onDestroy();
    }

}
