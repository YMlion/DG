package com.duoyi.drawguess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家基本信息
 * Created by YMlion on 2017/12/15.
 */

public class Player {
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
}
