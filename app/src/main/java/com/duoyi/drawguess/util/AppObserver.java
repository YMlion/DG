package com.duoyi.drawguess.util;

import io.reactivex.observers.DisposableObserver;

/**
 * Observer空实现类
 * Created by YMlion on 2018/1/2.
 */

public class AppObserver<T> extends DisposableObserver<T> {

    @Override public void onNext(T t) {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onComplete() {

    }
}
