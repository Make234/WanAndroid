package com.zyh.wanandroid.http;

import com.zyh.wanandroid.bean.Banner;
import com.zyh.wanandroid.bean.HomePageBean;
import com.zyh.wanandroid.bean.HomePageDetail;
import com.zyh.wanandroid.bean.Knowledge;
import com.zyh.wanandroid.bean.Navigation;
import com.zyh.wanandroid.bean.Project;
import com.zyh.wanandroid.bean.ProjectTitle;
import com.zyh.wanandroid.bean.RegisterLogin;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.bean.ResultListBean;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zyh
 * @date 2019/1/17
 */
public class HttpHelper {
    private static final int TIME_OUT = 6;
    private HttpService httpService;
    private static volatile HttpHelper instance = null;

    private HttpHelper() {
        OkHttpClient client = new OkHttpClient.Builder()
                //统一处理get形式的请求数据
                .addInterceptor(new AddCookiesInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }

    public void getHomePageList(Observer<ResultBean<HomePageBean>> observer, int page) {
        httpService.getHomePageList(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void login(Observer<ResultBean<RegisterLogin>> observer, HashMap<String, String> map) {
        httpService.login(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void loginOut(Observer<ResultBean> observer) {
        httpService.loginOut()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void register(Observer<ResultBean<RegisterLogin>> observer, HashMap<String, String> map) {
        httpService.register(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void collect(Observer<ResultBean> observer, int id) {
        httpService.collect(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void unCollect(Observer<ResultBean> observer, int id) {
        httpService.unCollect(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void unCollect(Observer<ResultBean> observer, int id, int originId) {
        httpService.unCollect(id, originId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getBannerUrls(Observer<ResultListBean<Banner>> observer) {
        httpService.getBannerUrls()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getKnowLedge(Observer<ResultListBean<Knowledge>> observer) {
        httpService.getKnowLedge()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void getNavigation(Observer<ResultListBean<Navigation>> observer) {
        httpService.getNavigation()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void getArticleList(Observer<ResultBean<HomePageBean>> observer, int page, int id) {
        httpService.getArticleList(page, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public void getProjectTitle(Observer<ResultListBean<ProjectTitle>> observer) {
        httpService.getProjectTitle()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getProject(Observer<ResultBean<Project>> observer, int page, int id) {
        httpService.getProject(page, id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCollectList(Observer<ResultBean<HomePageBean>> observer, int page) {
        httpService.getCollectList(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
