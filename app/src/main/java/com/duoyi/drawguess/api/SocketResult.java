package com.duoyi.drawguess.api;

import com.google.gson.Gson;

/**
 * socket response
 * Created by YMlion on 2018/1/2.
 */

public class SocketResult<T> {

    public SocketResult(int code, String action, T result) {
        this.code = code;
        this.action = action;
        this.result = result;
    }

    /**
     * 状态码，表明成功失败
     */
    public int code;
    /**
     * 接口响应动作
     */
    public String action;
    /**
     * 响应结果
     */
    public T result;

    public static SocketResult get(String text) {
        Gson gson = new Gson();
        return gson.fromJson(text, SocketResult.class);
    }
}
