package com.duoyi.drawguess.api;

import com.google.gson.Gson;

/**
 * socket request data
 * Created by YMlion on 2018/1/2.
 */

public class SocketRequestData<T> {
    /**
     * 接口请求动作
     */
    public String action;
    /**
     * 请求数据
     */
    public T data;

    public SocketRequestData(String action, T data) {
        this.action = action;
        this.data = data;
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
