package com.byth.lifesaver.api.request;


import com.byth.lifesaver.bean.CustomerInfoBean;
import com.byth.lifesaver.bean.UserInfoBean;
import com.byth.lifesaver.http.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/20 0020.
 * retrofit请求服务 auth Post请求接口
 */

public interface AuthServiceRequest {
    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("SMS/getIdenCode")
    Observable<HttpResult> registerCode(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/regist")
    Observable<HttpResult> register(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/login")
    Observable<HttpResult<UserInfoBean>> login(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("SMS/checkRandomCode")
    Observable<HttpResult> checkRandomCode(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/changePwd")
    Observable<HttpResult> modifyPassword(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/changePhone")
    Observable<HttpResult<CustomerInfoBean>> modifyPhone(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/changePhone_validatePwd")
    Observable<HttpResult> modifyPhoneWithPsw(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/fbPwd")
    Observable<HttpResult> findBackPsw(@Body RequestBody body);
}