package com.duoyi.drawguess.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.duoyi.drawguess.AppContext;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.api.AppSocket;
import com.duoyi.drawguess.api.SocketResult;
import com.duoyi.drawguess.base.BaseActivity;
import com.duoyi.drawguess.base.RvMultiBaseAdapter;
import com.duoyi.drawguess.base.ViewHolder;
import com.duoyi.drawguess.model.ChatMsg;
import com.duoyi.drawguess.model.GameResult;
import com.duoyi.drawguess.model.Player;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 你画我猜主界面
 */
public class DrawGuessActivity extends BaseActivity {

    private ArrayList<Player> sittingPlayers;

    private Fragment[] mFragments;
    private FragmentManager mFm;
    private int mIndex = 0;

    private PopupWindow setDialog;
    private int roomId;
    private TextView roomNameTv;
    // 消息列表：法官消息、用户消息和自己发的消息；又分为文本、图片、语音等。
    private RecyclerView msgRv;
    private RvMultiBaseAdapter<ChatMsg> msgAdapter;
    private List<ChatMsg> mChatMsgList;
    // 聊天相关
    private ImageButton changeInputIb;
    private EditText inputEt;
    private Button sendBtn;
    private View openPlusBtn;
    private View voiceLayout;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_guess);
        EventBus.getDefault().register(this);
    }

    @Override protected void initView() {
        mFm = getSupportFragmentManager();
        super.initView();
        setOnClickListener(R.id.tv_set, true);
        roomNameTv = (TextView) fv(R.id.tv_title);
        msgRv = (RecyclerView) fv(R.id.rv_msg);
        changeInputIb = (ImageButton) setOnClickListener(R.id.ib_change_input_mode);
        changeInputIb.setImageLevel(1);
        inputEt = (EditText) fv(R.id.et_edit_chat);
        sendBtn = (Button) setOnClickListener(R.id.btn_chat_send);
        openPlusBtn = setOnClickListener(R.id.iv_open_plugins);
        inputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    openPlusBtn.setVisibility(View.VISIBLE);
                    sendBtn.setVisibility(View.GONE);
                } else {
                    openPlusBtn.setVisibility(View.GONE);
                    sendBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
        voiceLayout = fv(R.id.ll_record_btn);
    }

    @Override protected void initData() {
        super.initData();
        Intent intent = getIntent();
        roomId = intent.getIntExtra("roomId", 0);
        if (roomId == 0) {
            finish();
            return;
        }
        roomNameTv.setText(roomId + "房间");
        boolean started = intent.getBooleanExtra("started", false);
        ArrayList<Player> players = intent.getParcelableArrayListExtra("players");
        sittingPlayers = new ArrayList<>();
        if (players != null && !players.isEmpty()) {
            sittingPlayers.addAll(players);
        }
        if (!started) {
            sittingPlayers.add(AppContext.getInstance().getUser().getPlayer());
        }
        setFragment(started ? 1 : 0);
        initMsgRv();
    }

    private void setFragment(int index) {
        FragmentTransaction ft = mFm.beginTransaction();
        if (mFragments == null) {
            mFragments = new Fragment[2];
        }
        if (mFragments[index] == null) {
            mFragments[index] = index == 0 ? PrepareFragment.newInstance(sittingPlayers)
                    : PaintingFragment.newInstance(sittingPlayers);
        }
        for (Fragment f : mFragments) {
            if (f != null && f.isVisible()) {
                ft.hide(f);
            }
        }
        if (mFragments[index].isAdded()) {
            ft.show(mFragments[index]);
        } else {
            ft.add(R.id.fragment_container, mFragments[index]);
        }
        ft.commitAllowingStateLoss();
        mIndex = index;
    }

    private void initMsgRv() {
        mChatMsgList = new ArrayList<>();
        String name = AppContext.getInstance().getUser().getName();
        ChatMsg welcomeMsg =
                new ChatMsg(ChatMsg.CHAT_TYPE_TEXT, "欢迎" + name, System.currentTimeMillis(), "法官",
                        "", "0");
        mChatMsgList.add(welcomeMsg);
        msgRv.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new RvMultiBaseAdapter<ChatMsg>(mChatMsgList, R.layout.item_chat_text,
                R.layout.item_chat_image, R.layout.item_chat_voice, R.layout.item_chat_dice) {
            @Override public void onBind(ViewHolder holder, ChatMsg model, int viewType) {
                switch (viewType) {
                    case 0:
                        holder.setText(R.id.tv_user_name, model.getUserName() + ": ")
                                .setText(R.id.tv_chat_content, model.getText());
                        break;
                }
            }

            @Override public int getItemViewType(int position) {
                return mDatas.get(position).getMsgViewType();
            }
        };
        msgRv.setAdapter(msgAdapter);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_set:
                // dialog or popup window
                showSetDialog();
                break;
            case R.id.ib_change_input_mode:
                int level = 1 - changeInputIb.getDrawable().getLevel();
                changeInputIb.setImageLevel(level);
                if (level == 0) {
                    inputEt.setVisibility(View.INVISIBLE);
                    voiceLayout.setVisibility(View.VISIBLE);
                } else {
                    inputEt.setVisibility(View.VISIBLE);
                    voiceLayout.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.btn_chat_send:
                AppSocket.get().sendChatMsg(inputEt.getText().toString());
                inputEt.setText("");
                break;
        }
    }

    private void showSetDialog() {
        if (setDialog == null) {
            setDialog = new PopupWindow(-2, -2);
            View contentView = LayoutInflater.from(this)
                    .inflate(R.layout.dialog_set, (ViewGroup) getWindow().getDecorView(), false);
            // TODO: 2017/12/19 view初始化

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

    @Subscribe(threadMode = ThreadMode.MAIN) public void onMsgReceived(SocketResult result) {
        switch (result.action) {
            case "user_in":
                Toast.makeText(this, "新用户加入", Toast.LENGTH_SHORT).show();
                sittingPlayers.add((Player) result.data);
                if (mIndex == 0) {
                    ((PrepareFragment) mFragments[0]).addPlayer((Player) result.data);
                }
                break;
            case "user_quit":
                Toast.makeText(this, "有用户退出", Toast.LENGTH_SHORT).show();
                String quitId = (String) result.data;
                for (int i = 0; i < sittingPlayers.size(); i++) {
                    if (sittingPlayers.get(i).getId().equals(quitId)) {
                        sittingPlayers.remove(i);
                        if (mIndex == 0) {
                            ((PrepareFragment) mFragments[0]).removePlayer(i);
                        } else {
                            ((PaintingFragment) mFragments[0]).removePlayer(i);
                        }
                        break;
                    }
                }
                break;
            case "user_ready":
                Toast.makeText(this, "有用户准备", Toast.LENGTH_SHORT).show();
                String readyId = (String) result.data;
                for (int i = 0; i < sittingPlayers.size(); i++) {
                    if (sittingPlayers.get(i).getId().equals(readyId)) {
                        sittingPlayers.get(i).setReady(true);
                        if (mIndex == 0) {
                            ((PrepareFragment) mFragments[0]).setPlayerReady(i);
                        }
                        break;
                    }
                }
                break;
            case "start_game":
                // TODO: 2018/1/05 切换界面
                setFragment(1);
                break;
            case "chat_msg":
                // TODO: 2018/1/12 消息
                mChatMsgList.add((ChatMsg) result.data);
                msgAdapter.notifyItemInserted(mChatMsgList.size());
                msgRv.smoothScrollToPosition(msgAdapter.getItemCount() - 1);
                break;
            case "game_result":
                final GameResult gameResult = (GameResult) result.data;
                final PopupWindow window = showGameResult(gameResult);
                // 3s后跳转惩罚
                Observable.timer(3, TimeUnit.SECONDS).subscribe(aLong -> {
                    window.dismiss();
                    Intent intent = new Intent(DrawGuessActivity.this, PunishActivity.class);
                    ArrayList<CharSequence> failures = new ArrayList<>();
                    for (String id : gameResult.failures) {
                        for (Player player : sittingPlayers) {
                            if (player.getId().equals(id)) {
                                failures.add(player.getName());
                            }
                        }
                    }
                    intent.putCharSequenceArrayListExtra("failures", failures);
                    startActivityForResult(intent, 0x1);
                });
                break;
        }
    }

    private PopupWindow showGameResult(GameResult gameResult) {
        PopupWindow resultWindow = new PopupWindow(-2, -2);
        View contentView = LayoutInflater.from(this)
                .inflate(R.layout.paint_game_over, (ViewGroup) getWindow().getDecorView(), false);
        // TODO: 2018/1/12 设置头像
        ImageView myAvatar = contentView.findViewById(R.id.iv_my_avatar);

        resultWindow.setContentView(contentView);
        resultWindow.setBackgroundDrawable(new BitmapDrawable());
        resultWindow.setFocusable(true);
        resultWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        return resultWindow;
    }

    @Override protected void onDestroy() {
        AppSocket.get().quitDG();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
