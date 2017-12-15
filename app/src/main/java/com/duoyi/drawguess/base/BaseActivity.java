package com.duoyi.drawguess.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.duoyi.drawguess.util.DLog;

/**
 * activity基类，公共方法及生命周期管理
 * Created by YMlion on 2017/12/14.
 */

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLog.d("activity onCreate");
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
        DLog.d("activity onStart");
    }

    @Override protected void onResume() {
        super.onResume();
        DLog.d("activity onResume");
    }

    @Override protected void onPause() {
        super.onPause();
        DLog.d("activity onPause");
    }

    @Override protected void onStop() {
        super.onStop();
        DLog.d("activity onStop");
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        DLog.d("activity onDestroy");
    }
}