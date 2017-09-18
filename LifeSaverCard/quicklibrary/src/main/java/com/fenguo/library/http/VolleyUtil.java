package com.fenguo.library.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.fenguo.library.http.GsonRequest.GsonListener;
import com.fenguo.library.util.LogUtil;
import com.fenguo.library.util.NetworkUtil;
import com.fenguo.library.util.Preference;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

;

/**
 * 使用volley请求网络的工具类
 *
 * @author chenjc@ifenguo.com
 * @createDate 2015年1月14日
 */
public class VolleyUtil {

    private volatile static VolleyUtil volleyUtil;
    private RequestQueue mRequestQueue;
    private ErrorListener errorListener;
    private ErrorListener noResponseListener;
    private final String TAG = "volly_request";
    private Context context;
    private String imei;

    public VolleyUtil(Context context) {
        this.context = context.getApplicationContext();
       mRequestQueue = Volley.newRequestQueue(context);

        initErrorListener();

        // 获取imei号
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
    }
    // Singleton pattern
    public static VolleyUtil init(Context context) {
        if (volleyUtil == null) {
            synchronized (Volley.class) {
                if (volleyUtil == null) {
                    volleyUtil = new VolleyUtil(context);
                }
            }
        }
        return volleyUtil;
    }

    public static VolleyUtil getInstance() {
        if (volleyUtil == null) {
            throw new IllegalStateException(VolleyUtil.class.getSimpleName()
                    + " is not initialized, call getInstance(..) method first.");
        }
        return volleyUtil;
    }
    private void initErrorListener() {
        errorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if(!NetworkUtil.checkConnection(context)){
                    Toast.makeText(context, "当前网络无法连接服务器，请检查网络设置", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
                }
            }
        };
        noResponseListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        };
    }

    /**
     * 发起请求，请求方式为POST
     *
     * @param params
     * @param url
     * @param listener Listener<String>
     */
    public void doRequest(final Map<String, String> params, String url, Listener<String> listener) {
        doRequest(Method.POST, params, url, listener, volleyUtil.errorListener);
    }

    /**
     * 发起请求
     *
     * @param method   请求方式 默认是get
     * @param params   参数
     * @param url
     * @param listener Listener<String>
     */
    public void doRequest(int method, final Map<String, String> params, final String url,
                          Listener<String> listener, ErrorListener errorListener) {

        StringRequest stringRequest = null;
        stringRequest = new StringRequest(method, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    StringBuilder encodedParams = new StringBuilder();
                    encodedParams.append(url);
                    encodedParams.append('?');
                    Set<String> keySet = params.keySet();
                    Iterator<String> iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        String value = params.get(key);
                        encodedParams.append(key);
                        encodedParams.append("=");
                        encodedParams.append(value);
                        encodedParams.append('&');
                    }
                    encodedParams.deleteCharAt(encodedParams.length() - 1);
                    Log.i("msg", "getParams----" + encodedParams.toString());
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sessionId", Preference.getInstance().getString("sessionId"));
                // params.put("imei", imei);
                // params.put("client", "android");
//                params.put("sessionId", Preference.getInstance().getString("sessionId"));
//                params.put("Accept-Encoding", "gzip");
                return params;
            }


        };
        stringRequest.setTag(TAG);
        volleyUtil.mRequestQueue.add(stringRequest);
    }

    public void doGsonRequest(String url, final Map<String, String> params,
                              GsonListener<JsonResponse> listener) {
        doGsonRequest(Method.POST, url, params, listener, TAG);

    }

    public void doGsonRequest(String url, final Map<String, String> params,
                              GsonListener<JsonResponse> listener, Object tag) {
        doGsonRequest(Method.POST, url, params, listener, tag);

    }


    public void doGsonRequest(int method, final String url, final Map<String, String> params,
                              GsonListener<JsonResponse> listener, Object tag) {
        GsonRequest request = new GsonRequest(method, url, listener, volleyUtil.errorListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    StringBuilder encodedParams = new StringBuilder();
                    encodedParams.append(url);
                    encodedParams.append('?');
                    Set<String> keySet = params.keySet();
                    Iterator<String> iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        String value = params.get(key);
                        encodedParams.append(key);
                        encodedParams.append("=");
                        encodedParams.append(value);
                        encodedParams.append('&');
                    }
                    encodedParams.deleteCharAt(encodedParams.length() - 1);
                    Log.i("msg", "getParams----" + encodedParams.toString());
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("imei", imei);
                // params.put("client", "android");
                params.put("sessionId", Preference.getInstance().getString("sessionId"));
                return params;
            }


        };
        request.setTag(tag);
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleyUtil.mRequestQueue.add(request);

    }

    public void doGsonRequestNoErrorResponse(int method, final String url, final Map<String, String> params,
                                             GsonListener<JsonResponse> listener) {
        GsonRequest request = new GsonRequest(method, url, listener, noResponseListener) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    StringBuilder encodedParams = new StringBuilder();
                    encodedParams.append(url);
                    encodedParams.append('?');
                    Set<String> keySet = params.keySet();
                    Iterator<String> iterator = keySet.iterator();
                    while (iterator.hasNext()) {
                        String key = (String) iterator.next();
                        String value = params.get(key);
                        encodedParams.append(key);
                        encodedParams.append("=");
                        encodedParams.append(value);
                        encodedParams.append('&');
                    }
                    encodedParams.deleteCharAt(encodedParams.length() - 1);
                    Log.i("msg", "getParams----" + encodedParams.toString());
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("imei", imei);
                // params.put("client", "android");
                params.put("sessionId", Preference.getInstance().getString("sessionId"));
                Log.i("msg", "sessionId----" + Preference.getInstance().getString("sessionId"));
                return params;
            }


        };
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        volleyUtil.mRequestQueue.add(request);

    }


    /**
     * 取消所有请求
     */

    public void cancelRequest() {
        volleyUtil.mRequestQueue.cancelAll(TAG);
    }

    public void cancelRequest(Object tag) {
        volleyUtil.mRequestQueue.cancelAll(tag);
    }

//    /**
//     * 检查服务端返回的状态码，进行统一的处理。
//     *
//     * @param status
//     * @param context
//     * @return
//     */
//    public static boolean checkStatusOk(JsonResponse result, Context context) {
//        if (result.status == StateCode.OK) {
//            return true;
//        }
//
//        // 未登录或者登录过期，清空本地session数据
//        if (result.status == StateCode.USER_SESS_EXPIRED
//                || result.status == StateCode.USER_SESS_EXPIRED) {
//            // Toast.makeText(context, context.getString(R.string.user_not_login), Toast.LENGTH_SHORT)
//            // .show();
//            // SessionUserUtil.cleanSession();
//            return false;
//        }
//
//        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show();
//        return false;
//    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

}