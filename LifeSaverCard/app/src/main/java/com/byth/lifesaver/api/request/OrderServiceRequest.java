package com.byth.lifesaver.api.request;

import com.byth.lifesaver.bean.AddressListBean;
import com.byth.lifesaver.bean.OrderBean;
import com.byth.lifesaver.bean.OrderDetailBean;
import com.byth.lifesaver.bean.OrderPageBean;
import com.byth.lifesaver.bean.PayResultBean;
import com.byth.lifesaver.bean.WeChatRechargeBean;
import com.byth.lifesaver.http.HttpResult;


import java.util.List;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/8/7 0007.
 * 订单服务请求
 */

public interface OrderServiceRequest {
    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/myOrder")
    Observable<HttpResult<List<OrderBean.data>>> getOrderList(@Body RequestBody body);


    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/orderDetail")
    Observable<HttpResult<OrderDetailBean>> getOrderDetail(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/obtainMaxPage")
    Observable<HttpResult<OrderPageBean>> getMaxPage(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/deliveryAddrAdmin")
    Observable<HttpResult<AddressListBean>> getAddressList(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("extUser/toPrePay")
    Observable<HttpResult<WeChatRechargeBean>> getWeChatPayInfo(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/orderPaySuccess")
    Observable<HttpResult<PayResultBean>> getPayResult(@Body RequestBody body);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//转换成json格式
    @POST("card/recvConfm")
    Observable<HttpResult> receiveConfirm(@Body RequestBody body);
}
