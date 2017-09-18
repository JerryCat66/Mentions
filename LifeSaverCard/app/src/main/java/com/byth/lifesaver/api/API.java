package com.byth.lifesaver.api;

import com.android.volley.Request;
import com.fenguo.library.http.GsonRequest;
import com.fenguo.library.http.JsonResponse;
import com.fenguo.library.http.VolleyUtil;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class API {
    //public static final String url = "http://192.168.10.75:8080";//小黄
//    public static final String url = "http://192.168.10.49:8080/WeixinService/";//如敏
//    public static final String url = "http://192.168.10.158:8080/WeixinService/";//测试服务器
//    public static final String url = "http://192.168.10.194:8080/WeixinService/";//小林
//    public static final String url = "http://192.168.10.79:8080/WeixinService/";
    public static final String url = "http://120.24.157.210:8888/WeixinService/";//上线地址


    protected final static VolleyUtil volleyUtil = VolleyUtil.getInstance();

    protected static void doRequest(String url, Map<String, String> map, GsonRequest.GsonListener<JsonResponse> listener) {
        volleyUtil.doGsonRequest(url, map, listener);
    }

    protected static void doRequest(String url, Map<String, String> map, GsonRequest.GsonListener<JsonResponse> listener, Object tag) {
        volleyUtil.doGsonRequest(url, map, listener, tag);
    }

    protected static void doRequestNoErrorResponse(String url, Map<String, String> map, GsonRequest.GsonListener<JsonResponse> listener) {
        volleyUtil.doGsonRequestNoErrorResponse(Request.Method.POST, url, map, listener);
    }
}
