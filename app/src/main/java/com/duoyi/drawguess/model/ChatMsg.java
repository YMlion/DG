package com.duoyi.drawguess.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YMlion on 2018/1/12.
 */

public class ChatMsg implements Parcelable {

    /**
     * 消息类型：文本
     */
    public static final int CHAT_TYPE_TEXT = 0;
    /**
     * 消息类型：图片 or 表情
     */
    public static final int CHAT_TYPE_IMAGE = 1;
    /**
     * 消息类型：语音
     */
    public static final int CHAT_TYPE_VOICE = 2;
    /**
     * 消息类型：骰子
     */
    public static final int CHAT_TYPE_DICE = 3;

    /**
     * 消息类型数量
     */
    public static final int TYPE_NUM = 4;

    /**
     * 消息类型
     * <p>
     * 0：纯文本；1：图片 or 表情；2：语音
     * <p/>
     *
     * @see #CHAT_TYPE_TEXT
     * @see #CHAT_TYPE_IMAGE
     * @see #CHAT_TYPE_VOICE
     * @see #CHAT_TYPE_DICE
     */
    public int type;

    /**
     * 是否是我自己的消息
     */
    public boolean myMsg = false;

    /**
     * 消息内容：文本 or 图片地址 or 语音地址
     */
    public String text;
    /**
     * 消息时间
     */
    public long time;
    /**
     * 语音时长
     */
    public int voiceDuration;

    /**
     * 发送消息的用户名
     */
    public String userName;
    /**
     * 发送消息的头像
     */
    public String userAvatar;
    /**
     * 发送消息的用户id
     */
    public String userId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isMyMsg() {
        return myMsg;
    }

    public void setMyMsg(boolean myMsg) {
        this.myMsg = myMsg;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getVoiceDuration() {
        return voiceDuration;
    }

    public void setVoiceDuration(int voiceDuration) {
        this.voiceDuration = voiceDuration;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取消息类型对应的view
     *
     * @return view类型
     */
    public int getMsgViewType() {
        return type;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeByte(this.myMsg ? (byte) 1 : (byte) 0);
        dest.writeString(this.text);
        dest.writeLong(this.time);
        dest.writeInt(this.voiceDuration);
        dest.writeString(this.userName);
        dest.writeString(this.userAvatar);
        dest.writeString(this.userId);
    }

    public ChatMsg() {
    }

    public ChatMsg(int type, String text, long time, String userName, String userAvatar,
            String userId) {
        this.type = type;
        this.text = text;
        this.time = time;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userId = userId;
    }

    protected ChatMsg(Parcel in) {
        this.type = in.readInt();
        this.myMsg = in.readByte() != 0;
        this.text = in.readString();
        this.time = in.readLong();
        this.voiceDuration = in.readInt();
        this.userName = in.readString();
        this.userAvatar = in.readString();
        this.userId = in.readString();
    }

    public static final Parcelable.Creator<ChatMsg> CREATOR = new Parcelable.Creator<ChatMsg>() {
        @Override public ChatMsg createFromParcel(Parcel source) {
            return new ChatMsg(source);
        }

        @Override public ChatMsg[] newArray(int size) {
            return new ChatMsg[size];
        }
    };
}
