package com.duoyi.drawguess.api;

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
}
