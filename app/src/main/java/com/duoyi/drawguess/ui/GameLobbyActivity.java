package com.duoyi.drawguess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
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

    private TextInputLayout mTokenTil;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        EventBus.getDefault().register(this);
        AppSocket.get().init();
    }

    @Override protected void initView() {
        super.initView();
        mTokenTil = (TextInputLayout) fv(R.id.textInputLayout);
        setOnClickListener(R.id.btn_lobby_dg_open);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lobby_dg_open:
                String token = mTokenTil.getEditText().getText().toString();
                if (TextUtils.isEmpty(token)) {
                    mTokenTil.setError("input the token!!!");
                    return;
                }
                AppSocket.get().verifyToken(token);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsgRecieved(SocketResult result){
        if (result.action.equals("start")) {
            playDG();
        } else if (result.action.equals("user_info")) {

        }
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
