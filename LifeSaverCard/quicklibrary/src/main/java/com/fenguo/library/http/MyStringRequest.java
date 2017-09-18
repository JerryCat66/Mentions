package com.fenguo.library.http;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * 测试使用
 *
 * @author Lee
 * @createDate 2015年3月19日
 */
public class MyStringRequest extends Request<String> {
    private final MyListener<String> mListener;

    public MyStringRequest(int method, String url, MyListener<String> listener,
                           ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public MyStringRequest(String url, MyListener<String> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        String[] str1 = response.split(",");
        String[] str2 = str1[1].split(":");
        mListener.onSuccessResponse(response);
//        if (str2[1].equals("2000000")) {
//            mListener.onSuccessResponse(response);
//        } else {
//            mListener.onFailResponse(response);
//        }

    }

    public interface MyListener<T> {
        public void onSuccessResponse(T response);

        public void onFailResponse(T response);
    }

}