package com.zyh.wanandroid.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.bean.RegisterLogin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zyh
 * @date 2019/1/21
 */
public class SharedPreferencesUtil {
    private static final String KEY_USER = "user";
    private static final String KEY_HISTORY = "history";
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

    public void saveHistory(String historyStr) {
        String oldHistoryStr = sharedPreferences.getString(KEY_HISTORY, "");
        if (oldHistoryStr != null) {
            if (oldHistoryStr.contains(historyStr) || historyStr.isEmpty()) {
                return;
            }
            String[] split = oldHistoryStr.split(",");
            List<String> historyList = Arrays.asList(split);
            int maxSize = 20;
            if (historyList.size() > maxSize) {
                int lastIndexOf = oldHistoryStr.lastIndexOf(",");
                if (lastIndexOf < 0) {
                    return;
                }
                String substring = oldHistoryStr.substring(0, lastIndexOf);
                sharedPreferences.edit().putString(KEY_HISTORY, historyStr + "," + substring).apply();
                return;

            }
        }
        sharedPreferences.edit().putString(KEY_HISTORY, historyStr + "," + oldHistoryStr).apply();
    }

    public void clearHistory() {
        sharedPreferences.edit().remove(KEY_HISTORY).apply();
    }

    public ArrayList<String> getHistory() {
        ArrayList<String> history = new ArrayList<>();
        String oldHistoryStr = sharedPreferences.getString(KEY_HISTORY, "");
        if ("".equals(oldHistoryStr)) {
            return history;
        }
        if (oldHistoryStr != null) {
            String[] split = oldHistoryStr.split(",");
            history.addAll(Arrays.asList(split));
        }
        return history;
    }
}
