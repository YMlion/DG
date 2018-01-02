package com.duoyi.drawguess.api;

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
}
