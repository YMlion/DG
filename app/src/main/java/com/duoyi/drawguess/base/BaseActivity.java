package com.duoyi.drawguess.base;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import com.duoyi.drawguess.util.DLog;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * activity基类，公共方法及生命周期管理
 * Created by YMlion on 2017/12/14.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    
    private String activityName = getClass().getSimpleName();
    private CompositeDisposable mCD = new CompositeDisposable();

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLog.d(activityName + " onCreate");
    }

    @Override public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    private void init() {
        initView();
        initData();
    }

    /**
     * 数据初始化
     */
    protected void initData() {

    }

    /**
     * view初始化
     */
    protected void initView() {

    }

    protected View fv(int resId) {
        return findViewById(resId);
    }

    /**
     * 设置View点击事件
     *
     * @param resId view id
     * @return the view
     */
    protected View setOnClickListener(int resId) {
        View v = fv(resId);
        v.setOnClickListener(this);
        return v;
    }

    /**
     * 设置View点击事件
     *
     * @param resId view id
     * @param visible 是否可见
     * @return the view
     */
    protected View setOnClickListener(int resId, boolean visible) {
        View v = fv(resId);
        v.setOnClickListener(this);
        v.setVisibility(visible ? View.VISIBLE : View.GONE);
        return v;
    }

    /**
     * 设置View点击事件
     *
     * @param resId view id
     * @param listener 点击回调
     * @return the view
     */
    protected View setOnClickListener(int resId, View.OnClickListener listener) {
        View v = fv(resId);
        v.setOnClickListener(listener);
        return v;
    }

    @Override public void onClick(View v) {

    }

    @Override protected void onStart() {
        super.onStart();
        DLog.d(activityName + " onStart");
    }

    @Override protected void onResume() {
        super.onResume();
        DLog.d(activityName + " onResume");
    }

    @Override protected void onPause() {
        super.onPause();
        DLog.d(activityName + " onPause");
    }

    @Override protected void onStop() {
        super.onStop();
        DLog.d(activityName + " onStop");
    }

    @Override protected void onDestroy() {
        mCD.clear();
        super.onDestroy();
        DLog.d(activityName + " onDestroy");
    }

    /**
     * 设置背景透明度渐变动画
     */
    public void setBackgroundAlpha(float from, float to, int duration) {
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        ValueAnimator animator = ValueAnimator.ofFloat(from, to).setDuration(duration);
        animator.addUpdateListener(animation -> {
            lp.alpha = (Float) animation.getAnimatedValue();
            getWindow().setAttributes(lp);
        });
        animator.start();
    }

    public <T> DisposableObserver<T> addDisposable(DisposableObserver<T> d) {
        if (d != null) {
            mCD.add(d);
        }
        return d;
    }
}
