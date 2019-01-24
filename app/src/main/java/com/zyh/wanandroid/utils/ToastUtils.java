package com.zyh.wanandroid.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.zyh.wanandroid.MyApplication;

/**
 *
 * @author zyh
 *
 */

public class ToastUtils {

    public static void toastShort(@StringRes int res) {
        Toast.makeText(MyApplication.getInstance(), res, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(String text) {
        Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(String text) {
        Toast.makeText(MyApplication.getInstance(), text, Toast.LENGTH_LONG).show();
    }

    public static void toastLong(@StringRes int res) {
        Toast.makeText(MyApplication.getInstance(), res, Toast.LENGTH_LONG).show();
    }
}
