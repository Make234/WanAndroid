package com.zyh.wanandroid.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.bean.RegisterLogin;
import com.zyh.wanandroid.databinding.ActivityRegisterLoginBinding;
import com.zyh.wanandroid.utils.BaseOnClickListener;
import com.zyh.wanandroid.utils.SharedPreferencesUtil;
import com.zyh.wanandroid.utils.ToastUtils;
import com.zyh.wanandroid.vm.RegisterLoginViewModel;

import java.util.HashMap;
import java.util.List;

/**
 * @author zyh
 */
public class RegisterLoginActivity extends BaseActivity<RegisterLogin> implements View.OnClickListener, TextWatcher {
    private static final int LOGIN = 1;
    private static final int REGISTER = 2;
    ActivityRegisterLoginBinding mBinding;
    String passWord;
    /**
     * LOGIN 登陆 REGISTER 注册
     */
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_login);
        initToolbar();
        initView();
    }

    private void initView() {
        hideLoading();
        mBinding.tvRegister.setOnClickListener(this);
        mBinding.tvOk.setOnClickListener(this);
        mBinding.tetPsw.addTextChangedListener(this);
        mBinding.tetPswAgain.addTextChangedListener(this);
        mBinding.tetUserName.addTextChangedListener(this);
    }

    private void initToolbar() {
        type = LOGIN;
        mBinding.toolbar.getLeftImageView().setOnClickListener(new BaseOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                finish();
            }
        });
        mBinding.toolbar.getCenterTextView().setText(("登陆"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                if (type == LOGIN) {
                    type = REGISTER;
                    mBinding.tilPswAgain.setVisibility(View.VISIBLE);
                    mBinding.tvRegister.setText(getString(R.string.go_login));
                    mBinding.tvOk.setText("去注册");
                    mBinding.toolbar.getCenterTextView().setText(("注册"));
                } else {
                    type = LOGIN;
                    mBinding.tilPswAgain.setVisibility(View.GONE);
                    mBinding.tvRegister.setText(getString(R.string.go_register));
                    mBinding.tvOk.setText("去登陆");
                    mBinding.toolbar.getCenterTextView().setText(("登陆"));
                }
                break;
            case R.id.tv_ok:
                checkStyle(type == LOGIN);
                break;
            default:
                break;
        }
    }

    private void checkStyle(boolean isLogin) {
        Editable userText = mBinding.tetUserName.getText();
        if (userText == null || userText.toString().isEmpty()) {
            mBinding.tilUserName.setError("用户名为空");
            mBinding.tetUserName.requestFocus();
            return;
        }

        Editable pswText = mBinding.tetPsw.getText();
        if (pswText == null) {
            mBinding.tilPsw.setError("密码为空");
            return;
        }
        passWord = pswText.toString();
        if (passWord.isEmpty()) {
            mBinding.tilPsw.setError("密码为空");
            mBinding.tetPsw.requestFocus();
            return;
        } else {
            int minLength = 6;
            if (pswText.length() < minLength) {
                mBinding.tilPsw.setError("密码长度小于6位，请重新输入");
                mBinding.tetPsw.requestFocus();
            }
        }

        if (!isLogin) {
            Editable pswAgainText = mBinding.tetPswAgain.getText();
            if (pswAgainText == null || pswAgainText.toString().isEmpty()
                    || !pswAgainText.toString().equals(passWord)) {
                mBinding.tilPswAgain.setError("两次密码输入不一致，请重新输入");
                mBinding.tetPswAgain.requestFocus();
                return;
            }
        }
        RegisterLoginViewModel viewModel = new RegisterLoginViewModel();
        viewModel.attachView(this);
        HashMap<String, String> map = new HashMap<>(16);
        map.put("username", userText.toString());
        map.put("password", passWord);
        showLoading();
        if (isLogin) {
            viewModel.login(map);
        } else {
            map.put("repassword", passWord);
            viewModel.register(map);
        }
    }

    @Override
    public void onSuccess(RegisterLogin data) {
        data.setPassword(passWord);
        SharedPreferencesUtil.getInstance().saveUser(data);
        finish();
    }

    @Override
    public void onSuccess(List<RegisterLogin> data) {

    }

    @Override
    public void onError(String msg) {
        ToastUtils.toastShort(msg);
    }

    @Override
    public void complete() {

    }

    @Override
    public void showLoading() {
        mBinding.loadingProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mBinding.loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mBinding != null) {
            mBinding.tilPsw.setError("");
            mBinding.tilPswAgain.setError("");
            mBinding.tilUserName.setError("");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
