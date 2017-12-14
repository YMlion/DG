package com.duoyi.drawguess.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.duoyi.drawguess.util.DLog;

/**
 * Created by YMlion on 2017/12/14.
 */

public class BaseActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLog.d("activity onCreate");
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
