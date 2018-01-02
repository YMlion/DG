package com.duoyi.drawguess;

import android.app.Application;
import android.os.StrictMode;
import com.squareup.leakcanary.LeakCanary;

/**
 * App base
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
        LeakCanary.install(this);
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
    }

    @Override public void onTerminate() {
        super.onTerminate();
    }
}
