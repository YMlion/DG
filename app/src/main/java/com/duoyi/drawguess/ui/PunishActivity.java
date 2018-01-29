package com.duoyi.drawguess.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.duoyi.drawguess.R;
import com.duoyi.drawguess.api.AppSocket;
import com.duoyi.drawguess.api.SocketResult;
import com.duoyi.drawguess.base.BaseActivity;
import com.duoyi.drawguess.base.RvMultiBaseAdapter;
import com.duoyi.drawguess.base.ViewHolder;
import com.duoyi.drawguess.model.ChatMsg;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 惩罚界面
 */
public class PunishActivity extends BaseActivity {

    private RecyclerView msgRv;
    private TextView titleTv;
    private TextView timeTv;
    private EditText inputEt;
    private View openPlusBtn;
    private ImageButton changeInputIb;
    private View voiceLayout;
    private View sendBtn;

    private RvMultiBaseAdapter<ChatMsg> msgAdapter;
    private List<ChatMsg> mChatMsgList;

    private CompositeDisposable mCompositeDisposable;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punish);
    }

    @Override protected void initView() {
        super.initView();
        msgRv = (RecyclerView) fv(R.id.rv_punish_msg);
        titleTv = (TextView) fv(R.id.tv_title);
        timeTv = (TextView) fv(R.id.tv_title_time);
        inputEt = (EditText) fv(R.id.et_edit_chat);
        openPlusBtn = setOnClickListener(R.id.iv_open_plugins);
        changeInputIb = (ImageButton) setOnClickListener(R.id.ib_change_input_mode);
        voiceLayout = fv(R.id.ll_record_btn);
        sendBtn = setOnClickListener(R.id.btn_chat_send);
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
    }

    @Override protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        initMsgRv();
        mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(
                Observable.intervalRange(0, 40, 0, 1, TimeUnit.SECONDS, Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(this::finish)
                        .subscribe(aLong -> timeTv.setText((40 - aLong) + "")));
    }

    private void initMsgRv() {
        mChatMsgList = new ArrayList<>();
        ChatMsg welcomeMsg =
                new ChatMsg(ChatMsg.CHAT_TYPE_TEXT, "游戏还没结束！下面是40s惩罚时间", System.currentTimeMillis(),
                        "法官", "", "0");
        mChatMsgList.add(welcomeMsg);
        msgRv.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter =
                new RvMultiBaseAdapter<ChatMsg>(mChatMsgList, R.layout.chat_list_item_text_left,
                        R.layout.chat_list_item_picture_left, R.layout.chat_list_item_dice_left,
                        R.layout.chat_list_item_voice_left, R.layout.chat_list_item_text_right,
                        R.layout.chat_list_item_picture_right, R.layout.chat_list_item_dice_right,
                        R.layout.chat_list_item_voice_right) {
                    @Override public void onBind(ViewHolder holder, ChatMsg model, int viewType) {
                        if (model.getUserId().equals("0")) {
                            holder.setImageResource(R.id.iv_user_avatar, R.drawable.chage_icon);
                        } else {
                            holder.showNetImage(R.id.iv_user_avatar, model.getUserAvatar());
                        }
                        switch (viewType) {
                            case 0:
                            case 4:
                                holder.setText(R.id.tv_user_name, model.getUserName())
                                        .setText(R.id.tv_chat_content, model.getText());
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            default:
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

    @Subscribe(threadMode = ThreadMode.MAIN) public void onMsgReceived(SocketResult result) {
        switch (result.action) {
            case "chat_msg":
                mChatMsgList.add((ChatMsg) result.data);
                msgAdapter.notifyItemInserted(mChatMsgList.size());
                msgRv.smoothScrollToPosition(msgAdapter.getItemCount() - 1);
                break;
            case "punish_finished":
                finish();
                break;
        }
    }

    @Override protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mCompositeDisposable.clear();
        super.onDestroy();
    }
}
