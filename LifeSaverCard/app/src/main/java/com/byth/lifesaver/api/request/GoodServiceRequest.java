package com.byth.lifesaver.api.request;

import com.byth.lifesaver.bean.CardActiveSuccessBean;
import com.byth.lifesaver.bean.CardDetailBean;
import com.byth.lifesaver.bean.CardInfoBean;
import com.byth.lifesaver.bean.CardReissueBean;
import com.byth.lifesaver.bean.CardReissueConfirmBean;
import com.byth.lifesaver.bean.CardRenewBean;
import com.byth.lifesaver.bean.CardRenewConfirmBean;
import com.byth.lifesaver.bean.DefaultAddressBean;
import com.byth.lifesaver.bean.GoodAddressBean;
import com.byth.lifesaver.bean.GoodInfoBean;
import com.byth.lifesaver.bean.OrderSuccessBean;
import com.byth.lifesaver.bean.WxInfoBean;
import com.byth.lifesaver.http.HttpResult;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/28 0028.
 * 商品信息api
 */

public interface GoodServiceRequest {
    @GET("card/goods")
    Observable<HttpResult<GoodInfoBean>> getGoodsInfo(@QueryMap Map<String, String> map);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/defAddress")
    Observable<HttpResult<GoodAddressBean>> getDefaultAddress(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/cfmInfo")
    Observable<HttpResult<CardDetailBean>> getCardDetail(@Body RequestBody body);

    @Multipart
    @POST("card/upload")
    Observable<HttpResult<CardActiveSuccessBean>> uploadCard(@Part MultipartBody.Part part);


    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/active")
    Observable<HttpResult<CardActiveSuccessBean>> getCardActiveSuccessInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/orderTaker")
    Observable<HttpResult<OrderSuccessBean>> getPurchaseInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/weixinLg")
    Observable<HttpResult<WxInfoBean>> getWxLoginInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/weixinLg_regist")
    Observable<HttpResult<WxInfoBean>> quickRegister(@Body RequestBody body);


    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/weixinLg_contact")
    Observable<HttpResult<WxInfoBean>> loginBinding(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/renew")
    Observable<HttpResult<CardRenewBean>> getCardRenewInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/renewConfirm")
    Observable<HttpResult<CardRenewConfirmBean>> getCardRenewConfirmInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/renewToPay")
    Observable<HttpResult<OrderSuccessBean>> payForRenew(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/obtainLifeCardInfo")
    Observable<HttpResult<CardInfoBean>> getCardInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/toMakeUp")
    Observable<HttpResult<CardReissueBean>> getCardReissueInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/mkupOrderTaker")
    Observable<HttpResult<CardReissueConfirmBean>> getCardReissueConfirmInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/mkupOrderTaker")
    Observable<HttpResult<OrderSuccessBean>> reissueCardOrder(@Body RequestBody body);

}
