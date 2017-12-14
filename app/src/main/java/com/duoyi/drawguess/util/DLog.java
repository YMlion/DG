package com.duoyi.drawguess.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by YMlion on 2017/12/14.
 */

public class DLog {

    private static String TAG = "";

    public static void setTAG(String TAG) {
        DLog.TAG = TAG;
    }

    private static String getTag(String tag) {
        if (TextUtils.isEmpty(TAG)) {
            return tag;
        } else {
            return TAG + "-" + tag;
        }
    }

    private static String getTag() {
        if (TextUtils.isEmpty(TAG)) {
            return "com.duoyi.drawguess";
        } else {
            return TAG;
        }
    }

    public static void v(String tag, Object msg) {
        Log.v(getTag(tag), String.valueOf(msg));
    }

    public static void d(String tag, Object msg) {
        Log.d(getTag(tag), String.valueOf(msg));
    }

    public static void i(String tag, Object msg) {
        Log.i(getTag(tag), String.valueOf(msg));
    }

    public static void w(String tag, Object msg) {
        Log.w(getTag(tag), String.valueOf(msg));
    }

    public static void e(String tag, Object msg) {
        Log.e(getTag(tag), String.valueOf(msg));
    }

    public static void v(Object msg) {
        Log.v(getTag(), String.valueOf(msg));
    }

    public static void d(Object msg) {
        Log.d(getTag(), String.valueOf(msg));
    }

    public static void i(Object msg) {
        Log.i(getTag(), String.valueOf(msg));
    }

    public static void w(Object msg) {
        Log.w(getTag(), String.valueOf(msg));
    }

    public static void e(Object msg) {
        Log.e(getTag(), String.valueOf(msg));
    }
}
