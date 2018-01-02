package com.duoyi.drawguess.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer空实现类
 * Created by YMlion on 2018/1/2.
 */

public class AppObserver<T> implements Observer<T> {
    @Override public void onSubscribe(Disposable d) {

    }

    @Override public void onNext(T t) {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onComplete() {

    }
}
