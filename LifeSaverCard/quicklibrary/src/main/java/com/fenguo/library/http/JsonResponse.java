package com.fenguo.library.http;

import com.fenguo.library.util.JsonUtil;
import com.google.gson.reflect.TypeToken;

/**
 * Fenguo接口返回的JavaBean
 *
 * @author Lee
 * @createDate 2015年5月25日
 */
public class JsonResponse {

    public String status;
    public String message;
    public Object data;

    public boolean isOk() {
        return status.equals(StateCode.C2000000.getStatus());

    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return String.valueOf(data);
    }

    public <T> T getData(Class<T> clazz) {
        return JsonUtil.fromObject(data, clazz);
    }

    public <T> T getData(TypeToken<T> token) {
        return JsonUtil.fromObject(data, token);
    }

    public static JsonResponse fromJson(String jsonString) {
        return JsonUtil.fromJson(jsonString, JsonResponse.class);
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
