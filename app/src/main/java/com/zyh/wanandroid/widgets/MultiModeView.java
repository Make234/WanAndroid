package com.zyh.wanandroid.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.utils.DimenUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 多模式view (网络加载失败，无网络，无数据，loading)
 *
 * @author zyh
 * @date 2019/1/24
 */
public class MultiModeView extends RelativeLayout {
    static final int DEFAULT_NONE_RES = 0;
    static final String DEFAULT_TEXT_COLOR = "#999999";
    static final int DEFAULT_TEXT_SIZE = 14;
    private int noDataViewRes;
    private int netWorkViewRes;
    private int errorViewRes;
    private int loadingViewRes;

    private int defaultImageWidth;
    private int defaultImageHeight;

    private int noDataImageRes;
    private int netWorkImageRes;
    private int errorImageRes;

    private String noDataText;
    private String netWorkText;
    private String errorText;

    private int defaultTextColor;
    private int defaultTextSize;

    private View noDataView;
    private View netWorkView;
    private View errorView;
    private View loadingView;

    private OnNetWorkClick onNetWorkClick;
    private OnNoDataClick onNoDataClick;
    private OnErrorClick onErrorClick;

    public MultiModeView(Context context) {
        this(context, null);
    }

    public MultiModeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);

        initView(context);
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MultiModeView);
        noDataViewRes = array.getResourceId(R.styleable.MultiModeView_noDataView, DEFAULT_NONE_RES);
        netWorkViewRes = array.getResourceId(R.styleable.MultiModeView_netWorkView, DEFAULT_NONE_RES);
        errorViewRes = array.getResourceId(R.styleable.MultiModeView_errorView, DEFAULT_NONE_RES);
        loadingViewRes = array.getResourceId(R.styleable.MultiModeView_loadingView, DEFAULT_NONE_RES);

        defaultImageWidth = (int) array.getDimension(R.styleable.MultiModeView_defaultImageWidth, DimenUtil.dp2px(context, 80));
        defaultImageHeight = (int) array.getDimension(R.styleable.MultiModeView_defaultImageHeight, DimenUtil.dp2px(context, 80));

        noDataImageRes = array.getResourceId(R.styleable.MultiModeView_defaultNoDataImage, DEFAULT_NONE_RES);
        netWorkImageRes = array.getResourceId(R.styleable.MultiModeView_defaultNetWorkImage, DEFAULT_NONE_RES);
        errorImageRes = array.getResourceId(R.styleable.MultiModeView_defaultErrorImage, DEFAULT_NONE_RES);

        noDataText = array.getString(R.styleable.MultiModeView_noDataText);
        netWorkText = array.getString(R.styleable.MultiModeView_netWorkText);
        errorText = array.getString(R.styleable.MultiModeView_errorText);
        defaultTextColor = array.getColor(R.styleable.MultiModeView_defaultTextColor, Color.parseColor(DEFAULT_TEXT_COLOR));
        defaultTextSize = (int) array.getDimension(R.styleable.MultiModeView_defaultTextSize, DEFAULT_TEXT_SIZE);

        array.recycle();
    }

    private void initView(Context context) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        initNoDataViw(context, layoutParams);
        initNetWorkViw(context, layoutParams);
        initErrorViw(context, layoutParams);
        initLoadingView(context);
        setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        showLoading();
    }

    private void initNoDataViw(Context context, LayoutParams layoutParams) {
        if (noDataViewRes != DEFAULT_NONE_RES) {
            noDataView = LayoutInflater.from(context).inflate(noDataViewRes, this, false);
        } else {
            noDataView = new LinearLayout(context);
            ((LinearLayout) noDataView).setOrientation(LinearLayout.VERTICAL);

            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            ivParams.gravity = Gravity.CENTER_HORIZONTAL;
            ivParams.setMargins(0, 0, 0, DimenUtil.dp2px(context, 10));
            if (defaultImageHeight != DEFAULT_NONE_RES) {
                ivParams.height = defaultImageHeight;
            }
            if (defaultImageWidth != DEFAULT_NONE_RES) {
                ivParams.width = defaultImageWidth;
            }
            imageView.setLayoutParams(ivParams);
            imageView.setImageDrawable(ContextCompat.getDrawable(context, noDataImageRes != DEFAULT_NONE_RES
                    ? noDataImageRes : R.drawable.no_data));

            TextView textView = new TextView(context);
            textView.setText(noDataText != null && !noDataText.isEmpty() ? noDataText : "暂无数据");
            textView.setTextSize(defaultTextSize != DEFAULT_NONE_RES ? defaultTextSize
                    : DimenUtil.dp2px(context, DEFAULT_TEXT_SIZE));
            textView.setTextColor(defaultTextColor != DEFAULT_NONE_RES ? defaultTextColor
                    : Color.parseColor(DEFAULT_TEXT_COLOR));
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            tvParams.gravity = Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(tvParams);
            ((LinearLayout) noDataView).addView(imageView);
            ((LinearLayout) noDataView).addView(textView);
        }
        noDataView.setLayoutParams(layoutParams);
        noDataView.setOnClickListener(v -> {
            if (onNoDataClick != null) {
                onNoDataClick.onNoDataClickListener(v);
            }
        });
        addView(noDataView);
    }


    private void initNetWorkViw(Context context, LayoutParams layoutParams) {
        if (netWorkViewRes != DEFAULT_NONE_RES) {
            netWorkView = LayoutInflater.from(context).inflate(netWorkViewRes, this, false);
        } else {
            netWorkView = new LinearLayout(context);
            ((LinearLayout) netWorkView).setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            ivParams.gravity = Gravity.CENTER_HORIZONTAL;
            ivParams.setMargins(0, 0, 0, DimenUtil.dp2px(context, 10));
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (defaultImageHeight != DEFAULT_NONE_RES) {
                ivParams.height = defaultImageHeight;
            }
            if (defaultImageWidth != DEFAULT_NONE_RES) {
                ivParams.width = defaultImageWidth;
            }
            imageView.setLayoutParams(ivParams);
            imageView.setImageDrawable(ContextCompat.getDrawable(context, netWorkImageRes != DEFAULT_NONE_RES
                    ? netWorkImageRes : R.drawable.no_data));

            TextView textView = new TextView(context);
            textView.setText(netWorkText != null && !netWorkText.isEmpty() ? netWorkText : "无网络，请检查网络");
            textView.setTextSize(defaultTextSize != DEFAULT_NONE_RES ? defaultTextSize
                    : DimenUtil.dp2px(context, DEFAULT_TEXT_SIZE));
            textView.setTextColor(defaultTextColor != DEFAULT_NONE_RES ? defaultTextColor
                    : Color.parseColor(DEFAULT_TEXT_COLOR));
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            tvParams.gravity = Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(tvParams);

            ((LinearLayout) netWorkView).addView(imageView);
            ((LinearLayout) netWorkView).addView(textView);
        }
        netWorkView.setLayoutParams(layoutParams);
        netWorkView.setOnClickListener(v -> {
            if (onNetWorkClick != null) {
                onNetWorkClick.onNetWorkClickListener(v);
            }
        });
        addView(netWorkView);
    }

    private void initErrorViw(Context context, LayoutParams layoutParams) {
        if (errorViewRes != DEFAULT_NONE_RES) {
            errorView = LayoutInflater.from(context).inflate(errorViewRes, this, false);
        } else {
            errorView = new LinearLayout(context);
            ((LinearLayout) errorView).setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            ivParams.gravity = Gravity.CENTER_HORIZONTAL;
            ivParams.setMargins(0, 0, 0, DimenUtil.dp2px(context, 10));
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            if (defaultImageHeight != DEFAULT_NONE_RES) {
                ivParams.height = defaultImageHeight;
            }
            if (defaultImageWidth != DEFAULT_NONE_RES) {
                ivParams.width = defaultImageWidth;
            }
            imageView.setLayoutParams(ivParams);
            imageView.setImageDrawable(ContextCompat.getDrawable(context, errorImageRes != DEFAULT_NONE_RES
                    ? errorImageRes : R.drawable.no_data));

            TextView textView = new TextView(context);
            textView.setText(errorText != null && !errorText.isEmpty() ? errorText : "请求出错了");
            textView.setTextSize(defaultTextSize != DEFAULT_NONE_RES ? defaultTextSize
                    : DimenUtil.dp2px(context, DEFAULT_TEXT_SIZE));
            textView.setTextColor(defaultTextColor != DEFAULT_NONE_RES ? defaultTextColor
                    : Color.parseColor(DEFAULT_TEXT_COLOR));
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            tvParams.gravity = Gravity.CENTER_HORIZONTAL;
            textView.setLayoutParams(tvParams);

            ((LinearLayout) errorView).addView(imageView);
            ((LinearLayout) errorView).addView(textView);
        }
        errorView.setLayoutParams(layoutParams);
        errorView.setOnClickListener(v -> {
            if (onErrorClick != null) {
                onErrorClick.onErrorClickListener(v);
            }
        });
        addView(errorView);
    }

    private void initLoadingView(Context context) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                DimenUtil.dp2px(context, 50), DimenUtil.dp2px(context, 50));
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        if (loadingViewRes == DEFAULT_NONE_RES) {
            loadingViewRes = R.layout.content_loading;
        }
        loadingView = LayoutInflater.from(context).inflate(loadingViewRes, this, false);
        loadingView.setLayoutParams(layoutParams);
        addView(loadingView);
    }

    public void showLoading() {
        showView(loadingView);
    }

    public void showEmpty() {
        showView(noDataView);
    }

    public void showError() {
        showView(errorView);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    public void showNetWork() {
        showView(netWorkView);
    }

    private void showView(View showView) {
        setVisibility(VISIBLE);
        noDataView.setVisibility(showView == noDataView ? VISIBLE : GONE);
        netWorkView.setVisibility(showView == netWorkView ? VISIBLE : GONE);
        errorView.setVisibility(showView == errorView ? VISIBLE : GONE);
        loadingView.setVisibility(showView == loadingView ? VISIBLE : GONE);
    }

    public void setOnNetWorkClick(OnNetWorkClick onNetWorkClick) {
        this.onNetWorkClick = onNetWorkClick;
    }

    public void setOnNoDataClick(OnNoDataClick onNoDataClick) {
        this.onNoDataClick = onNoDataClick;
    }

    public void setOnErrorClick(OnErrorClick onErrorClick) {
        this.onErrorClick = onErrorClick;
    }

    public interface OnNoDataClick {
        /**
         * 无数据点击事件
         *
         * @param view view
         */
        void onNoDataClickListener(View view);
    }

    public interface OnErrorClick {
        /**
         * 请求错误点击事件
         *
         * @param view view
         */
        void onErrorClickListener(View view);
    }

    public interface OnNetWorkClick {
        /**
         * 无网络
         *
         * @param view view
         */
        void onNetWorkClickListener(View view);
    }
}
