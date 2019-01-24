package com.zyh.wanandroid.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.bean.RegisterLogin;

/**
 * @author 88421876
 * @date 2019/1/21
 */
public class SharedPreferencesUtil {
    private static final String KEY_USER = "user";
    private static volatile SharedPreferencesUtil instance;
    private SharedPreferences sharedPreferences;

    public static SharedPreferencesUtil getInstance() {
        if (instance == null) {
            synchronized (SharedPreferencesUtil.class) {
                if (instance == null) {
                    instance = new SharedPreferencesUtil();
                }
            }
        }
        return instance;
    }

    private SharedPreferencesUtil() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
    }

    public void saveUser(RegisterLogin registerLogin) {
        if (registerLogin == null) {
            sharedPreferences.edit().putString(KEY_USER, "").apply();
            return;
        }
        sharedPreferences.edit().putString(KEY_USER, new Gson().toJson(registerLogin)).apply();
    }

    public void clearUser() {
        sharedPreferences.edit().remove(KEY_USER).apply();
    }

    public RegisterLogin getUser() {
        return new Gson().fromJson(sharedPreferences.getString(KEY_USER, ""), RegisterLogin.class);
    }

    public String getUserImgUrl() {
        String url = "";
        RegisterLogin user = getUser();
        if (user != null) {
            url = user.getIcon();
        }
        return url;
    }
}
