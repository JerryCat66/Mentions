package com.fenguo.library.http;

import android.nfc.Tag;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

;

/**
 * 实现GSON的Volley解析
 *
 * @author Lee
 * @createDate 2015年3月19日
 */
public class GsonRequest extends Request<JsonResponse> {
    private final GsonListener<JsonResponse> mListener;
    private Gson mGson;

    public GsonRequest(int method, String url, GsonListener<JsonResponse> listener,
                       ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mListener = listener;
    }

    public GsonRequest(String url, GsonListener<JsonResponse> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    protected Response<JsonResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.i("lina", "parseNetworkResponse---" + jsonString.toString());
            return Response.success(mGson.fromJson(jsonString, JsonResponse.class),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(JsonResponse response) {
        // Result result = Result.fromJson(result.toString());
        JsonResponse result = (JsonResponse) response;
        if (result.isOk()) {
            mListener.onSuccessResponse(result);
        } else {
            mListener.onFailResponse(result);
        }

    }

    @Override
    public void deliverError(VolleyError error) {
        mListener.onFailResponse(new JsonResponse());
        super.deliverError(error);
    }

    public interface GsonListener<JsonResponse> {
        public void onSuccessResponse(JsonResponse response);

        public void onFailResponse(JsonResponse response);
    }

}
