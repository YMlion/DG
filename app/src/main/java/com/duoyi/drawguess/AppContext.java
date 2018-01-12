package com.duoyi.drawguess;

import android.app.Application;
import android.os.StrictMode;
import com.duoyi.drawguess.model.Player;
import com.duoyi.drawguess.model.User;
import com.squareup.leakcanary.LeakCanary;
import org.greenrobot.eventbus.EventBus;

/**
 * App base
 * Created by YMlion on 2017/12/14.
 */

public class AppContext extends Application {

    private static AppContext INSTANCE = null;
    private User mUser;

    public static AppContext getInstance() {
        return INSTANCE;
    }

    @Override public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }

    public User getUser() {
        // TODO: 2018/1/2 查找数据库
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @Override public void onTerminate() {
        super.onTerminate();
    }
}
