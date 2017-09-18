package com.byth.lifesaver.api.request;

import com.byth.lifesaver.bean.CardRenewBean;
import com.byth.lifesaver.bean.EmergencyCallBean;
import com.byth.lifesaver.bean.HomePictureListBean;
import com.byth.lifesaver.http.HttpResult;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/8/9 0009.
 * 首页接口
 */

public interface HomeServiceRequest {
    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/allowToOpperatCard")
    Observable<HttpResult<CardRenewBean>> getIsRenew(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("common/indexCarousel")
    Observable<HttpResult<HomePictureListBean>> getHomeBanner(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/allowToCall")
    Observable<HttpResult<EmergencyCallBean>> isAllowedCall(@Body RequestBody body);
}
