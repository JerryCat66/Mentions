package com.byth.lifesaver.api;

import com.byth.lifesaver.api.request.HomeServiceRequest;
import com.byth.lifesaver.bean.CardRenewBean;
import com.byth.lifesaver.bean.EmergencyCallBean;
import com.byth.lifesaver.bean.HomePictureListBean;
import com.byth.lifesaver.http.HttpMethod;
import com.byth.lifesaver.http.HttpResult;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/8/9 0009.
 * 首页api
 */

public class HomeAPI extends HttpMethod {
    private volatile static HomeAPI homeAPI;
    private HomeServiceRequest request = retrofitBuild().create(HomeServiceRequest.class);

    private HomeAPI() {

    }

    public static HomeAPI getInstance() {
        if (homeAPI == null) {
            synchronized (HomeAPI.class) {
                if (homeAPI == null) {
                    homeAPI = new HomeAPI();
                }
            }
        }
        return homeAPI;
    }

    //获取是否可以续卡标志
    public void getIsRenewTag(Subscriber<HttpResult<CardRenewBean>> subscriber, RequestBody body) {
        Observable observable = request.getIsRenew(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //获取首页轮播图
    public void getHomeBannerList(Subscriber<HttpResult<HomePictureListBean>> subscriber, RequestBody body) {
        Observable observable = request.getHomeBanner(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //是否可以拨打紧急救援电话
    public void isAllowedCall(Subscriber<HttpResult<EmergencyCallBean>> subscriber, RequestBody body) {
        Observable observable = request.isAllowedCall(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
}
