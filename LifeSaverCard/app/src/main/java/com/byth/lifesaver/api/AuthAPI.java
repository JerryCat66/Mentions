package com.byth.lifesaver.api;

import com.byth.lifesaver.bean.CustomerInfoBean;
import com.byth.lifesaver.bean.UserInfoBean;
import com.byth.lifesaver.http.HttpMethod;
import com.byth.lifesaver.http.HttpResult;
import com.byth.lifesaver.api.request.AuthServiceRequest;

import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/6/6 0006.
 * 登录注册等
 */

public class AuthAPI extends HttpMethod {
    private volatile static AuthAPI authAPI;
    private AuthServiceRequest authServiceRequest = retrofitBuild().create(AuthServiceRequest.class);

    private AuthAPI() {

    }

    public static AuthAPI getInstance() {
        if (authAPI == null) {
            synchronized (AuthAPI.class) {
                if (authAPI == null) {
                    authAPI = new AuthAPI();
                }
            }
        }
        return authAPI;
    }

    //获取验证码
    public void getRegisterCodeWithRetrofit(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.registerCode(body).map(new HttpMethod.HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    /**
     * 注册
     */
    public void register(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.register(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //登录
    public void login(Subscriber<HttpResult<UserInfoBean>> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.login(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //校验验证码
    public void checkRandomCode(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.checkRandomCode(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //修改密码
    public void modifyPassword(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.modifyPassword(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //修改电话号码
    public void modifyPhone(Subscriber<HttpResult<CustomerInfoBean>> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.modifyPhone(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //验证密码修改手机号码
    public void modifyPhoneWithPsw(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.modifyPhoneWithPsw(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }

    //找回密码
    public void findBackPsw(Subscriber<HttpResult> subscriber, RequestBody body) {
        Observable observable = authServiceRequest.findBackPsw(body).map(new HttpResultFunc());
        toSubscribe(observable, subscriber);
    }
}
