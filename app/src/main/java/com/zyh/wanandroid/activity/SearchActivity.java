package com.zyh.wanandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.databinding.ActivitySearchBinding;
import com.zyh.wanandroid.utils.DimenUtil;
import com.zyh.wanandroid.utils.SharedPreferencesUtil;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.widgets.MultiModeView;

import java.util.ArrayList;

import static com.zyh.wanandroid.activity.ArticleListActivity.SEARCH_LIST;
import static com.zyh.wanandroid.activity.ArticleListActivity.TITLE;
import static com.zyh.wanandroid.activity.ArticleListActivity.TYPE;

/**
 * @author zyh
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySearchBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
    }

    private void initView() {
        mBinding.ivBack.setOnClickListener(this);
        mBinding.tvSearch.setOnClickListener(this);
        mBinding.clearHistory.setOnClickListener(this);
        ArrayList<String> history = SharedPreferencesUtil.getInstance().getHistory();
        mBinding.clearHistory.setVisibility(history.isEmpty() ? View.GONE : View.VISIBLE);
        mBinding.tvHistory.setVisibility(history.isEmpty() ? View.GONE : View.VISIBLE);
        mBinding.history.removeAllViews();
        for (int i = 0; i < history.size(); i++) {
            int padding = DimenUtil.dp2px(this, 12);
            TextView textView = new TextView(this);
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
            textView.setBackgroundResource(R.drawable.history_bg);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimenUtil.sp2px(this, 14));
            textView.setGravity(Gravity.CENTER);

            textView.setPadding(padding, padding, padding, padding);
            String historyStr = history.get(i);
            if (history.isEmpty()) {
                return;
            }
            textView.setText(historyStr);

            textView.setOnClickListener(v -> goSearch(historyStr));
            mBinding.history.addView(textView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                goSearch(mBinding.search.getText().toString());
                break;
            case R.id.clear_history:
                SharedPreferencesUtil.getInstance().clearHistory();
                initView();
                break;
            default:
                break;
        }
    }

    private void goSearch(String key) {
        if (key.isEmpty()) {
            ToastUtils.toastShort("请输入搜索内容");
            return;
        }
        SharedPreferencesUtil.getInstance().saveHistory(key);
        Intent intent = new Intent(this, ArticleListActivity.class);
        intent.putExtra(TYPE, SEARCH_LIST);
        intent.putExtra(TITLE, "搜索");
        intent.putExtra("key", key);
        startActivity(intent);
    }

}
