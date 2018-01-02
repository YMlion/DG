package com.duoyi.drawguess.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by YMlion on 2018/1/2.
 */

public class RxUtil {
    public static <T> ObservableTransformer<T, T> applyScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
