package com.duoyi.drawguess.api;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import java.lang.reflect.Type;

/**
 * socket response
 * Created by YMlion on 2018/1/2.
 */

public class SocketResult<T> extends Result {

    public SocketResult(int code, String action, T data) {
        super(code, action);
        this.data = data;
    }

    /**
     * 响应结果
     */
    public T data;

    public static SocketResult getObj(String text, Class clazz) {
        Gson gson = new Gson();
        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, SocketResult.class, clazz);
        return gson.fromJson(text, type);
    }
}
