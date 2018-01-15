package com.duoyi.drawguess.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

/**
 * 玩家基本信息
 * Created by YMlion on 2017/12/15.
 */

public class Player implements Parcelable {
    private String id;
    private String name;
    private String avatar;
    private boolean state;

    public Player(String id, String name, String avatar, boolean state) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.state = state;
    }

    public Player(String name, String avatar, boolean state) {
        this.name = name;
        this.avatar = avatar;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isReady() {
        return state;
    }

    public void setReady(boolean state) {
        this.state = state;
    }

    @Override public boolean equals(Object obj) {
        if (obj instanceof Player) {
            return ((Player) obj).id.equals(id);
        }
        return super.equals(obj);
    }

    public static List<Player> mockList(int num) {
        List<Player> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Player p = new Player("张三" + i,
                    "http://img5.imgtn.bdimg.com/it/u=1082195034,4139527125&fm=27&gp=0.jpg", false);
            list.add(p);
        }
        return list;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeByte(this.state ? (byte) 1 : (byte) 0);
    }

    protected Player(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.state = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
