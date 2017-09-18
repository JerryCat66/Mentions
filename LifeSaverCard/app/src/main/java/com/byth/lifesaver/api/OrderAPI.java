package com.byth.lifesaver.api;

import com.byth.lifesaver.api.request.OrderServiceRequest;
import com.byth.lifesaver.bean.AddressListBean;
import com.byth.lifesaver.bean.OrderBean;
import com.byth.lifesaver.bean.OrderDetailBean;
import com.byth.lifesaver.bean.OrderPageBean;
import com.byth.lifesaver.bean.PayResultBean;
import com.byth.lifesaver.bean.WeChatRechargeBean;
import com.byth.lifesaver.http.HttpMethod;
import com.byth.lifesaver.http.HttpResult;

import java.util.List;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/8/7 0007.
 * 订单api
 */

public class OrderAPI extends HttpMethod {
    private volatile static OrderAPI orderAPI;
    private OrderServiceRequest request = retrofitBuild().create(OrderServiceRequest.class);

    private OrderAPI() {

    }

    public static OrderAPI getInstance() {
        if (orderAPI == null) {
            synchronized (OrderAPI.class) {
                if (orderAPI == null) {
                    orderAPI = new OrderAPI();
                }
            }
        }
        return orderAPI;
    }

    //获取订单列表
    public void getOrderList(Subscriber<HttpResult<List<OrderBean.data>>> subscriber, RequestBody body) {
        Observable observable = request.getOrderList(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //获取订单详情
    public void getOrderDetail(Subscriber<HttpResult<OrderDetailBean>> subscriber, RequestBody body) {
        Observable observable = request.getOrderDetail(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //获取订单最大页
    public void getMaxPage(Subscriber<HttpResult<OrderPageBean>> subscriber, RequestBody body) {
        Observable observable = request.getMaxPage(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //获取地址
    public void getAddressList(Subscriber<HttpResult<AddressListBean>> subscriber, RequestBody body) {
        Observable observable = request.getAddressList(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //获取调起微信支付参数
    public void getWeChatPayInfo(Subscriber<HttpResult<WeChatRechargeBean>> subscriber, RequestBody body) {
        Observable observable = request.getWeChatPayInfo(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //获取支付结果
    public void getPayResultInfo(Subscriber<HttpResult<PayResultBean>> subscriber, RequestBody body) {
        Observable observable = request.getPayResult(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //确认收货
    public void receiveConfirm(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = request.receiveConfirm(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
}
