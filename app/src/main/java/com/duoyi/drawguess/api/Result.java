package com.duoyi.drawguess.api;

import com.google.gson.Gson;

/**
 * response
 * Created by YMlion on 2018/1/2.
 */

public class Result {

    public Result(int code, String action) {
        this.code = code;
        this.action = action;
    }

    /**
     * 状态码，表明成功失败
     */
    public int code;
    /**
     * 接口响应动作
     */
    public String action;

    public static Result get(String text) {
        Gson gson = new Gson();
        return gson.fromJson(text, Result.class);
    }
}
