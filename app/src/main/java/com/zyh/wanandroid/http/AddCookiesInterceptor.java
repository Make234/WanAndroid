package com.zyh.wanandroid.http;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zyh.wanandroid.bean.RegisterLogin;
import com.zyh.wanandroid.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author 88421876
 * @date 2019/1/17
 */
class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        //请求发起的时间
        long t1 = System.nanoTime();
        RegisterLogin user = SharedPreferencesUtil.getInstance().getUser();
        if (user != null) {
            builder.addHeader("Cookie", "loginUserName=" + user.getUsername());
            builder.addHeader("Cookie", "loginUserPassword=" + user.getPassword());
        }
        //收到响应的时间
        Request request = builder.build();
        long t2 = System.nanoTime();
        Log.i("zyh", String.format("发送请求 %s on %s%n%s",
                request.url(), request.toString(), request.headers()));

        Response response = chain.proceed(request);
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        String responseStr = String.format(Locale.getDefault(),
                "接收响应: [%s] %n返回json:【%s】 %.1fms%n%s",
                response.request().url(),
                responseBody.string(),
                (t2 - t1) / 1e6d,
                response.headers());
        Log.i("zyh", responseStr);
        return response;
    }
}
