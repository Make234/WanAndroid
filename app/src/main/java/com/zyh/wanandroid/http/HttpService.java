package com.zyh.wanandroid.http;

import com.zyh.wanandroid.bean.Banner;
import com.zyh.wanandroid.bean.HomePageBean;
import com.zyh.wanandroid.bean.Knowledge;
import com.zyh.wanandroid.bean.Navigation;
import com.zyh.wanandroid.bean.Project;
import com.zyh.wanandroid.bean.ProjectTitle;
import com.zyh.wanandroid.bean.RegisterLogin;
import com.zyh.wanandroid.bean.ResultBean;
import com.zyh.wanandroid.bean.ResultListBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author zyh
 * @date 2019/1/17
 */
public interface HttpService {
    String BASE_URL = "http://wanandroid.com/";

    /**
     * 首页
     *
     * @param page 页码
     * @return ResultBean<HomePageBean>
     */
    @GET("article/list/{page}/json")
    Observable<ResultBean<HomePageBean>> getHomePageList(@Path("page") int page);

    /**
     * 登录
     *
     * @param map 账号，密码
     * @return ResultBean<RegisterLogin>
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<ResultBean<RegisterLogin>> login(@FieldMap HashMap<String, String> map);

    /**
     * 注册
     *
     * @param map 账号，密码
     * @return ResultBean<RegisterLogin>
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<ResultBean<RegisterLogin>> register(@FieldMap HashMap<String, String> map);

    /**
     * 注销
     *
     * @return ResultBean
     */
    @GET("user/logout/json")
    Observable<ResultBean> loginOut();

    /**
     * 收藏
     *
     * @param id 文章id
     * @return ResultBean
     */
    @POST("lg/collect/{id}/json")
    Observable<ResultBean> collect(@Path("id") int id);

    /**
     * 取消收藏
     *
     * @param id 文章id
     * @return ResultBean
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<ResultBean> unCollect(@Path("id") int id);

    /**
     * 取消收藏(我的收藏列表)
     *
     * @param id 文章id
     * @param originId 收藏列表下发，无则传 -1
     * @return ResultBean
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<ResultBean> unCollect(@Path("id") int id, @Field("originId") int originId);


    /**
     * 首页banner
     *
     * @return <ResultListBean<Banner>>
     */
    @GET("banner/json")
    Observable<ResultListBean<Banner>> getBannerUrls();


    /**
     * 知识体系
     *
     * @return <ResultBean<Knowledge>>
     */
    @GET("tree/json")
    Observable<ResultListBean<Knowledge>> getKnowLedge();


    /**
     * 导航
     *
     * @return ResultListBean<Navigation>
     */
    @GET("navi/json")
    Observable<ResultListBean<Navigation>> getNavigation();


    /**
     * 知识体系下的文章
     *
     * @param page 页数
     * @param id   文章id
     * @return ResultBean<HomePageBean>
     */
    @GET("article/list/{page}/json")
    Observable<ResultBean<HomePageBean>> getArticleList(@Path("page") int page, @Query("cid") int id);


    /**
     * 项目分类
     *
     * @return ResultListBean<ProjectTitle>
     */
    @GET("project/tree/json")
    Observable<ResultListBean<ProjectTitle>> getProjectTitle();

    /**
     * 项目
     *
     * @param id   项目id
     * @param page 页数
     * @return ResultBean<Project>>
     */
    @GET("project/list/{page}/json")
    Observable<ResultBean<Project>> getProject(@Path("page") int page, @Query("cid") int id);

    /**
     * 收藏列表
     *
     * @param page 页数
     * @return ResultBean<HomePageBean>
     */
    @GET("lg/collect/list/{page}/json")
    Observable<ResultBean<HomePageBean>> getCollectList(@Path("page") int page);
}
