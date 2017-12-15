package com.duoyi.drawguess.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.base.BaseActivity;

public class DrawGuessActivity extends BaseActivity {

    private RecyclerView readyUsersRv;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_guess);
    }

    @Override protected void initView() {
        super.initView();
        initRv();
    }

    private void initRv() {
        readyUsersRv = (RecyclerView) fv(R.id.rv_dg_ready_users);
        readyUsersRv.setLayoutManager(new GridLayoutManager(this, 3));
    }

    @Override protected void initData() {
        super.initData();
    }

    @Override public void onClick(View v) {

    }
}
