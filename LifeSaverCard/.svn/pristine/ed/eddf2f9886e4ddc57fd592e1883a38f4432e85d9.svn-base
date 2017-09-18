package com.byth.lifesaver.http;

import com.byth.lifesaver.bean.ErrorMsgBean;
import com.fenguo.library.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 * 请求数据结果基类
 * success:false or true
 * msgList:[code:message]
 * data:[]
 */

public class HttpResult<T> {
    private boolean success;//是否成功
    private T data;//返回的数据
    private List<ErrorMsgBean> msgList = new ArrayList<>();//返回错误数据

    public List getMsgList() {
        return msgList;
    }

    public String message() {
        return msgList.get(0).getMessage();
    }

    public T getData() {
        return data;
    }

    public <T> T getData(Class<T> clazz) {
        return JsonUtil.fromObject(data, clazz);
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(data);
    }

}
