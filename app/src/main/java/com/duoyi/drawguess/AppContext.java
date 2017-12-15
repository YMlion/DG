package com.duoyi.drawguess;

import android.app.Application;

/**
 * Created by YMlion on 2017/12/14.
 */

public class AppContext extends Application {

    private static AppContext INSTANCE = null;

    public static AppContext getInstance() {
        return INSTANCE;
    }

    @Override public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}