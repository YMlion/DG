package com.duoyi.drawguess.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.api.AppSocket;
import com.duoyi.drawguess.api.SocketResult;
import com.duoyi.drawguess.base.BaseActivity;
import com.duoyi.drawguess.base.RvBaseAdapter;
import com.duoyi.drawguess.base.ViewHolder;
import com.duoyi.drawguess.model.Player;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 你画我猜主界面
 */
public class DrawGuessActivity extends BaseActivity {

    private RecyclerView seatRv;
    private List<Player> sittingPlayers;
    private Button readyBtn;

    private PopupWindow setDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_guess);
        EventBus.getDefault().register(this);
    }

    @Override protected void initView() {
        super.initView();
        seatRv = (RecyclerView) fv(R.id.rv_dg_seat);
        setOnClickListener(R.id.tv_invite);
        setOnClickListener(R.id.tv_exit);
        setOnClickListener(R.id.tv_dg_settings);
        readyBtn = (Button) setOnClickListener(R.id.btn_ready);
    }

    private void initRv() {
        sittingPlayers = new ArrayList<>();
        sittingPlayers.addAll(Player.mockList(6));
        seatRv.setLayoutManager(new GridLayoutManager(this, 3));
        seatRv.setAdapter(
                new RvBaseAdapter<Player>(sittingPlayers, R.layout.item_room_prepare_seat) {
                    @Override public void onBind(ViewHolder holder, Player model) {
                        holder.setText(R.id.tv_user_name, model.getName())
                                .setVisible(R.id.iv_user_status, model.isReady())
                                .showNetImage(R.id.iv_user_avatar, model.getAvatar());
                    }
                });
    }

    @Override protected void initData() {
        super.initData();
        initRv();
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dg_settings:
                // dialog or popup window
                showSetDialog();
                break;
            case R.id.tv_invite:
                break;
            case R.id.tv_exit:
                finish();
                break;
            case R.id.btn_ready:
                AppSocket.get().readyDG();
                readyBtn.setText("已准备");
                readyBtn.setEnabled(false);
                break;
        }
    }

    private void showSetDialog() {
        if (setDialog == null) {
            setDialog = new PopupWindow(-2, -2);
            View contentView = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_set, (ViewGroup) getWindow().getDecorView(), false);
            // TODO: 2017/12/19 view初始化
            LinearLayout quitlayout = contentView.findViewById(R.id.quit_layout);
            TextView btnquit = contentView.findViewById(R.id.btn_quit);
            LinearLayout followroom = contentView.findViewById(R.id.follow_room);
            TextView followtext = contentView.findViewById(R.id.follow_text);
            LinearLayout viproominfo = contentView.findViewById(R.id.vip_room_info);
            RelativeLayout rlrule = contentView.findViewById(R.id.rl_rule);
            LinearLayout soundlayout = contentView.findViewById(R.id.sound_layout);
            TextView soundtext = contentView.findViewById(R.id.sound_text);
            RelativeLayout rlmusic = contentView.findViewById(R.id.rl_music);
            TextView musicindicator = contentView.findViewById(R.id.music_indicator);
            TextView textmusic = contentView.findViewById(R.id.text_music);
            TextView tvcoin = contentView.findViewById(R.id.tv_coin);
            TextView tvroom = contentView.findViewById(R.id.tv_room);
            ImageView ivcancel = contentView.findViewById(R.id.iv_cancel);
            setDialog.setContentView(contentView);
            setDialog.setBackgroundDrawable(new BitmapDrawable());
            setDialog.setFocusable(true);
            setDialog.setTouchable(true);
            setDialog.setOutsideTouchable(true);
            setDialog.setOnDismissListener(() -> setBackgroundAlpha(0.6f, 1, 250));
        }

        setDialog.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        setBackgroundAlpha(1, 0.6f, 250);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) public void onMsgRecieved(SocketResult result) {
        switch (result.action) {
            case "user_in":
                Toast.makeText(this, "新用户加入", Toast.LENGTH_SHORT).show();
                break;
            case "user_quit":
                Toast.makeText(this, "有用户退出", Toast.LENGTH_SHORT).show();
                break;
            case "user_ready":
                Toast.makeText(this, "有用户准备", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override protected void onDestroy() {
        AppSocket.get().quitDG();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
