package com.duoyi.drawguess.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.api.AppSocket;
import com.duoyi.drawguess.base.BaseActivity;

/**
 * 游戏大厅界面
 */
public class GameLobbyActivity extends BaseActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
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

    private void handleResult() {
        /*AppSocket.get()
                .getObservable()
                .compose(RxUtil.applyScheduler())
                .subscribe(addDisposable(new AppObserver<SocketResult>() {
                    @Override public void onNext(SocketResult socketResult) {
                        DLog.d("the result is " + socketResult.result);
                        if (socketResult.result.equals("12121")) {
                            playDG();
                        }
                    }
                }));*/
    }

    private void requestRoom() {
        AppSocket.get().startDG();
    }

    private void playDG() {
        Intent intent = new Intent(this, DrawGuessActivity.class);
        startActivity(intent);
    }

    @Override protected void onDestroy() {
        AppSocket.get().close();
        super.onDestroy();
    }
}
