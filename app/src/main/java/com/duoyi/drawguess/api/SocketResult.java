package com.duoyi.drawguess.api;

import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * socket response
 * Created by YMlion on 2018/1/2.
 */

public class SocketResult<T> extends Result {

    public SocketResult(int code, String action, T result) {
        super(code, action);
        this.result = result;
    }

    /**
     * 响应结果
     */
    public T result;

    public static SocketResult get(String text) {
        Gson gson = new Gson();
        return gson.fromJson(text, Type);
    }
}
