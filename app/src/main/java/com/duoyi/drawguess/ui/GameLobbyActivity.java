package com.duoyi.drawguess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.base.BaseActivity;

/**
 * 游戏大厅界面
 */
public class GameLobbyActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
    }

    @Override protected void initView() {
        super.initView();
        setOnClickListener(R.id.btn_lobby_dg_open);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lobby_dg_open:
                playDG();
                break;
        }
    }

    private void playDG() {
        Intent intent = new Intent(this, DrawGuessActivity.class);
        startActivity(intent);
    }
}