package com.byth.lifesaver.api;

import com.byth.lifesaver.api.request.MineServiceRequest;
import com.byth.lifesaver.bean.CustomerInfoBean;
import com.byth.lifesaver.bean.UserInfoBean;
import com.byth.lifesaver.http.HttpMethod;
import com.byth.lifesaver.http.HttpResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/9/4 0004.
 * 我的页面api
 */

public class MineAPI extends HttpMethod {
    private volatile static MineAPI mineAPI;
    private MineServiceRequest request = retrofitBuild().create(MineServiceRequest.class);

    private MineAPI() {

    }

    public static MineAPI getInstance() {
        if (mineAPI == null) {
            synchronized (MineAPI.class) {
                if (mineAPI == null) {
                    mineAPI = new MineAPI();
                }
            }
        }
        return mineAPI;
    }

    //获取用户信息
    public void getUserInfo(Subscriber<HttpResult<CustomerInfoBean>> subscriber, RequestBody body) {
        Observable observable = request.getUserInfo(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //新增地址
    public void addAddress(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = request.addAddress(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //上传头像
    public void uploadAvatar(Subscriber<HttpResult<CustomerInfoBean>> subscriber, MultipartBody.Part body) {
        Observable observable = request.uploadAvatar(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //修改地址
    public void modifyAddress(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = request.modifyAddress(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //修改用户信息
    public void modifyUserInfo(Subscriber<HttpResult<UserInfoBean>> subscriber, RequestBody body) {
        Observable observable = request.modifyUserInfo(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
}