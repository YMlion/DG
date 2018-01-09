package com.duoyi.drawguess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.api.AppSocket;
import com.duoyi.drawguess.api.SocketResult;
import com.duoyi.drawguess.base.BaseActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 游戏大厅界面
 */
public class GameLobbyActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        EventBus.getDefault().register(this);
        AppSocket.get().init();
    }

    @Override protected void initView() {
        super.initView();
        setOnClickListener(R.id.btn_lobby_dg_open);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lobby_dg_open:
                //handleResult();
                requestRoom();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgRecieved(SocketResult result){
        if (result.action.equals("start")) {
            playDG();
        }
    }

    private void requestRoom() {
        AppSocket.get().startDG();
    }

    private void playDG() {
        Intent intent = new Intent(this, DrawGuessActivity.class);
        startActivity(intent);
    }

    @Override protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        AppSocket.get().close();
        super.onDestroy();
    }
}
