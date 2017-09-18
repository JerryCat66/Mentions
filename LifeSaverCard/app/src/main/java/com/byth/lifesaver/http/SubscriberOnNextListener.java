package com.byth.lifesaver.http;

/**
 * Created by wjn on 2016/7/4.
 * 获取model数据监听
 */
public interface SubscriberOnNextListener<T> {
    void onStart();

    void onNext(T t);

    void onApiError(ApiException e);

    void onNetworkError(Throwable e);

    void onOtherError(Throwable e);

    void onCompleted();
}