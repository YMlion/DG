package com.duoyi.drawguess.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.base.BaseActivity;
import com.duoyi.drawguess.base.RvBaseAdapter;
import com.duoyi.drawguess.base.ViewHolder;
import com.duoyi.drawguess.model.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * 你画我猜主界面
 */
public class DrawGuessActivity extends BaseActivity {

    private RecyclerView seatRv;
    private List<Player> sittingPlayers;
    private Button readyBtn;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_guess);
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
                break;
            case R.id.tv_invite:
                break;
            case R.id.tv_exit:
                finish();
                break;
        }
    }
}
